package com.frndzcode.task_webskitters.view.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.view.interfaces.ImageCapturedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by SERVER on 03/03/2018.
 */

public class CaptureImage {

    private static final String TAG = CaptureImage.class.getSimpleName();
    private static final int PICK_IMAGE_REQUEST = 0x02;
    private static final int CHECK_PERMISSIONS = 0x03;
    private final ImageCapturedListener imageCapturedListener;
    private Context context;
    private String fileName;
    private AppCompatActivity activity;
    private Uri fileUri;
    private int resolution;
    private String[] permissions = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private boolean allowMultiple = false;
    private File tempFile;
    private boolean galleryImage = false;


    public CaptureImage(Context context, String fileName, int resolution, boolean multiple) {
        this.context = context;
        this.fileName = fileName;
        this.resolution = resolution;
        this.activity = (AppCompatActivity) context;
        imageCapturedListener = (ImageCapturedListener) context;
        this.allowMultiple = multiple;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void beginCapture() {
        checkCameraPermission();
    }

    private void checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)) {
                showPermissionAlert();
            } else {
                ActivityCompat.requestPermissions(activity, permissions, CHECK_PERMISSIONS);
            }
        } else if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionAlert();
            } else {
                ActivityCompat.requestPermissions(activity, permissions, CHECK_PERMISSIONS);
            }
        } else if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionAlert();
            } else {
                ActivityCompat.requestPermissions(activity, permissions, CHECK_PERMISSIONS);
            }
        } else {
            prepareImage();
        }
    }

    private void prepareImage() {

        fileUri = getImageFile();
        Log.e(TAG, "prepareImage: " + fileUri);

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        captureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        captureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (allowMultiple)
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{captureIntent});
        activity.startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }

    private Uri getImageFile() {
        File folder = new File(context.getExternalFilesDir(null), "images");
        if (!folder.exists())
            folder.mkdir();
        File file = new File(folder, System.currentTimeMillis() + ".jpg");
        tempFile = file;
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName(), file);
        context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return uri;
    }

    public void resolveImage(Uri uri) {

        if (uri != null) {
            galleryImage = true;
            this.fileUri = uri;
        }

        imageCapturedListener.imageCaptured(fileUri);

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), fileUri);

                    if (bitmap != null) {
                        if(galleryImage)
                        {
                            fileUri = getImageFile();
                            saveImageToFolder(bitmap,fileUri);
                        }

                        ExifInterface ei = new ExifInterface(tempFile.getPath());
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }

                        bitmap = getResizedBitmap(rotatedBitmap, resolution);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        int greater = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
//                        int factor = greater / resolution;
//                        if (factor == 0)
//                            factor = 1;

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
                        byte[] imageBytes = baos.toByteArray();
                        imageCapturedListener.imageProcessed(Base64.encodeToString(imageBytes, Base64.DEFAULT));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doInBackground: " + e.getMessage());
                }
                return null;
            }
        }.execute();

    }


    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void saveImageToFolder(Bitmap image, Uri uri) {
        try {
            OutputStream fOut = activity.getContentResolver().openOutputStream(uri);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showPermissionAlert() {
        new AlertDialog.Builder(context)
                .setTitle("Permission Requested")
                .setMessage("Please provide all the permission for camera to work properly")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, permissions, CHECK_PERMISSIONS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create().show();
    }


}
