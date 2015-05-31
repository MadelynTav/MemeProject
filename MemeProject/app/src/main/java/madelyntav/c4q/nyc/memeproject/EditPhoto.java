package madelyntav.c4q.nyc.memeproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.graphics.Color.WHITE;


public class EditPhoto extends ActionBarActivity {
    private ImageView imageView;
    private Button Vanilla;
    private EditText editText;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
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
}
