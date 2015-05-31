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
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
=======
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.graphics.Color.WHITE;
>>>>>>> ray


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;
<<<<<<< HEAD
    Bitmap b;
=======
    private Button Vanilla;
    private EditText editText;
    private EditText editText2;
>>>>>>> ray

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
<<<<<<< HEAD
        imageView = (ImageView) findViewById(R.id.mImageView);




        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
=======
        imageView = (ImageView) findViewById(R.id.imageView);
        Vanilla = (Button) findViewById(R.id.vanilla);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        //opens pic in this activity
        if(getIntent().hasExtra("byteArray")) {
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("byteArray");
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imageView.setImageBitmap(bm);
        }
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clear(editText);
//
//           }
//        });
//        editText2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clear2(editText2);
//
//        }
//    });
}
>>>>>>> ray

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
<<<<<<< HEAD

=======
    public void vanillaM (View v){
        editText.setBackgroundColor(WHITE);
        editText2.setBackgroundColor(WHITE);
        editText.setHint("write something here");
        editText2.setHint("and here");
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
    }
//    public void clear (View v){
//        editText.setText("");
//        editText.setBackground(null);
//    }
//    public void clear2 (View v){
//        editText2.setText("");
//        editText2.setBackground(null);
//    }
>>>>>>> ray
}
