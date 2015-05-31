package madelyntav.c4q.nyc.memeproject;

import android.content.Intent;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        imageView = (ImageView) findViewById(R.id.mImageView);




        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);

            Button share = (Button) findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), b,"title", null);
                    Uri bmpUri= Uri.parse(pathOfBmp);
                    Intent attachIntent = new Intent(Intent.ACTION_SEND);
                    attachIntent.putExtra(Intent.EXTRA_STREAM,  bmpUri);
                    attachIntent.setType("image/png");
                    startActivity(attachIntent);
                }
            });


        }
    }

}
