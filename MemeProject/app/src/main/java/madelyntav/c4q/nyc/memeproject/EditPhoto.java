package madelyntav.c4q.nyc.memeproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.net.URI;


public class EditPhoto extends ActionBarActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
    }


    public void sharePhoto(View view){

        imageView=(ImageView)findViewById(R.id.mImageView);
        if(imageView.getDrawable()!=null) {
            Uri imageUri = Uri.parse(String.valueOf(imageView.getDrawable()));

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.setType("image/jpeg");
            startActivity(intent);
        }

    }
}

