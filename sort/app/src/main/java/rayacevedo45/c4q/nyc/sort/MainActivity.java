package rayacevedo45.c4q.nyc.sort;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    EditText editTextA,editTextB,editTextC,editTextD,editTextE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextA = (EditText) findViewById(R.id.editTextA);
        editTextB = (EditText) findViewById(R.id.editTextB);
        editTextC = (EditText) findViewById(R.id.editTextC);
        editTextD = (EditText) findViewById(R.id.editTextD);
        editTextE = (EditText) findViewById(R.id.editTextE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void sort (View v){
        final ArrayList <Integer> integerArrayList = new ArrayList<>();
        int b = Integer.parseInt(editTextB.getText().toString());
        int c = Integer.parseInt(editTextC.getText().toString());
        int d = Integer.parseInt(editTextD.getText().toString());
        int e = Integer.parseInt(editTextE.getText().toString());


        integerArrayList.add(Integer.parseInt(editTextA.getText().toString()));
        integerArrayList.add(b);
        integerArrayList.add(c);
        integerArrayList.add(d);
        integerArrayList.add(e);

        editTextA.setText("");
        editTextB.setText("");
        editTextC.setText("");
        editTextD.setText("");
        editTextE.setText("");

        // TODO: clear edit texts
        // TODO (optional): make output go to a single textview, make sure to append each number

        //Collections.sort(integerArrayList);

        Handler handler = new Handler();
        // TODO (optional)
        // for integer in integerArrayList
        // make a new runnable
        // post it using the handler
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                editTextA.setText(integerArrayList.get(0) + "");

            }
        };
        handler.postDelayed(runnable, integerArrayList.get(0) * 1000);

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                editTextB.setText(integerArrayList.get(1)+"");
            }
        };
        handler.postDelayed(runnable2, integerArrayList.get(1) * 1000);

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                editTextC.setText(integerArrayList.get(2)+"");
            }
        };
        handler.postDelayed(runnable3, integerArrayList.get(2) * 1000);



        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                editTextD.setText(integerArrayList.get(3)+"");

            }
        };
        handler.postDelayed(runnable4, integerArrayList.get(3) * 1000);

        Runnable runnable5 = new Runnable() {
            @Override
            public void run() {
                editTextE.setText(integerArrayList.get(4)+"");

            }
        };
        handler.postDelayed(runnable5, integerArrayList.get(4) * 1000);


    }
}

