package madelyntav.c4q.nyc.memeproject;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int EXTERNAL_CONTENT_URI = 0;
    protected static Uri targetUri;
    private String stringVariable = "file:///sdcard/_pictureholder_id.jpg";



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
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, EXTERNAL_CONTENT_URI);
    }


    public void takePic(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.parse(stringVariable));

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void chooseMeme(View view){
        Intent popularMemeIntent = new Intent(MainActivity.this, TemplateActivity.class);
        startActivity(popularMemeIntent);
    }

    @Override // saves pic and sends it to editPhoto activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(MainActivity.this, EditPhoto.class);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
            intent.putExtra("byteArray", bs.toByteArray());
            startActivity(intent);

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
//            intent.putExtra("byteArray", bs.toByteArray());

            targetUri = Uri.parse(stringVariable);
            intent.putExtra("image", targetUri);




        }

        if (requestCode == EXTERNAL_CONTENT_URI && resultCode == RESULT_OK & data!= null) {
            //Image selected message
            Toast.makeText(this, "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();


//            // TODO: causing small photo error?!
//            // get Uri from selected image
            targetUri = data.getData();
            intent.putExtra("image", targetUri);


//            Bitmap bitmap = null;
//            ContentResolver cr = getContentResolver();
//
//            // TODO: is this code used??
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//
//            //turn selected image into a Bitmap image
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(cr, targetUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//


        }
        //pass image to intent
        if (targetUri != null) {
            startActivity(intent);
        }

    }

}