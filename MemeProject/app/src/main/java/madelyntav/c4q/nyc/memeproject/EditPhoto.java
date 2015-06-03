package madelyntav.c4q.nyc.memeproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;

    Bitmap b;
    Bitmap bitmap;

    private Button Vanilla;
    private EditText editText;
    private EditText editText2;

    Button save;

    private File file;
    private String TAG = "GallerySaving";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        imageView = (ImageView) findViewById(R.id.mImageView);
        Vanilla = (Button) findViewById(R.id.vanilla);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);

            imageView.setImageBitmap(b);
        }else{
            //retrieve passed uri
            Uri uri = getIntent().getExtras().getParcelable("image");
            //retrieve bitmap uri from intent
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                   e.printStackTrace();
                }
            //create bitmap for use within activity
            try {
                bitmap = Bitmap.createBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                } catch (IOException e) {
                   e.printStackTrace();
                }
            imageView.setImageBitmap(bitmap);
        }

            Button share = (Button) findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b=getBitmapFromView(findViewById(R.id.meme));

                    String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), b,"title", null);
                    Uri bmpUri= Uri.parse(pathOfBmp);

                    Intent attachIntent = new Intent(Intent.ACTION_SEND);
                    attachIntent.putExtra(Intent.EXTRA_STREAM,  bmpUri);
                    attachIntent.setType("image/png");
                    startActivity(attachIntent);
                }
            });
        }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public void vanillaM (View v){
        editText.setBackgroundColor(Color.WHITE);
        editText2.setBackgroundColor(Color.WHITE);
        editText.setHint("write something here");
        editText2.setHint("and here");
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
    }

    public void storeImage(View v) {
        //converts the current imageview into a bitmap for storage purposes
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
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

}
