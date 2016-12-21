package pt.ipp.estg.cmu.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

/**
 * Class responsavel por gerir as keys guardadas no keystore, cria uma key , encripa e desencripta informcacao associada a essa key
 */
public class SecurityManager {

    private static final String TAG = "AppData";
    private static final String CIPHER_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String CIPHER_PROVIDER = "AndroidOpenSSL";
    private static final String KEY_PROVIDER = "AndroidKeyStore";
    private static final String KEY_ALGORITHM = "RSA";

    private KeyStore keyStore;

    private Context mContext;
    private String alias;

    public SecurityManager(Context mContext, String alias) {
        this.mContext = mContext;
        this.alias = alias;
        try {
            keyStore = KeyStore.getInstance(KEY_PROVIDER);
            keyStore.load(null);
            createNewKey();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewKey() {
        try {
            //new key if needed
            if (!keyStore.containsAlias(alias)) {

                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM, KEY_PROVIDER);

                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                kpg.initialize(new KeyPairGeneratorSpec.Builder(mContext)
                        .setAlias(alias)
                        .setSubject(new X500Principal(String.format("CN=%s, OU=%s", alias, mContext.getPackageName())))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build()
                );

                KeyPair keyPair = kpg.generateKeyPair();
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void deleteKey() {
        try {
            keyStore.deleteEntry(alias);
        } catch (KeyStoreException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }


    public String encryptString(String initialText) {
        try {

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            Cipher inCipher = Cipher.getInstance(CIPHER_TRANSFORMATION, CIPHER_PROVIDER);
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inCipher);
            cipherOutputStream.write(initialText.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte[] vals = outputStream.toByteArray();

            String cipher = Base64.encodeToString(vals, Base64.DEFAULT);
            save(cipher);
            return cipher;

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return null;
    }

    public String decryptString() {
        try {
            String cipherText = retrieve();

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
            Cipher output = Cipher.getInstance(CIPHER_TRANSFORMATION);
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            CipherInputStream cipherInputStream = new CipherInputStream(new ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte) nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            return new String(bytes, 0, bytes.length, "UTF-8");

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return null;
    }

    /*
     * Metodo para guardar a string encriptada em preferences
     */
    private void save(String text) {
        SharedPreferences pref = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.commit();

        editor.putString("text", text);
        editor.commit();
    }

    /**
     * Metodo para obter a string encriptada nos preferences
     *
     * @return string guardada
     */
    private String retrieve() {
        SharedPreferences pref = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return pref.getString("text", null);
    }


}
