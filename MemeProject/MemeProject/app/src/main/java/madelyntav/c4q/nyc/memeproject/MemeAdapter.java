package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import madelyntav.c4q.nyc.memeproject.MemeBm;

/**
 * Created by alvin2 on 7/13/15.
 */
public class MemeAdapter extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    List<MemeBm> memes;

    @Override
    public int getCount() {
        // ternary; IF [condition] ? THEN : ELSE
        return memes != null ? memes.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return memes.get(i);
    }

    // leave as default
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        MemeBm memeBm = (MemeBm) getItem(i);

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.list_item, viewGroup, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.memeImage);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imageView.setImageBitmap(memeBm.getImageBitmap());

        return view;
    }

    // matches up with adapter
    public class ViewHolder {
        ImageView imageView;
    }

    public MemeAdapter(List<MemeBm> memeBmList, Context context) {
        this.memes = memeBmList;
        mLayoutInflater = LayoutInflater.from(context);
    }
}
