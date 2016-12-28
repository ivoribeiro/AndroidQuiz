package pt.ipp.estg.cmu.security;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.FingerprintControllerCallback;

public class FingerprintController {

    private static final String KEY_NAME = "cmu_key";
    private android.hardware.fingerprint.FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject;

    private ImageView mInfoImageView;
    private TextView mInfoTextView;
    private Context mContext;
    private FingerprintControllerCallback mListener;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public FingerprintController(Context mContext, ImageView imageView, TextView textView, FingerprintControllerCallback fingerprintControllerCallback) {
        this.mContext = mContext;
        mInfoImageView = imageView;
        mInfoTextView = textView;
        mListener = fingerprintControllerCallback;
        start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void start() {

        //sem marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mInfoTextView.setVisibility(View.GONE);
            mInfoImageView.setVisibility(View.GONE);
            return;

        } else {
            keyguardManager = (KeyguardManager) mContext.getSystemService(mContext.KEYGUARD_SERVICE);
            fingerprintManager = (android.hardware.fingerprint.FingerprintManager) mContext.getSystemService(mContext.FINGERPRINT_SERVICE);


            //sem sensor
            if (!fingerprintManager.isHardwareDetected()) {
                mInfoTextView.setVisibility(View.GONE);
                mInfoImageView.setVisibility(View.GONE);
                return;
            }

            if (!keyguardManager.isKeyguardSecure()) {
                mInfoTextView.setText(mContext.getString(R.string.setup_fingerprint_error_lockscreen));
                return;
            }

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                mInfoTextView.setText(mContext.getString(R.string.setup_fingerprint_error_permission));
                return;
            }

            if (!fingerprintManager.hasEnrolledFingerprints()) {
                mInfoTextView.setText(mContext.getString(R.string.setup_fingerprint_error_register));
                return;
            }

            generateKey();

            if (cipherInit()) {
                cryptoObject = new android.hardware.fingerprint.FingerprintManager.CryptoObject(cipher);
            }

            if (cipherInit()) {
                cryptoObject = new android.hardware.fingerprint.FingerprintManager.CryptoObject(cipher);
                FingerprintHandler helper = new FingerprintHandler(mContext, mInfoImageView, mInfoTextView, mListener);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    /**
     * Inner class que acede aos dados do fingerprint
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

        private CancellationSignal cancellationSignal;
        private Context mContext;
        private TextView mInfoTextView;
        private ImageView mInfoImageView;

        private FingerprintControllerCallback mCallBack;

        public FingerprintHandler(Context context, ImageView imageView, TextView textView, FingerprintControllerCallback fingerprintControllerCallback) {
            mContext = context;
            mInfoImageView = imageView;
            mInfoTextView = textView;
            mCallBack = fingerprintControllerCallback;
        }

        public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
            cancellationSignal = new CancellationSignal();

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            mInfoTextView.setText(errString);
            mCallBack.fingerprintAuthResult(false);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            mInfoTextView.setText(helpString);
            mCallBack.fingerprintAuthResult(false);
        }

        @Override
        public void onAuthenticationFailed() {
            mInfoImageView.setBackground(null);
            mInfoImageView.setImageResource(R.drawable.vt_fingerprint_error);
            mInfoTextView.setText(mContext.getString(R.string.setup_fingerprint_error));
            mCallBack.fingerprintAuthResult(false);

        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            mInfoImageView.setBackground(null);
            mInfoImageView.setImageResource(R.drawable.vt_fingerprint_success);
            mInfoTextView.setText(mContext.getString(R.string.setup_fingerprint_sucesso));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCallBack.fingerprintAuthResult(true);
                }
            }, 500);
        }
    }
}