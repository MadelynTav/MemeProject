package gmsyrimis.c4q.nyc.sultanfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by c4q-george on 7/18/15.
 */
public class FragmentTwo extends Fragment {

    TextView gv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View g = inflater.inflate(R.layout.fragment_two_layout, container, false);
        gv = (TextView) g.findViewById(R.id.frag_two_tv);
        return g;
    }


}
