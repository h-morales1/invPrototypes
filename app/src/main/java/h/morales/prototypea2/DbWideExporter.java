package h.morales.prototypea2;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.*;

public class DbWideExporter {
    // second attempt at renaming files
    public static void renameFile(Context context, Product product) {
        //get Uri from product
        Uri fileUri = Uri.parse(product.getProductPicturePath());
        String oldFilePath = fileUri.getPath();
        String newFilePath = (product.getProductName()+".jpg");
        File imageFile = new File(oldFilePath);
        File nimageFile = new File(newFilePath);
        boolean success = imageFile.renameTo(nimageFile);
        if(success){
            Log.d("changed name", "renameFile: SUCCESS: " + imageFile.getPath());
        }
        Log.d("fileName", "renameFile: filename: " + fileUri.getPath());
    }

    // function to zip up a list of files, given a list of URIs
    public static void zipper(Context context, Product product) throws IOException {
        Uri fileUri = Uri.parse(product.getProductPicturePath());
        File fl = new File(product.getProductPicturePath());
        String sourceFile = fl.getCanonicalPath();

        // Get the external files directory
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        // Specify the path for the compressed file in the external files directory
        String compressedFilePath = new File(externalFilesDir, "compressed.zip").getAbsolutePath();

        FileOutputStream fos = new FileOutputStream(compressedFilePath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.closeEntry();
        zipOut.close();
        fis.close();
        fos.close();

        Log.d("zipper", "zipper: zip location: " + compressedFilePath);
    }

    public static void zipFile(Context context, Uri fileUri) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(fileUri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                FileInputStream fileInputStream = (FileInputStream) contentResolver.openInputStream(fileUri);

                File zipFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), displayName + ".zip");
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));

                ZipEntry zipEntry = new ZipEntry(displayName);
                zipOutputStream.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, length);
                }

                zipOutputStream.closeEntry();
                zipOutputStream.close();
                fileInputStream.close();

                Log.d("ZipUtil", "File zipped successfully: " + zipFile.getAbsolutePath());
                // Add this line to log the destination path
                Log.d("ZipUtil", "Zip file location: " + context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));

            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Creates a copy on external storage of file from its uri.
        The name is just the product name
     */
    public static boolean copyFileFromUri(Context context, Uri sourceUri, Product product) {
        try {
            // Get the external files directory
            File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            if (externalFilesDir == null) {
                return false; // External storage not available
            }

            // Create a destination file in the external files directory
            String destinationFileName = product.getProductName(); // Set your desired file name
            File destinationFile = new File(externalFilesDir, destinationFileName);

            // Open an InputStream for the source file using ContentResolver
            ContentResolver contentResolver = context.getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(sourceUri);

            if (inputStream == null) {
                return false; // Unable to open InputStream for the source file
            }

            // Open an OutputStream for the destination file
            OutputStream outputStream = new FileOutputStream(destinationFile);

            // Copy the content from InputStream to OutputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close the streams
            inputStream.close();
            outputStream.close();

            // Inform the MediaStore about the new file for it to be visible in file explorers
            MediaStore.Images.Media.insertImage(
                    context.getContentResolver(),
                    destinationFile.getAbsolutePath(),
                    destinationFileName,
                    null
            );

            return true; // File successfully copied
        } catch (IOException e) {
            e.printStackTrace();
            return false; // An error occurred during the copy process
        }
    }

}
