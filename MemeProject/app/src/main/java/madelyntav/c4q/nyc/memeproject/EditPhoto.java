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



    public class EditPhoto extends Activity implements View.OnTouchListener, View.OnDragListener {

        Bitmap b;
        Bitmap bitmap;
        public static ImageView imageView;
        private int color;
        private ColorPicker colorPicker;
        private Button vanilla;
        private Button demotivational;
        private EditText editText, editText2, demoTitle, demoText;
        private ImageView demoImage;
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


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_photo);

            colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
            imageView = (ImageView) findViewById(R.id.mImageView);
            demoImage = (ImageView) findViewById(R.id.demotivationalImage);


                root = (RelativeLayout) findViewById(R.id.root);
                linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
                linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
                ten = (Button) findViewById(R.id.ten);
                fifteen = (Button) findViewById(R.id.fifteen);
                twenty = (Button) findViewById(R.id.twenty);
                twentyFive = (Button) findViewById(R.id.twentyfive);


                vanilla = (Button) findViewById(R.id.vanilla);
                demotivational = (Button) findViewById(R.id.demotivational);


                editText = (EditText) findViewById(R.id.editText);
                editText2 = (EditText) findViewById(R.id.editText2);
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

                imageView = (ImageView) findViewById(R.id.mImageView);
                demoImage = (ImageView) findViewById(R.id.demotivationalImage);
                memeLayout = (RelativeLayout) findViewById(R.id.meme);


            //----------------------------GET IMAGE FROM PREVIOUS INTENT--------------------------//

            //opens pic in this activity
                if (getIntent().hasExtra("byteArray")) {
                    b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
                    imageView = (ImageView) findViewById(R.id.mImageView);
                    vanilla = (Button) findViewById(R.id.vanilla);
                    editText = (EditText) findViewById(R.id.editText);
                    editText2 = (EditText) findViewById(R.id.editText2);
                    demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
                    demoText = (EditText) findViewById(R.id.demotivationalText);
                    imageView.setImageBitmap(b);
                    demoImage.setImageBitmap(b);


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
            //-----------------------------SHARE BUTTON ONCLICKLISTENER---------------------------//

            // Shares image via Email, Text, Bluetooth, etc...
                ImageButton share = (ImageButton) findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageView = (ImageView) findViewById(R.id.mImageView);
                        vanilla = (Button) findViewById(R.id.vanilla);
                        editText = (EditText) findViewById(R.id.editText);
                        editText2 = (EditText) findViewById(R.id.editText2);
                        editText.setHint("");
                        editText2.setHint("");
                        //opens pic in this activity
                        if (getIntent().hasExtra("byteArray")) {
                            Bundle extras = getIntent().getExtras();
                            byte[] byteArray = extras.getByteArray("byteArray");
                            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            imageView.setImageBitmap(bm);
                        }

                        ImageButton share = (ImageButton) findViewById(R.id.share);
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

                vanilla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isVanilla = true;
                        vanillaM(view);

                    }
                });

                demotivational.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isVanilla = false;
                        demotivate(view);
                    }
                });

            instance = this;
            }

        //----------------------------VANILLA AND DEMOTIVATIONAL METHODS--------------------------//


        //Sets demotivational meme editing view
        public void demotivate(View v) {

            editText = (EditText) findViewById(R.id.editText);
            editText2 = (EditText) findViewById(R.id.editText2);
            memeLayout = (RelativeLayout) findViewById(R.id.meme);
            demoImage = (ImageView) findViewById(R.id.demotivationalImage);
            demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
            demoText = (EditText) findViewById(R.id.demotivationalText);


            if (!isVanilla) {
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

        }

        //-------------------------------IMAGE STORE AND SAVE METHODS-----------------------------//

        //onClick method for the save button. Calls other methods to create the save image function
        public void storeImage(View v) {
            editText.setHint("");
            editText2.setHint("");
            editText.setCursorVisible(false);
            editText2.setCursorVisible(false);
            Bitmap image = getBitmapFromView(memeLayout);
            File pictureFile = createImageFile();
            addImageToFile(image, pictureFile);
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

                if((to.getResources().getInteger(Integer.valueOf(R.id.editText)) == (from.getResources().getInteger(Integer.valueOf(R.id.editText2))))) {

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


        // Sets vanilla font size to 10sp
        public void setTen(View v) {
            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);

            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.VISIBLE);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        }

        // Sets vanilla font size to 15sp
        public void setFifteen(View v) {
            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.VISIBLE);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        // Sets vanilla font size to 20sp
        public void setTwenty(View v) {
            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.VISIBLE);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        }

        // Sets vanilla font size to 25sp
        public void setTwentyFive(View v) {
            linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.VISIBLE);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        }

        //---------------------------VANILLA EDITTEXT FONT COLOR METHODS--------------------------//

        // Sets vanilla font to black
        public void setBlack(View v) {

            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
        }

        // Sets vanilla font to white
        public void setWhite(View v) {

            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            editText.setTextColor(Color.WHITE);
            editText2.setTextColor(Color.WHITE);
        }

        // Sets vanilla font to red
        public void setRed(View v) {
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            editText.setTextColor(Color.RED);
            editText2.setTextColor(Color.RED);
        }

        // Sets vanilla font to blue
        public void setBlue(View v) {

            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            editText.setTextColor(Color.BLUE);
            editText2.setTextColor(Color.BLUE);

        }

        //----------------------------------IMAGE EFFECTS METHODS---------------------------------//

        // Applies engrave effect to image
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
            Toast.makeText(this, "Engraved", Toast.LENGTH_SHORT).show();
        }

        // Applies inverted colors effect to image
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
            Toast.makeText(this,"Inverted",Toast.LENGTH_SHORT).show();

        }

        // Applies greyscale effect to image
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
            Toast.makeText(this,"Greyscale",Toast.LENGTH_SHORT).show();
        }

        // Applies blue shading effect to image
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
            Toast.makeText(this,"Blue",Toast.LENGTH_SHORT).show();
        }

        // Applies red shading effect to image
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
            Toast.makeText(this,"Red",Toast.LENGTH_SHORT).show();
        }

        // Applies green shading effect to image
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
            Toast.makeText(this,"Green",Toast.LENGTH_SHORT).show();
        }

        public void choseColor (View v) {
            color = colorPicker.getColor();
            linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            editText.setTextColor(color);
            editText2.setTextColor(color);
        }

        public void vanillaM(View v) {
                linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
                linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
                editText = (EditText) findViewById(R.id.editText);
                editText2 = (EditText) findViewById(R.id.editText2);
                editText.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                memeLayout = (RelativeLayout) findViewById(R.id.meme);
                memeLayout.setPadding(0, 0, 0, 0);
                demoImage = (ImageView) findViewById(R.id.demotivationalImage);
                demoTitle = (EditText) findViewById(R.id.demotivationalTitle);
                demoText = (EditText) findViewById(R.id.demotivationalText);
                linearLayout2.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                demoImage.setVisibility(View.INVISIBLE);
                demoTitle.setVisibility(View.INVISIBLE);
                demoText.setVisibility(View.INVISIBLE);
                ten.setVisibility(View.VISIBLE);
                fifteen.setVisibility(View.VISIBLE);
                twenty.setVisibility(View.VISIBLE);
                twentyFive.setVisibility(View.VISIBLE);
                colorPicker.setVisibility(View.VISIBLE);
                memeLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
        public void setTopColor(int color) {
            editText = (EditText) findViewById(R.id.editText);
            editText2 = (EditText) findViewById(R.id.editText2);
            editText.setTextColor(color);
            editText.setHintTextColor(color);
            editText.invalidate();
            editText2.setTextColor(color);
            editText2.setHintTextColor(color);
            editText2.invalidate();
        }
    }

