
package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
/*
The refactored code by Yuliya Kaleda is included in the comments with /*
 */

public class MainActivity extends ActionBarActivity {

    /*
    it is a good convention to initialize static variables first
     */
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int EXTERNAL_CONTENT_URI = 0;
    private static final String BYTE_ARRAY = "byteArray";

    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        isExternalStorageReadable();

        mImageView = (ImageView) findViewById(R.id.mImageView);
        mImageView.setVisibility(View.INVISIBLE);
    }

   /*
    *
    *
    * Why do you need this code?

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    *
    *
    */

    public void usePic(View v) {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /*
        *
        *
        using constants is always more preferable rather than just their values, they are created for across app usage;
        instead of 0 it is better to use EXTERNAL_CONTENT_URI.
        I added Null Pointer Check for choosePictureIntent to eliminate the possible error (the same as you did for takePictureIntent)
        *
        *
        */

        if (choosePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(choosePictureIntent, 0);
        }
    }


    public void takePic(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    // saves pic and sends it to editPhoto activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent intent = new Intent(MainActivity.this, EditPhoto.class);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
            /*
            for "byteArray" create a static variable
             */
            intent.putExtra(BYTE_ARRAY, bs.toByteArray());
            startActivity(intent);


        } else if (requestCode == EXTERNAL_CONTENT_URI && resultCode == RESULT_OK) {
            //Image selected message
            Toast.makeText(this, "Image Selected!", Toast.LENGTH_SHORT).show();

           // get Uri from selected image
            Uri targetUri = data.getData();

            // There is no necessity to convert image into Bitmap, because in your intent to the EditPhoto Activity
            //you are sending Uri. Plus you don't need a bitmap to set ImageView, you can just use URI. Redundant code
            /*
            *
            *
            * Bitmap bitmap = null;
            ContentResolver cr = getContentResolver();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();

            //turn selected image into a Bitmap image
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, targetUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            *
            *
            */

            //pass image to intent
            intent.putExtra("image", targetUri);
            mImageView.setImageURI(targetUri); //changed to setImageURI
            startActivity(intent);

        }
    }
}
