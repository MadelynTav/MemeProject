package madelyntav.c4q.nyc.memeproject;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditPhoto extends Activity implements View.OnTouchListener {


    private NotificationManager mNotificationManager;
    private Bitmap returnedBitmap;
    Bitmap bitmap, bm, uriBm, image;
    private int delta_x;
    private int delta_y;
    public static ImageView imageView;
    public static ImageView demoImage;
    private int color;
    private ColorPicker colorPicker;
    private Button vanilla;
    private Button demotivational;
    private EditText editText, editText2, demoTitle, demoText;
    private String TAG = "GallerySaving";
    RelativeLayout memeLayout;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    Button ten;
    Button fifteen;
    Button twenty;
    Button twentyFive;
    RelativeLayout root;
    private boolean isVanilla = true;
    public static EditPhoto getInstance() {
        return instance;
    }
    static EditPhoto instance;
    ImageButton share;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);


        colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        imageView = (ImageView) findViewById(R.id.mImageView);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);

        root = (RelativeLayout) findViewById(R.id.root);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        ten = (Button) findViewById(R.id.ten);
        fifteen = (Button) findViewById(R.id.fifteen);
        twenty = (Button) findViewById(R.id.twenty);
        twentyFive = (Button) findViewById(R.id.twentyfive);
        share = (ImageButton) findViewById(R.id.share);

        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
        demoText = (EditText) findViewById(R.id.demotivationalText);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
        demoText = (EditText) findViewById(R.id.demotivationalText);
        demoTitle.setVisibility(View.GONE);
        demoText.setVisibility(View.GONE);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");
        editText.setTypeface(custom_font);
        editText2.setTypeface(custom_font);

        editText.setOnTouchListener(this);
        editText2.setOnTouchListener(this);









        //----------------------------GET IMAGE FROM PREVIOUS INTENT--------------------------//

        //opens pic in this activity

        uri = (Uri) getIntent().getExtras().get("image");
        try {
            uriBm = Bitmap.createBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
        } catch (IOException e) {
            e.printStackTrace();
        }


        imageView.setImageBitmap(uriBm);
        demoImage.setImageBitmap(uriBm);

//        if (getIntent().hasExtra("byteArray")) {
//            uriBm = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
//            imageView.setImageBitmap(uriBm);
//            demoImage.setImageBitmap(uriBm);
//
//
//        } else {
//
//            //retrieve passed uri
//
//            uri = (Uri) getIntent().getExtras().get("image");
//            //retrieve bitmap uri from intent
////            try {
////                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
////
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            //create bitmap for use within activity
////            try {
////                bitmap = Bitmap.createBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//
//            imageView.setImageURI(uri);
//            demoImage.setImageURI(uri);
////            Log.d(TAG,"bitmap height: "+ bitmap.getHeight()+"  width: "+bitmap.getWidth());
//
//        }



        //-----------------------------SHARE BUTTON ONCLICKLISTENER---------------------------//

        // Shares image via Email, Text, Bluetooth, etc...
        ImageButton share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vanilla = (Button) findViewById(R.id.vanilla);
                editText = (EditText) findViewById(R.id.editText);
                editText2 = (EditText) findViewById(R.id.editText2);
                editText.setHint("");
                editText2.setHint("");
                //opens pic in this activity
                if (getIntent().hasExtra("byteArray")) {
                    Bundle extras = getIntent().getExtras();
                    byte[] byteArray = extras.getByteArray("byteArray");
                    bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageView.setImageBitmap(bm);
                }

                ImageButton share = (ImageButton) findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), uriBm, "title", null);
                        Uri bmpUri = Uri.parse(pathOfBmp);
                        Intent attachIntent = new Intent(Intent.ACTION_SEND);
                        attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        attachIntent.setType("image/png");
                        startActivity(attachIntent);
                    }
                });

                uriBm = getBitmapFromView(memeLayout);

                String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), uriBm, "title", null);
                Uri bmpUri = Uri.parse(pathOfBmp);

                Intent attachIntent = new Intent(Intent.ACTION_SEND);
                attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                attachIntent.setType("image/png");
                startActivity(attachIntent);
            }
        });


        instance = this;

    }
    //-----------------------------SHARE BUTTON ONCLICKLISTENER---------------------------//

    // Shares image via Email, Text, Bluetooth, etc...
    public void shareImage(View v) {
        editText.setHint("");
        editText2.setHint("");
        bitmap = getBitmapFromView(memeLayout);

        String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        Uri bmpUri = Uri.parse(pathOfBmp);
        Intent attachIntent = new Intent(Intent.ACTION_SEND);
        attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        attachIntent.setType("image/png");
        startActivity(attachIntent);


    }

    //----------------------------VANILLA AND DEMOTIVATIONAL METHODS--------------------------//

    //Sets vanilla meme editing view

    //Sets demotivational meme editing view
    public void demotivate(View v) {



        memeLayout.setBackgroundColor(Color.BLACK);
        memeLayout.setPadding(20, 20, 20, 20);
        imageView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        editText2.setVisibility(View.INVISIBLE);
        demoImage.setVisibility(View.VISIBLE);
        demoTitle.setVisibility(View.VISIBLE);
        demoText.setVisibility(View.VISIBLE);
        ten.setVisibility(View.INVISIBLE);
        fifteen.setVisibility(View.INVISIBLE);
        twenty.setVisibility(View.INVISIBLE);
        twentyFive.setVisibility(View.INVISIBLE);
        colorPicker.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);

    }


    //-------------------------------IMAGE STORE AND SAVE METHODS-----------------------------//

    //onClick method for the save button. Calls other methods to create the save image function
    public void storeImage(View v) {


        editText.setCursorVisible(false);
        editText2.setCursorVisible(false);
        demoText.setCursorVisible(false);
        demoTitle.setCursorVisible(false);

        if (isVanilla) {
            if (editText.getText().toString().equals("")) {
                editText.setVisibility(View.GONE);
            } else if (editText2.getText().toString().equals("")) {
                editText2.setVisibility(View.GONE);
            }
        }


            image = getBitmapFromView(memeLayout);
            File pictureFile = createImageFile();
            addImageToFile(image, pictureFile);


        editText.setCursorVisible(true);
        editText2.setCursorVisible(true);
        demoText.setCursorVisible(true);
        demoTitle.setCursorVisible(true);



        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo);

        Bitmap logo = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.logo);

        builder.setLargeIcon(logo);
        builder.setContentTitle("Meme-ify Me");


        //Intent is created to bring you from current application context to "MainActivity" activity
        Intent resultIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //This code adds the pendingIntent to the builder which is what applies specifications to the notification !
        builder.setContentIntent(pendingIntent);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        builder.setAutoCancel(true);

        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle().bigPicture(image).setSummaryText("Image has been saved to gallery");
        builder.setStyle(style);
        Notification notification = builder.build();


        mNotificationManager.notify(1234, notification);
        Toast.makeText(this, "Saved to gallery", Toast.LENGTH_SHORT).show();




    }

    /**
     * Create a File for saving an image
     * Handles file name, and where to store it
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date());
        String imageName = "MEME_" + timeStamp + ".jpg";

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(path, imageName);
        Log.d("Path: ", path.getPath());
        return imageFile;

    }

    private void addImageToFile(Bitmap image, File file) {
        FileOutputStream fos = null;
        //Tries to add the bitmap image to the file
        try {
            fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);


        } catch (Exception e) {
            Log.d(TAG, "trying to compress image did not work" + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close(); //Closes the fileoutput stream
                    //Scans the image file that was just created so user can immediately see it in Pictures
                    try {
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(file);
                        mediaScanIntent.setData(uri);
                        sendBroadcast(mediaScanIntent);
                    } catch (Exception e) {
                        Log.d("scanIntent", "Failed: ");
                    }
                }
            } catch (IOException e) {
                Log.d(TAG, "fos did not close " + e.getMessage());
            }
        }

    }

    //------------------------------CREATE BITMAP FROM VIEW METHOD----------------------------//

    //Takes the current view and creates a bitmap representing that view.
    public Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);

        returnedBitmap = view.getDrawingCache();
//        Canvas canvas = new Canvas(returnedBitmap);
//
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//             bgDrawable.draw(canvas);
//        else
//            canvas.drawColor(Color.WHITE);
//        view.draw(canvas);

        return returnedBitmap;
    }


    //---------------------------------DRAGGING EDITTEXT METHODS------------------------------//

    // onTouch and onDrag work together to allow for views to be moved around within the layout
    //to children of that layout
    public boolean onTouch(final View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                delta_x = X - lParams.leftMargin;
                delta_y = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - delta_x;
                layoutParams.topMargin = Y - delta_y;
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }


    //----------------------------VANILLA EDITTEXT FONT SIZE METHODS--------------------------//


    // Sets vanilla font size to 10sp
    public void setTen(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }

    // Sets vanilla font size to 15sp
    public void setFifteen(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }

    // Sets vanilla font size to 20sp
    public void setTwenty(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    // Sets vanilla font size to 25sp
    public void setTwentyFive(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
    }

      //----------------------------------IMAGE EFFECTS METHODS---------------------------------//

    // Applies engrave effect to image
    public void engravedImage(View view) {
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getIntent().hasExtra("byteArray")) {

                    Bitmap engraved = ApplyFilters.engrave(uriBm);
                    imageView.setImageBitmap(engraved);
                    demoImage.setImageBitmap(engraved);
                } else {
                    Bitmap engraved = ApplyFilters.engrave(uriBm);
                    imageView.setImageBitmap(engraved);
                    demoImage.setImageBitmap(engraved);
                }
                Toast.makeText(EditPhoto.this, "Engraved", Toast.LENGTH_SHORT).show();

            }
        },1000);

    }

    // Applies inverted colors effect to image
    public void invertColors(View view) {
        Handler handler= new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getIntent().hasExtra("byteArray")) {
                    Bitmap inverted = ApplyFilters.doInvert(uriBm);
                    imageView.setImageBitmap(inverted);
                    demoImage.setImageBitmap(inverted);
                } else {
                    Bitmap inverted = ApplyFilters.doInvert(uriBm);
                    imageView.setImageBitmap(inverted);
                    demoImage.setImageBitmap(inverted);
                }
                Toast.makeText(EditPhoto.this, "Inverted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    // Applies greyscale effect to image
    public void greyscaleImage(View view) {
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getIntent().hasExtra("byteArray")) {
                    Bitmap greyscaled = ApplyFilters.doGreyscale(uriBm);
                    imageView.setImageBitmap(greyscaled);
                    demoImage.setImageBitmap(greyscaled);
                } else {
                    Bitmap greyscaled = ApplyFilters.doGreyscale(uriBm);
                    imageView.setImageBitmap(greyscaled);
                    demoImage.setImageBitmap(greyscaled);
                }
                Toast.makeText(EditPhoto.this, "Greyscale", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Applies blue shading effect to image
    public void shadingFilterBlue(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap blueShade = ApplyFilters.applyShadingFilter(uriBm, Color.BLUE);
            imageView.setImageBitmap(blueShade);
            demoImage.setImageBitmap(blueShade);
        } else {
            Bitmap blueShade = ApplyFilters.applyShadingFilter(uriBm, Color.BLUE);
            imageView.setImageBitmap(blueShade);
            demoImage.setImageBitmap(blueShade);
        }
        Toast.makeText(this, "Blue", Toast.LENGTH_SHORT).show();
    }

    // Applies red shading effect to image
    public void shadingFilterRed(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap redShade = ApplyFilters.applyShadingFilter(uriBm, Color.RED);
            imageView.setImageBitmap(redShade);
            demoImage.setImageBitmap(redShade);
        } else {
            Bitmap redShade = ApplyFilters.applyShadingFilter(uriBm, Color.RED);
            imageView.setImageBitmap(redShade);
            demoImage.setImageBitmap(redShade);
        }
        Toast.makeText(this, "Red", Toast.LENGTH_SHORT).show();
    }

    // Applies green shading effect to image
    public void shadingFilterGreen(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap greenShade = ApplyFilters.applyShadingFilter(uriBm, Color.GREEN);
            imageView.setImageBitmap(greenShade);
            demoImage.setImageBitmap(greenShade);
        } else {
            Bitmap greenShade = ApplyFilters.applyShadingFilter(uriBm, Color.GREEN);
            imageView.setImageBitmap(greenShade);
            demoImage.setImageBitmap(greenShade);
        }
        Toast.makeText(this, "Green", Toast.LENGTH_SHORT).show();
    }


    public void choseColor(View v) {
        color = colorPicker.getColor();
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(color);
        editText2.setTextColor(color);
    }


    public void vanillaM(View v) {
        editText.setVisibility(View.VISIBLE);
        editText.setHintTextColor(Color.parseColor("#BF311F"));
        editText2.setVisibility(View.VISIBLE);
        editText2.setHintTextColor(Color.parseColor("#BF311F"));
        memeLayout.setPadding(0, 0, 0, 0);
        linearLayout2.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        demoImage.setVisibility(View.INVISIBLE);
        demoTitle.setVisibility(View.GONE);
        demoText.setVisibility(View.GONE);
        ten.setVisibility(View.VISIBLE);
        fifteen.setVisibility(View.VISIBLE);
        twenty.setVisibility(View.VISIBLE);
        twentyFive.setVisibility(View.VISIBLE);
        colorPicker.setVisibility(View.VISIBLE);
        memeLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));


    }

    public void setTopColor(int color) {
        editText.setTextColor(color);
        editText.setHintTextColor(color);
        editText.invalidate();
        editText2.setTextColor(color);
        editText2.setHintTextColor(color);
        editText2.invalidate();
    }
}
