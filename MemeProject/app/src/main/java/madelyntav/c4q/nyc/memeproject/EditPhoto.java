package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;
    Button save;

    private File file;
    private String TAG = "GallerySaving";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        imageView = (ImageView) findViewById(R.id.mImageView);

        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
        }

    }

    public void storeImage(View v) {
        //converts the current imageview into a bitmap for storage purposes
        //Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        //RelativeLayout rel = (RelativeLayout) findViewById(R.id.meme);
        Bitmap image = createBitmap();

        //Calls the getOutputMediaFile method to create the file the image will be stored in
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            Toast.makeText(this, "Saved image to camera", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /** Create a File for saving an image or video
     * Handles file name, and where to store it */
    private  File getOutputMediaFile(){

        // path to /data/data/yourapp/app_data/imageDir
        File mediaStorageDir = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(mediaStorageDir, "profile.jpg");
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name, format is
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MEME_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public Bitmap createBitmap(){
        RelativeLayout view = (RelativeLayout)findViewById(R.id.meme);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        Toast.makeText(this,"created Bitmap method worked", Toast.LENGTH_SHORT).show();
        return bm;
    }


}
