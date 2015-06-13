package madelyntav.c4q.nyc.memeproject;

import android.widget.Button;
import android.content.Context;
import android.app.Activity;
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
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EditPhoto extends Activity implements View.OnTouchListener, View.OnDragListener {

    private static final String FILE_EXTERNAL = "myFile.jpg";
    private static final String TAG_YULIYA = "logTags";

    Bitmap b;
    Bitmap bitmap;
    /*
    the next two variables are never used, could be deleted
     */
    private Button Vanilla;
    private Button demotivational;
    private Button save;
    private EditText editText, editText2, demoTitle, demoText;
    private ImageView imageView, demoImage;
    private String TAG = "GallerySaving";
    private RelativeLayout memeLayout;
    private LinearLayout linearLayout2, linearLayout3, textTop, textMid, textBot;

    Button ten;
    Button fifteen;
    Button twenty;
    Button twentyfive;
    Button black;
    Button white;
    Button red;
    Button blue;
    RelativeLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

            /*
            onCreate is a pretty big method, a good idea to break it down into smaller pieces
             */
        setViews();
        setListeners();
        shareButtonOnClick();
        getIntentInfo();
        extraEffects();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMeme(getBitmapFromView(memeLayout));

            }
        });

    }

    //method to set up views
    public void setViews() {
        imageView = (ImageView) findViewById(R.id.mImageView);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);
        root = (RelativeLayout) findViewById(R.id.root);
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
        Vanilla = (Button) findViewById(R.id.vanilla);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        textTop = (LinearLayout) findViewById(R.id.textTop);
        textMid = (LinearLayout) findViewById(R.id.textMid);
        textBot = (LinearLayout) findViewById(R.id.textBottom);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
        demoText = (EditText) findViewById(R.id.demotivationalText);
        save = (Button) findViewById(R.id.save);

    }

    //method to set up listeners
    public void setListeners() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");

        editText.setTypeface(custom_font);
        editText2.setTypeface(custom_font);

        editText.setOnTouchListener(this);
        editText2.setOnTouchListener(this);

        textBot.setOnDragListener(this);
        textMid.setOnDragListener(this);
        textTop.setOnDragListener(this);

    }


    //method to implement shareButton
    public void shareButtonOnClick() {
        // Shares image via Email, Text, Bluetooth, etc...

        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*

you dont need to set up views of activity in every method. One time is enough OnCreatwe. Redundant code
 */
//                        imageView = (ImageView) findViewById(R.id.mImageView);
//                        Vanilla = (Button) findViewById(R.id.vanilla);
//                        editText = (EditText) findViewById(R.id.editText);
//                        editText2 = (EditText) findViewById(R.id.editText2);
                editText.setHint("");
                editText2.setHint("");
                //opens pic in this activity
                if (getIntent().hasExtra("byteArray")) {
                    Bundle extras = getIntent().getExtras();
                    byte[] byteArray = extras.getByteArray("byteArray");
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageView.setImageBitmap(bm);
                }

                b = getBitmapFromView(memeLayout);

                String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);
                Uri bmpUri = Uri.parse(pathOfBmp);

                Intent attachIntent = new Intent(Intent.ACTION_SEND);
                attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                attachIntent.setType("image/png");
                startActivity(attachIntent);
            }
        });
    }

    //method to extract info from Intent
    public void getIntentInfo() {
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);

                /*
                *
                *
                *
                *
                NO NEED TO SET UP VIEWS IN EVERY METHOD
                *
                *
                */
//                imageView = (ImageView) findViewById(R.id.mImageView);
//                Vanilla = (Button) findViewById(R.id.vanilla);
//                editText = (EditText) findViewById(R.id.editText);
//                editText2 = (EditText) findViewById(R.id.editText2);
//                demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
//                demoText = (EditText) findViewById(R.id.demotivationalText);
//                imageView.setImageBitmap(b);
//                demoImage.setImageBitmap(b);

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
            demoImage.setImageBitmap(bitmap);
        }
    }
    //----------------------------VANILLA AND DEMOTIVATIONAL METHODS--------------------------//

    //Sets Vanilla meme editing view
    public void vanillaM(View v) {
            /*
            *
            * Again no need to set up views in every method
            *
             */
//            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//            editText = (EditText) findViewById(R.id.editText);
//            editText2 = (EditText) findViewById(R.id.editText2);
//            memeLayout = (RelativeLayout) findViewById(R.id.meme);
//            demoImage = (ImageView) findViewById(R.id.demotivationalImage);


        linearLayout2.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        demoImage.setVisibility(View.INVISIBLE);
        demoTitle.setVisibility(View.INVISIBLE);
        demoText.setVisibility(View.INVISIBLE);
    }

    //Sets demotivational meme editing view
    public void demotivate(View v) {

            /*
            *
            * the same thing=))) Somebody really loves to play with all these layout elements. Once you set them up
            * in onCreate they will be there. They never disappear=))))
            *
             */
//            editText = (EditText) findViewById(R.id.editText);
//            editText2 = (EditText) findViewById(R.id.editText2);
//            memeLayout = (RelativeLayout) findViewById(R.id.meme);
//            demoImage = (ImageView) findViewById(R.id.demotivationalImage);
//            demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
//            demoText = (EditText) findViewById(R.id.demotivationalText);


        memeLayout.setBackgroundColor(Color.BLACK);
        imageView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        editText2.setVisibility(View.INVISIBLE);
        demoImage.setVisibility(View.VISIBLE);
        demoTitle.setVisibility(View.VISIBLE);
        demoText.setVisibility(View.VISIBLE);

    }

    //-------------------------------IMAGE STORE AND SAVE METHODS-----------------------------//

    //onClick method for the save button. Calls other methods to create the save image function

    /*
    *
    * Great job creating separate methods for creating a file and getting Bitmap from the layout!!!
    *
    *
     */
    public void storeImage(View v) {
        editText.setHint("");
        editText2.setHint("");
        Bitmap image = getBitmapFromView(memeLayout);
        File pictureFile = createImageFile();
        addImageToFile(image, pictureFile);

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

            Toast.makeText(this, "Saved image to camera pictures", Toast.LENGTH_SHORT).show();

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

    /*
    storing meme externally - Yuliya Kaleda
     */

    public void saveMeme(Bitmap bm) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getApplicationContext(),"Media is mounted",Toast.LENGTH_LONG).show();
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File myFile = new File(root, FILE_EXTERNAL);
            try {
                Toast.makeText(getApplicationContext(),"FOS",Toast.LENGTH_LONG).show();
                FileOutputStream fos = new FileOutputStream(myFile);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                //optional if the user wants to see the image in the folder
                try {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(myFile);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } catch (Exception e) {
                    Log.d("scanIntent", "Failed: ");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.d(TAG, "fos did not close " + e.getMessage());
            }
        }
        else {
            Log.d(TAG_YULIYA, "External Storage is not available");
        }
    }

    //------------------------------CREATE BITMAP FROM VIEW METHOD----------------------------//

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


    //---------------------------------DRAGGING EDITTEXT METHODS------------------------------//

    /*
    *
    * I was trying to add drag and drop functionality to our app, but could not figure out
    * drop stage. Good to know. Thank you!!!
    *
    *
     */
    // onTouch and onDrag work together to allow for views to be moved around within the layout
    //to children of that layout
    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {//Action_Down means a pressed gesture had started, view has been set in motion
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);//Creates an image that the system displays during the drag and drop operation.
            v.startDrag(null, shadowBuilder, v, 0);
            v.isInEditMode();
            v.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }


    public boolean onDrag(View v, DragEvent e) {
        if (e.getAction() == DragEvent.ACTION_DROP) {//if the shadow has been released within the view
            View view = (View) e.getLocalState();
            ViewGroup from = (ViewGroup) view.getParent();

            LinearLayout to = (LinearLayout) v;

            if ((to.getResources().getInteger(Integer.valueOf(R.id.editText)) == (from.getResources().getInteger(Integer.valueOf(R.id.editText2))))) {
            }
            from.removeView(view);
            to.addView(view);
            view.setVisibility(View.VISIBLE);

            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        return true;
    }


    //----------------------------VANILLA EDITTEXT FONT SIZE METHODS--------------------------//

    /*

    The same thing..set up redundant views=) I commented them out


     */
    // Sets Vanilla font size to 10sp
    public void setTen(View v) {
//            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);

        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }

    // Sets Vanilla font size to 15sp
    public void setFifteen(View v) {
//            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }

    // Sets Vanilla font size to 20sp
    public void setTwenty(View v) {
//            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    // Sets Vanilla font size to 25sp
    public void setTwentyfive(View v) {
//            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
    }

    //---------------------------VANILLA EDITTEXT FONT COLOR METHODS--------------------------//

    // Sets Vanilla font to black
    public void setBlack(View v) {

//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
    }

    // Sets Vanilla font to white
    public void setWhite(View v) {

        // linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.WHITE);
        editText2.setTextColor(Color.WHITE);
    }

    // Sets Vanilla font to red
    public void setRed(View v) {
//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.RED);
        editText2.setTextColor(Color.RED);
    }

    // Sets Vanilla font to blue
    public void setBlue(View v) {

//            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.BLUE);
        editText2.setTextColor(Color.BLUE);
    }


    //----------------------------------IMAGE EFFECTS METHODS---------------------------------//

    /*
    *
    *

    So much of the code was repeated in the next block. It would be easier to setOnClickListener dynamically
    and do switch cases. I rewrote the whole block of code
    In xml file I commented out onClick for every ImageView
     *
     *
     */
    public void extraEffects() {
        ImageView engrave = (ImageView) findViewById(R.id.engrave);
        ImageView invert = (ImageView) findViewById(R.id.invert);
        ImageView greyScale = (ImageView) findViewById(R.id.greyscale);
        ImageView blueShade = (ImageView) findViewById(R.id.blueShade);
        ImageView greenShade = (ImageView) findViewById(R.id.greenShade);
        ImageView redShade = (ImageView) findViewById(R.id.redShade);
        ImageView reflect = (ImageView) findViewById(R.id.reflect);

        ArrayList<ImageView> arrayList = new ArrayList<ImageView>();
        arrayList.add(engrave);
        arrayList.add(invert);
        arrayList.add(greyScale);
        arrayList.add(blueShade);
        arrayList.add(greenShade);
        arrayList.add(redShade);
        arrayList.add(reflect);

        for (ImageView im : arrayList) {
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getIntent().hasExtra("byteArray")) {
                        switch (view.getId()) {

                            case R.id.reflect:
                                Bitmap reflected = ApplyFilters.applyReflection(b);
                                imageView.setImageBitmap(reflected);
                                demoImage.setImageBitmap(reflected);
                                break;
                            case R.id.engrave:
                                Bitmap engraved = ApplyFilters.engrave(b);
                                imageView.setImageBitmap(engraved);
                                demoImage.setImageBitmap(engraved);
                                break;
                            case R.id.invert:
                                Bitmap invert = ApplyFilters.doInvert(b);
                                imageView.setImageBitmap(invert);
                                demoImage.setImageBitmap(invert);
                                break;
                            case R.id.greyscale:
                                Bitmap greySc = ApplyFilters.doGreyscale(b);
                                imageView.setImageBitmap(greySc);
                                demoImage.setImageBitmap(greySc);
                                break;
                            case R.id.redShade:
                                Bitmap redShade = ApplyFilters.applyShadingFilter(b, Color.RED);
                                imageView.setImageBitmap(redShade);
                                demoImage.setImageBitmap(redShade);
                                break;
                            case R.id.greenShade:
                                Bitmap green = ApplyFilters.applyShadingFilter(b, Color.GREEN);
                                imageView.setImageBitmap(green);
                                demoImage.setImageBitmap(green);
                                break;
                            case R.id.blueShade:
                                Bitmap blue = ApplyFilters.applyShadingFilter(b, Color.BLUE);
                                imageView.setImageBitmap(blue);
                                demoImage.setImageBitmap(blue);
                                break;
                        }
                    } else {
                        switch (view.getId()) {
                            case R.id.reflect:
                                Bitmap reflected = ApplyFilters.applyReflection(bitmap);
                                imageView.setImageBitmap(reflected);
                                demoImage.setImageBitmap(reflected);
                                break;

                            case R.id.engrave:
                                Bitmap engraved = ApplyFilters.engrave(bitmap);
                                imageView.setImageBitmap(engraved);
                                demoImage.setImageBitmap(engraved);
                                break;
                            case R.id.invert:
                                Bitmap inverted = ApplyFilters.doInvert(bitmap);
                                imageView.setImageBitmap(inverted);
                                demoImage.setImageBitmap(inverted);
                                break;
                            case R.id.greyscale:
                                Bitmap greyscaled = ApplyFilters.doGreyscale(bitmap);
                                imageView.setImageBitmap(greyscaled);
                                demoImage.setImageBitmap(greyscaled);
                                break;
                            case R.id.redShade:
                                Bitmap redShade = ApplyFilters.applyShadingFilter(bitmap, Color.RED);
                                imageView.setImageBitmap(redShade);
                                demoImage.setImageBitmap(redShade);
                                break;
                            case R.id.greenShade:
                                Bitmap greenShade = ApplyFilters.applyShadingFilter(bitmap, Color.GREEN);
                                imageView.setImageBitmap(greenShade);
                                demoImage.setImageBitmap(greenShade);
                                break;
                            case R.id.blueShade:
                                Bitmap blueShade = ApplyFilters.applyShadingFilter(bitmap, Color.BLUE);
                                imageView.setImageBitmap(blueShade);
                                demoImage.setImageBitmap(blueShade);
                                break;
                        }
                    }
                }
            });
        }
    }
}
