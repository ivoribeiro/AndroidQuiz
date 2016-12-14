package pt.ipp.estg.cmu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Navega on 12/10/2016.
 */

public class FileOperations {

    public static void copyImageToAppFolder(String sourcePath, String destName) throws IOException {
        File file_dest = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/CMU/");
        file_dest.mkdirs();
        File file = new File(file_dest, destName);

        //File compressed = compressImageFile(file);


        File file_source = new File(sourcePath);
        //copyFile(file_source, file_dest);

    }


    /**
     * public static void copyFile(File sourceFile, File destFile) throws IOException {
     * if (!sourceFile.exists()) {
     * return;
     * }
     * FileChannel source = null;
     * FileChannel destination = null;
     * source = new FileInputStream(sourceFile).getChannel();
     * destination = new FileOutputStream(destFile).getChannel();
     * if (destination != null && source != null) {
     * destination.transferFrom(source, 0, source.size());
     * }
     * if (source != null) {
     * source.close();
     * }
     * if (destination != null) {
     * destination.close();
     * }
     * }
     **/

    public static void copy(File src, String dstString) throws IOException {

        File dest = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/CMU/" + dstString);
        dest.createNewFile();

        File compressedFile = compressImageFile(dest);

        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(compressedFile);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }


    public static File compressImageFile(File file) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image
            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }
}
