package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.Meme_API.Meme;

/**
 * Created by kadeemmaragh on 6/4/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {

    List<String> memeNames= null;
    List<Integer> memeImages = null;
    List<String> memeUrls = null;
    private static LayoutInflater inflater = null;
    private Context context;

    CustomArrayAdapter(Context context, List<String> names, List<Integer> images) {
        super(context, R.layout.list_item, names);
        memeNames = names;
        memeImages = images;
        this.context = context.getApplicationContext();

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    CustomArrayAdapter(Context context, List<String> names, List<String> imageUrls, boolean api) {
        super(context, R.layout.list_item, names);
        memeNames = names;
        memeUrls = imageUrls;
        this.context = context.getApplicationContext();

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        if (convertView==null) {
            convertView=newView(parent);
        }

        bindView(position, convertView);

        return(convertView);
    }

    private View newView(ViewGroup parent) {
        return inflater.inflate(R.layout.list_item, parent, false);
    }

    private void bindView(int position, View row) {
        TextView label=(TextView)row.findViewById(R.id.memeName);
        ImageView icon=(ImageView)row.findViewById(R.id.memeImage);
        label.setText(memeNames.get(position));

        if(memeUrls == null) {
            Picasso.with(context).load(memeImages.get(position)).resize(250, 250).centerCrop().into(icon);
        }else{
            Picasso.with(context).load(memeUrls.get(position)).resize(250, 250).centerCrop().into(icon);
        }
    }


}
