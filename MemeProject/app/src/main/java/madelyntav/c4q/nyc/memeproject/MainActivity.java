package madelyntav.c4q.nyc.memeproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int EXTERNAL_CONTENT_URI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mImageView = (ImageView) findViewById(R.id.mImageView);
        mImageView.setVisibility(View.INVISIBLE);
    }
    public void usePic (View v){
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, 0);
    }

    @Override // saves pic and sends it to editPhoto activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent intent = new Intent(MainActivity.this, EditPhoto.class);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
            intent.putExtra("byteArray", bs.toByteArray());
            startActivity(intent);

        } else if (requestCode == EXTERNAL_CONTENT_URI && resultCode == RESULT_OK) {
            //Image selected message
            Toast.makeText(this, "Image Selected!", Toast.LENGTH_SHORT).show();

            //get Uri from selected image
            Uri targetUri = data.getData();
            Bitmap bitmap = null;
            ContentResolver cr = getContentResolver();

            //turn selected image into a Bitmap image
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, targetUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //pass image to intent
            intent.putExtra("image", targetUri);
            mImageView.setImageBitmap(bitmap);
            startActivity(intent);

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                intent.putExtra("byteArray", bs.toByteArray());
                startActivity(intent);

            } else if (requestCode == EXTERNAL_CONTENT_URI && resultCode == RESULT_OK) {
                //Image selected message
                Toast.makeText(this, "Image Selected!", Toast.LENGTH_SHORT).show();

                //get Uri from selected image
                targetUri = data.getData();
                bitmap = null;
                cr = getContentResolver();

                //turn selected image into a Bitmap image
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(cr, targetUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //pass image to intent
                intent.putExtra("image", targetUri);
                mImageView.setImageBitmap(bitmap);
                startActivity(intent);

            }
        }
    }

    //opens camera
    public void takePic (View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}