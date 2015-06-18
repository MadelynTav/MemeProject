package madelyntav.c4q.nyc.memeproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int EXTERNAL_CONTENT_URI = 0;

    public static final String FILE_NAME = "photo_file";
    public static String actualFile = null;
    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void usePic(View v) {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, 0);
    }


    public void takePic(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                photoFile = File.createTempFile(FILE_NAME, ".jpg", storageDir);

                actualFile=photoFile.getCanonicalPath();

                Log.i("||||| wrote to file: ", photoFile.getCanonicalPath());


            } catch (IOException ex) {
                // Error occurred while creating the File
                // Log.e("failed to create " + FILE_NAME + " because ");
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }

            // startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        }
    }


    public void chooseMeme(View view){
        Intent popularMemeIntent = new Intent(this, MemeList.class);
        startActivity(popularMemeIntent);
    }

    @Override // saves pic and sends it to editPhoto activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent intent = new Intent(MainActivity.this, EditPhoto.class);

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Log.i("||||| image bitmap height: " + imageBitmap.getHeight(), "  width: " + imageBitmap.getWidth());
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//
//            //imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bs);
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
//            intent.putExtra("byteArray", bs.toByteArray());
//            startActivity(intent);
//
//
//        } else if (requestCode == EXTERNAL_CONTENT_URI && resultCode == RESULT_OK) {
//            //Image selected message



            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();
//
//
//
//            // get Uri from selected image
//            Uri targetUri = data.getData();
//            Bitmap bitmap = null;
//            ContentResolver cr = getContentResolver();
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//
//            //turn selected image into a Bitmap image
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(cr, targetUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        //pass image to intent
        //intent.putExtra("image", targetUri);
        startActivity(intent);

    }
}
