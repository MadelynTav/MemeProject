package madelyntav.c4q.nyc.memeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
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
    RelativeLayout memeLayout;

    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    Button ten;
    Button fifteen;
    Button twenty;
    Button twentyfive;
    Button black;
    Button white;
    Button red;
    Button blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        imageView = (ImageView) findViewById(R.id.mImageView);
        Vanilla = (Button) findViewById(R.id.vanilla);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        ten = (Button) findViewById(R.id.ten);
        fifteen = (Button) findViewById(R.id.fifteen);
        twenty = (Button) findViewById(R.id.twenty);
        twentyfive = (Button) findViewById(R.id.twentyfive);
        black = (Button) findViewById(R.id.black);
        white = (Button) findViewById(R.id.white);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);

        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);

            imageView.setImageBitmap(b);
        } else {
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
                b = getBitmapFromView(findViewById(R.id.meme));

                String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);
                Uri bmpUri = Uri.parse(pathOfBmp);

                Intent attachIntent = new Intent(Intent.ACTION_SEND);
                attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                attachIntent.setType("image/png");
                startActivity(attachIntent);
            }
        });
    }

    public void vanillaM (View v){
        linearLayout2.setVisibility(View.VISIBLE);
    }

    public void setTen (View v){
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }
    public void setFifteen (View v){
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }
    public void setTwenty (View v){
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }
    public void setTwentyfive (View v){
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
    }

    public void setBlack (View v){
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
    }
    public void setWhite (View v){
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.WHITE);
        editText2.setTextColor(Color.WHITE);
    }
    public void setRed (View v){
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.RED);
        editText2.setTextColor(Color.RED);
    }
    public void setBlue (View v){
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.BLUE);
        editText2.setTextColor(Color.BLUE);
    }

    //onClick method for the save button. Calls other methods to create the save image function
    public void storeImage(View v) {
        Bitmap image = getBitmapFromView(memeLayout);
        File pictureFile = createImageFile();
        addImageToFile(image, pictureFile);
    }

    /**
     * Create a File for saving an image or video
     * Handles file name, and where to store it
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date());
        String imageName = "MEME_" + timeStamp + ".jpg";

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File (path, imageName);
        Log.d("Path: ", path.getPath());
        return imageFile;
    }

    private void addImageToFile(Bitmap image, File file){
        FileOutputStream fos=null;
        //Tries to add the bitmap image to the file
        try{
        fos=new FileOutputStream(file);
        image.compress(Bitmap.CompressFormat.PNG,90,fos);

        Toast.makeText(this,"Saved image to camera pictures",Toast.LENGTH_SHORT).show();

        }catch(Exception e){
        Log.d(TAG,"trying to compress image did not work"+e.getMessage());
        }finally{
        try{
        if(fos!=null){
        fos.close(); //Closes the fileoutput stream
        //Scans the image file that was just created so user can immediately see it in Pictures
        try{
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri=Uri.fromFile(file);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
        }catch(Exception e){
        Log.d("scanIntent","Failed: ");
        }
        }
        }catch(IOException e){
        Log.d(TAG,"fos did not close "+e.getMessage());
        }
        }
        }

    //Takes the current view and creates a bitmap representing that view.
    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);

        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public void engrainImage(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.engrave(b);
        imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.engrave(bitmap);
            imageView.setImageBitmap(engraved);
        }
    }

    public void invertColors(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.doInvert(b);
            imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.doInvert(bitmap);
            imageView.setImageBitmap(engraved);
        }
    }

    public void greyscaleImage(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.doGreyscale(b);
            imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.doGreyscale(bitmap);
            imageView.setImageBitmap(engraved);
        }
    }

    public void shadingFilterBlue(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.applyShadingFilter(b, Color.BLUE);
            imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.applyShadingFilter(bitmap, Color.BLUE);
            imageView.setImageBitmap(engraved);
        }
    }

    public void shadingFilterRed(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.applyShadingFilter(b, Color.RED);
            imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.applyShadingFilter(bitmap, Color.RED);
            imageView.setImageBitmap(engraved);
        }
    }

    public void shadingFilterGreen(View view){
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.applyShadingFilter(b,Color.GREEN);
            imageView.setImageBitmap(engraved);
        }else{
            Bitmap engraved = ApplyFilters.applyShadingFilter(bitmap, Color.GREEN);
            imageView.setImageBitmap(engraved);
        }
    }



}





