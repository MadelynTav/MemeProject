package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
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
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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


public class EditPhoto extends Activity implements View.OnTouchListener, View.OnDragListener {


    Bitmap bitmap, image;
    private EditText editText, editText2, demoTitle, demoText;
    private ImageView imageView, demoImage;
    private String TAG = "GallerySaving";
    RelativeLayout memeLayout, root;
    LinearLayout linearLayout2, linearLayout3;
    Button ten, fifteen, twenty, twentyfive, black, white, red, blue, share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        imageView = (ImageView) findViewById(R.id.mImageView);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);

        root = (RelativeLayout) findViewById(R.id.root);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
        ten = (Button) findViewById(R.id.ten);
        fifteen = (Button) findViewById(R.id.fifteen);
        twenty = (Button) findViewById(R.id.twenty);
        twentyfive = (Button) findViewById(R.id.twentyfive);
        black = (Button) findViewById(R.id.black);
        white = (Button) findViewById(R.id.white);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        share = (Button) findViewById(R.id.share);


        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        demoText = (EditText) findViewById(R.id.demotivationalText);
        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");

        editText.setTypeface(custom_font);
        editText2.setTypeface(custom_font);

        editText.setOnTouchListener(this);
        editText2.setOnTouchListener(this);


        //Drag and drop layouts for drag and drop EditText feature
        LinearLayout textTop = (LinearLayout) findViewById(R.id.textTop);
        LinearLayout textMid = (LinearLayout) findViewById(R.id.textMid);
        LinearLayout textBot = (LinearLayout) findViewById(R.id.textBottom);

        textBot.setOnDragListener(this);
        textMid.setOnDragListener(this);
        textTop.setOnDragListener(this);


        //----------------------------GET IMAGE FROM PREVIOUS INTENT--------------------------//

        //opens pic in this activity
        if (getIntent().hasExtra("byteArray")) {
            bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);

        }
        else {

            //retrieve passed uri
            Uri uri = getIntent().getExtras().getParcelable("image");
            //create bitmap for use within activity
            try {
                bitmap = Bitmap.createBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
            //Sets the imageviews to the updated bitmap
            imageView.setImageBitmap(bitmap);
            demoImage.setImageBitmap(bitmap);

        //-----------------------------SHARE BUTTON ONCLICKLISTENER---------------------------//

        // Shares image via Email, Text, Bluetooth, etc...
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = getBitmapFromView(memeLayout);
                String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                Uri bmpUri = Uri.parse(pathOfBmp);
                Intent attachIntent = new Intent(Intent.ACTION_SEND);
                attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                attachIntent.setType("image/png");
                startActivity(attachIntent);
            }
        });

    }

    //----------------------------VANILLA AND DEMOTIVATIONAL METHODS--------------------------//

    //Sets Vanilla meme editing view
    public void vanillaM(View v) {
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
    public void storeImage(View v) {
        image = getBitmapFromView(memeLayout);
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
                        Log.d(TAG, "Scanning image file failed");
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


    // Sets Vanilla font size to 10sp
    public void setTen(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }

    // Sets Vanilla font size to 15sp
    public void setFifteen(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }

    // Sets Vanilla font size to 20sp
    public void setTwenty(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    // Sets Vanilla font size to 25sp
    public void setTwentyfive(View v) {
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.VISIBLE);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
    }

    //---------------------------VANILLA EDITTEXT FONT COLOR METHODS--------------------------//

    // Sets Vanilla font to black
    public void setBlack(View v) {
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.BLACK);
        editText2.setTextColor(Color.BLACK);
    }

    // Sets Vanilla font to white
    public void setWhite(View v) {
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.WHITE);
        editText2.setTextColor(Color.WHITE);
    }

    // Sets Vanilla font to red
    public void setRed(View v) {
        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.RED);
        editText2.setTextColor(Color.RED);
    }

    // Sets Vanilla font to blue
    public void setBlue(View v) {

        linearLayout3.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText.setTextColor(Color.BLUE);
        editText2.setTextColor(Color.BLUE);
    }


    //----------------------------------IMAGE EFFECTS METHODS---------------------------------//

    // Applies engrave effect to image
    public void engravedImage(View view) {
            Bitmap engraved = ApplyFilters.engrave(bitmap);
            imageView.setImageBitmap(engraved);
            demoImage.setImageBitmap(engraved);

        Toast.makeText(this, "Engraved!", Toast.LENGTH_SHORT).show();
    }

    // Applies inverted colors effect to image
    public void invertColors(View view) {
            Bitmap inverted = ApplyFilters.doInvert(bitmap);
            imageView.setImageBitmap(inverted);
            demoImage.setImageBitmap(inverted);

        Toast.makeText(this, "Inverted!", Toast.LENGTH_SHORT).show();

    }

    // Applies greyscale effect to image
    public void greyscaleImage(View view) {
            Bitmap greyscaled = ApplyFilters.doGreyscale(bitmap);
            imageView.setImageBitmap(greyscaled);
            demoImage.setImageBitmap(greyscaled);

        Toast.makeText(this, "Old School Flow!", Toast.LENGTH_SHORT).show();
    }

    // Applies blue shading effect to image
    public void shadingFilterBlue(View view) {
            Bitmap blueShade = ApplyFilters.applyShadingFilter(bitmap, Color.BLUE);
            imageView.setImageBitmap(blueShade);
            demoImage.setImageBitmap(blueShade);

        Toast.makeText(this, "BLUE!", Toast.LENGTH_SHORT).show();
    }

    // Applies red shading effect to image
    public void shadingFilterRed(View view) {
            Bitmap redShade = ApplyFilters.applyShadingFilter(bitmap, Color.RED);
            imageView.setImageBitmap(redShade);
            demoImage.setImageBitmap(redShade);

        Toast.makeText(this, "RED!", Toast.LENGTH_SHORT).show();
    }

    // Applies green shading effect to image
    public void shadingFilterGreen(View view) {
            Bitmap greenShade = ApplyFilters.applyShadingFilter(bitmap, Color.GREEN);
            imageView.setImageBitmap(greenShade);
            demoImage.setImageBitmap(greenShade);

        Toast.makeText(this, "GREEN FACES ONLY!!", Toast.LENGTH_SHORT).show();
    }

    // Applies reflection effect to image
    public void reflectionEffect(View view) {
            Bitmap reflected = ApplyFilters.applyReflection(bitmap);
            imageView.setImageBitmap(reflected);
            demoImage.setImageBitmap(reflected);

        Toast.makeText(this, "Reflected!", Toast.LENGTH_SHORT).show();
    }
}