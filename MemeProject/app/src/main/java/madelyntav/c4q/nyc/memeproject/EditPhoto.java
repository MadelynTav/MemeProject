package madelyntav.c4q.nyc.memeproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPhoto extends Activity implements View.OnTouchListener, View.OnDragListener {

    Bitmap b;
    Bitmap bitmap;
    private Button Vanilla;
    private Button demotivational;
    private ViewSwitcher switcher;
    private EditText vanillaTitle, vanillaText, demoTitle, demoText;
    private ImageView imageView, demoImage;
    private String TAG = "GallerySaving";
    RelativeLayout memeLayout;
//    LinearLayout linearLayout2;
//    LinearLayout linearLayout3;
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
        setContentView(R.layout.activity_edit_photo_2);

        initializeViews();
        setTypeAssets();
        setOnTouchListeners();
        initializeDragDropViews();

        //-------------------------------TOGGLE MEME STYLE BUTTON-----------------------------//

        // Toggle between vanilla and demotivational views
        ToggleButton toggle = (ToggleButton) findViewById(R.id.btn_meme_type);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vanillaM(buttonView);
                } else {
                    demotivate(buttonView);
                }
            }
        });


        //----------------------------GET IMAGE FROM PREVIOUS INTENT--------------------------//

        // Opens image in this activity
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imageView = (ImageView) findViewById(R.id.mImageView);
            Vanilla = (Button) findViewById(R.id.vanilla);
            vanillaTitle = (EditText) findViewById(R.id.vanillaTitle);
            vanillaText = (EditText) findViewById(R.id.vanillaText);
            demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
            demoText = (EditText) findViewById(R.id.demotivationalText);
            imageView.setImageBitmap(b);
            demoImage.setImageBitmap(b);

        } else {

            // Retrieve passed uri
            Uri uri = getIntent().getExtras().getParcelable("image");
            // Retrieve bitmap uri from intent
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // Create bitmap for use within activity
            try {
                bitmap = Bitmap.createBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            demoImage.setImageBitmap(bitmap);
        }

        //-----------------------------SHARE BUTTON ONCLICKLISTENER---------------------------//

        // Shares image via Email, Text, Bluetooth, etc...
        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView = (ImageView) findViewById(R.id.mImageView);
                Vanilla = (Button) findViewById(R.id.vanilla);
                vanillaTitle = (EditText) findViewById(R.id.vanillaTitle);
                vanillaText = (EditText) findViewById(R.id.vanillaText);
                vanillaTitle.setHint("");
                vanillaText.setHint("");

                // Opens pic in this activity
                if (getIntent().hasExtra("byteArray")) {
                    Bundle extras = getIntent().getExtras();
                    byte[] byteArray = extras.getByteArray("byteArray");
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageView.setImageBitmap(bm);
                }

                Button share = (Button) findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pathOfBmp = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);
                        Uri bmpUri = Uri.parse(pathOfBmp);
                        Intent attachIntent = new Intent(Intent.ACTION_SEND);
                        attachIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        attachIntent.setType("image/png");
                        startActivity(attachIntent);
                    }
                });

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

    //----------------------------VANILLA AND DEMOTIVATIONAL METHODS--------------------------//

    // Sets Vanilla meme editing view
    public void vanillaM(View v) {
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        vanillaTitle = (EditText) findViewById(R.id.vanillaTitle);
        vanillaText = (EditText) findViewById(R.id.vanillaText);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);
        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
        demoText = (EditText) findViewById(R.id.demotivationalText);

//        linearLayout2.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        vanillaTitle.setVisibility(View.VISIBLE);
        vanillaText.setVisibility(View.VISIBLE);
        demoImage.setVisibility(View.INVISIBLE);
        demoTitle.setVisibility(View.INVISIBLE);
        demoText.setVisibility(View.INVISIBLE);
    }

    // Sets demotivational meme editing view
    public void demotivate(View v) {
        vanillaTitle = (EditText) findViewById(R.id.vanillaTitle);
        vanillaText = (EditText) findViewById(R.id.vanillaText);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);
        demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
        demoText = (EditText) findViewById(R.id.demotivationalText);

        memeLayout.setBackgroundColor(Color.TRANSPARENT);
        imageView.setVisibility(View.INVISIBLE);
        vanillaTitle.setVisibility(View.INVISIBLE);
        vanillaText.setVisibility(View.INVISIBLE);
        demoImage.setVisibility(View.VISIBLE);
        demoTitle.setVisibility(View.VISIBLE);
        demoText.setVisibility(View.VISIBLE);
    }

    //-------------------------------IMAGE STORE AND SAVE METHODS-----------------------------//

    // onClick method for the save button. Calls other methods to create the save image function
    public void storeImage(View v) {
        vanillaTitle.setHint("");
        vanillaText.setHint("");
        Bitmap image = getBitmapFromView(memeLayout);
        File pictureFile = createImageFile();
        addImageToFile(image, pictureFile);
    }

    // Creates a File for saving an image. Handles file name and save destination
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

    //------------------------------CREATE BITMAP FROM VIEW METHOD----------------------------//
    //-----------Takes the current view and creates a bitmap representing that view-----------//

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
    // to children of that layout

    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) { // Action_Down means a pressed gesture had started, view has been set in motion
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v); // Creates an image that the system displays during the drag and drop operation.
            v.startDrag(null, shadowBuilder, v, 0);
            v.isInEditMode();
            v.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }

    public boolean onDrag(View v, DragEvent e) {
        if (e.getAction() == DragEvent.ACTION_DROP) { // If the shadow has been released within the view
            View view = (View) e.getLocalState();
            ViewGroup from = (ViewGroup) view.getParent();

            LinearLayout to = (LinearLayout) v;

            if ((to.getResources().getInteger(Integer.valueOf(R.id.vanillaTitle)) == (from.getResources().getInteger(Integer.valueOf(R.id.vanillaText))))) {


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
    //---------------These methods modify the font size of the Vanilla EditTexts--------------//

    public void setTen(View v) {
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//
//        linearLayout2.setVisibility(View.GONE);
//        linearLayout3.setVisibility(View.VISIBLE);
        vanillaTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        vanillaText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }

    public void setFifteen(View v) {
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout2.setVisibility(View.GONE);
//        linearLayout3.setVisibility(View.VISIBLE);
        vanillaTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        vanillaText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }

    public void setTwenty(View v) {
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout2.setVisibility(View.GONE);
//        linearLayout3.setVisibility(View.VISIBLE);
        vanillaTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        vanillaText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    public void setTwentyfive(View v) {
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout2.setVisibility(View.GONE);
//        linearLayout3.setVisibility(View.VISIBLE);
        vanillaTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        vanillaText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
    }

    //---------------------------VANILLA EDITTEXT FONT COLOR METHODS--------------------------//
    //-----------------These methods modify the color of the Vanilla EditTexts----------------//

    public void setBlack(View v) {
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout3.setVisibility(View.GONE);
        vanillaTitle.setVisibility(View.VISIBLE);
        vanillaText.setVisibility(View.VISIBLE);
    }

    public void setWhite(View v) {
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout3.setVisibility(View.GONE);
        vanillaTitle.setVisibility(View.VISIBLE);
        vanillaText.setVisibility(View.VISIBLE);
        vanillaTitle.setTextColor(Color.WHITE);
        vanillaText.setTextColor(Color.WHITE);
    }

    public void setRed(View v) {
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout3.setVisibility(View.GONE);
        vanillaTitle.setVisibility(View.VISIBLE);
        vanillaText.setVisibility(View.VISIBLE);
        vanillaTitle.setTextColor(Color.RED);
        vanillaText.setTextColor(Color.RED);
    }

    public void setBlue(View v) {
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
//        linearLayout3.setVisibility(View.GONE);
        vanillaTitle.setVisibility(View.VISIBLE);
        vanillaText.setVisibility(View.VISIBLE);
        vanillaTitle.setTextColor(Color.BLUE);
        vanillaText.setTextColor(Color.BLUE);
    }

    //----------------------------------IMAGE EFFECTS METHODS---------------------------------//
    //---------------These methods apply various colors and effects to an image---------------//

    public void engravedImage(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap engraved = ApplyFilters.engrave(b);
            imageView.setImageBitmap(engraved);
            demoImage.setImageBitmap(engraved);
        } else {
            Bitmap engraved = ApplyFilters.engrave(bitmap);
            imageView.setImageBitmap(engraved);
            demoImage.setImageBitmap(engraved);
        }
        Toast.makeText(this, "Engraved!", Toast.LENGTH_SHORT).show();
    }

    public void invertColors(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap inverted = ApplyFilters.doInvert(b);
            imageView.setImageBitmap(inverted);
            demoImage.setImageBitmap(inverted);
        } else {
            Bitmap inverted = ApplyFilters.doInvert(bitmap);
            imageView.setImageBitmap(inverted);
            demoImage.setImageBitmap(inverted);
        }
        Toast.makeText(this, "Inverted!", Toast.LENGTH_SHORT).show();

    }

    public void greyscaleImage(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap greyscaled = ApplyFilters.doGreyscale(b);
            imageView.setImageBitmap(greyscaled);
            demoImage.setImageBitmap(greyscaled);
        } else {
            Bitmap greyscaled = ApplyFilters.doGreyscale(bitmap);
            imageView.setImageBitmap(greyscaled);
            demoImage.setImageBitmap(greyscaled);
        }
        Toast.makeText(this, "Old School Flow!", Toast.LENGTH_SHORT).show();
    }

    public void shadingFilterBlue(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap blueShade = ApplyFilters.applyShadingFilter(b, Color.BLUE);
            imageView.setImageBitmap(blueShade);
            demoImage.setImageBitmap(blueShade);
        } else {
            Bitmap blueShade = ApplyFilters.applyShadingFilter(bitmap, Color.BLUE);
            imageView.setImageBitmap(blueShade);
            demoImage.setImageBitmap(blueShade);
        }
        Toast.makeText(this, "BLUE!", Toast.LENGTH_SHORT).show();
    }

    public void shadingFilterRed(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap redShade = ApplyFilters.applyShadingFilter(b, Color.RED);
            imageView.setImageBitmap(redShade);
            demoImage.setImageBitmap(redShade);
        } else {
            Bitmap redShade = ApplyFilters.applyShadingFilter(bitmap, Color.RED);
            imageView.setImageBitmap(redShade);
            demoImage.setImageBitmap(redShade);
        }
        Toast.makeText(this, "RED!", Toast.LENGTH_SHORT).show();
    }

    public void shadingFilterGreen(View view) {

        if (getIntent().hasExtra("byteArray")) {
            Bitmap greenShade = ApplyFilters.applyShadingFilter(b, Color.GREEN);
            imageView.setImageBitmap(greenShade);
            demoImage.setImageBitmap(greenShade);
        } else {
            Bitmap greenShade = ApplyFilters.applyShadingFilter(bitmap, Color.GREEN);
            imageView.setImageBitmap(greenShade);
            demoImage.setImageBitmap(greenShade);
        }
        Toast.makeText(this, "GREEN FACES ONLY!!", Toast.LENGTH_SHORT).show();
    }

    public void reflectionEffect(View view) {
        if (getIntent().hasExtra("byteArray")) {
            Bitmap reflected = ApplyFilters.applyReflection(b);
            imageView.setImageBitmap(reflected);
            demoImage.setImageBitmap(reflected);
        } else {
            Bitmap reflected = ApplyFilters.applyReflection(bitmap);
            imageView.setImageBitmap(reflected);
            demoImage.setImageBitmap(reflected);
        }
        Toast.makeText(this, "Reflected!", Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------REFACTORING---------------------------------------//
    //-------------------------------Cleaning up onCreate method------------------------------//

    private void initializeViews() {
        imageView = (ImageView) findViewById(R.id.mImageView);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);

        root = (RelativeLayout) findViewById(R.id.root);
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        ten = (Button) findViewById(R.id.ten);
        fifteen = (Button) findViewById(R.id.fifteen);
        twenty = (Button) findViewById(R.id.twenty);
        twentyfive = (Button) findViewById(R.id.twentyfive);
        black = (Button) findViewById(R.id.black);
        white = (Button) findViewById(R.id.white);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        Vanilla = (Button) findViewById(R.id.vanilla);

        vanillaTitle = (EditText) findViewById(R.id.vanillaTitle);
        vanillaText = (EditText) findViewById(R.id.vanillaText);
    }

    private void setTypeAssets() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");
        vanillaTitle.setTypeface(custom_font);
        vanillaText.setTypeface(custom_font);
    }

    private void setOnTouchListeners() {
        vanillaTitle.setOnTouchListener(this);
        vanillaText.setOnTouchListener(this);
    }

    private void initializeDragDropViews() {
        //Drag and drop layouts for drag and drop EditText feature
        LinearLayout textTop = (LinearLayout) findViewById(R.id.textTop);
        LinearLayout textMid = (LinearLayout) findViewById(R.id.textMid);
        LinearLayout textBot = (LinearLayout) findViewById(R.id.textBottom);

        // FIXME: This throws a Null Pointer Exception -- consolidate the above views.
        textBot.setOnDragListener(this);
        textMid.setOnDragListener(this);
        textTop.setOnDragListener(this);

        imageView = (ImageView) findViewById(R.id.mImageView);
        demoImage = (ImageView) findViewById(R.id.demotivationalImage);
        memeLayout = (RelativeLayout) findViewById(R.id.meme);
    }

    //--------------------------------------DIALOG BOXES--------------------------------------//
    //------Helps reduce on-screen clutter, makes certain features more easily accessible-----//

    public Dialog editTextColor(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_color)
                .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int selection) {
                        // Call text color setting methods based on value of selection

                        if (selection == 0) {
                            setWhite(v);
                        } else if (selection == 1) {
                            setBlack(v);
                        } else if (selection == 2) {
                            setRed(v);
                        } else if (selection == 3) {
                            setBlue(v);
                        }
                    }
                });
        return builder.create();
    }

    public Dialog editTextSize(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_size)
                .setItems(R.array.size_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int selection) {
                        // Call text size setting methods based on value of selection

                        if (selection == 0) {
                            setTen(v);
                        } else if (selection == 1) {
                            setFifteen(v);
                        } else if (selection == 2) {
                            setTwenty(v);
                        } else if (selection == 3) {
                            setTwentyfive(v);
                        }
                    }
                });
        return builder.create();
    }

    public Dialog editEffect(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_effect)
                .setItems(R.array.effect_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int selection) {
                        // Call effect setting methods based on value of selection

                        if (selection == 0) {
                            engravedImage(v);
                        } else if (selection == 1) {
                            invertColors(v);
                        } else if (selection == 2) {
                            greyscaleImage(v);
                        } else if (selection == 3) {
                            reflectionEffect(v);
                        } else if (selection == 4) {
                            shadingFilterRed(v);
                        } else if (selection == 5) {
                            shadingFilterGreen(v);
                        } else {
                            shadingFilterBlue(v);
                        }
                    }
                });
        return builder.create();
    }
}