package madelyntav.c4q.nyc.memeproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;


    public static final int MEDIA_TYPE_IMAGE = 1;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        imageView = (ImageView) findViewById(R.id.imageView);

        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
        }
    }


    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        //Checks to see if an SDCard is mounted
        if (Environment.getExternalStorageState() != null) {

            //Creates a folder on the external SDCard, under Pictures called 'MemePictures'
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MemePictures");

            // Checks to see if the folder 'MemePictures' was created from the above code.
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MemePictures", "failed to create directory");
                    return null;
                }
            }

            // Create a media file name, timeStamp is for appending to file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "IMG_" + timeStamp + ".jpg");
            }
            else {
                return null;
            }

            return mediaFile;
        }
        else {
            return null;
        }

    }

    //Method for saving a meme
    public void savePicture() {
        file = getOutputMediaFile(MEDIA_TYPE_IMAGE); // create a file to save the image
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap(); //gets the bitmap from the current imageview

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        }
        catch(Exception e){
            Toast.makeText(this,"Could not save picture",Toast.LENGTH_LONG);
        }
    }

}
