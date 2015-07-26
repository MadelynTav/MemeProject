package gmsyrimis.c4q.nyc.sultanfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by c4q-george on 7/18/15.
 */
public class MainFragment extends Fragment {
    TextView tv;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_layout, container, false);
        tv = (TextView) v.findViewById(R.id.frag_counter_tv);
        btn = (Button) v.findViewById(R.id.frag_inc_btn);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int g = Integer.parseInt(tv.getText().toString());
                tv.setText(String.valueOf(++g));
            }
        });
    }
}
