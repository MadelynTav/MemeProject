package gmsyrimis.c4q.nyc.sultanfragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    Button one;
    Button two;
    Button incBTN;
    TextView counterTV;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();

        one = (Button) findViewById(R.id.go_to_frag_one);
        two = (Button) findViewById(R.id.go_to_frag_two);

        incBTN = (Button) findViewById(R.id.main_inc_btn);
        counterTV = (TextView) findViewById(R.id.main_counter_tv);

        incBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int g = Integer.parseInt(counterTV.getText().toString());
                counterTV.setText(String.valueOf(++g));
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mf = new MainFragment();
                fm.beginTransaction().replace(R.id.main_container, mf).addToBackStack(null).commit();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTwo fragmentTwo = new FragmentTwo();
                fm.beginTransaction().replace(R.id.main_container, fragmentTwo).addToBackStack(null).commit();

            }
        });

    }


}
