package com.doutya.mediacontroller;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportMedia {

    Activity activity;
    boolean isCamera;
    String CurrentCamImgPath;

    String Title="Choose Media";
    String Message="";



    public ImportMedia(Activity activity) {
        this.activity = activity;
    }

    public void GetMedia(int MEDIA_REQ_CODE) {
        new AlertDialog.Builder(activity)
                .setTitle(Title)
                .setMessage(Message)
                .setNegativeButton("Image", (dialogInterface, i) -> {
                    isCamera=false;
                    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    activity.startActivityForResult(in, MEDIA_REQ_CODE);
                })
                .setPositiveButton("Video", (dialogInterface, i) -> {
                    isCamera=false;
                    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                    activity.startActivityForResult(in, MEDIA_REQ_CODE);
                })
                .setNeutralButton("Open Camera", (dialogInterface, i) -> {
                    isCamera=true;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File

                        }
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(activity,
                                    "com.doutya.mediacontroller.fileprovider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            activity.startActivityForResult(takePictureIntent, MEDIA_REQ_CODE);
                        }

                }).show();


    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Doutya_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/Doutya/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        CurrentCamImgPath = image.getAbsolutePath();
        Log.e("MediaController:",image.getAbsolutePath());
        return image;
    }

    public String getImageData(Intent data) {
        if (isCamera) {
            return CurrentCamImgPath;
        }
        if (data != null) {
                return GetStringPath(data.getData());
            }
        return null;
    }

    private String GetStringPath(Uri data) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(data, filePathColumn, null, null, null);
        if (cursor == null) {
            return data.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
    }

    public void setDialogueTitle(String title) {
        Title = title;
    }

    public void setDialogueMessage(String message) {
        Message = message;
    }

    public static Bitmap WaterMark(Bitmap src, String watermark) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(12);
        paint.setAntiAlias(true);
        paint.setUnderlineText(true);
        canvas.drawText(watermark, 20, 25, paint);

        return result;
    }


}
