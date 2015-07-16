package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemeTemplateAdapter extends BaseAdapter {

    private List<MemeTemplate> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    @Bind(R.id.memeImage) ImageView mImageView;
    @Bind(R.id.memeName) TextView mTextView;

    public MemeTemplateAdapter(Context mContext, List<MemeTemplate> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MemeTemplate getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_meme_template, parent, false);
        }

        ButterKnife.bind(this, convertView);
        Picasso.with(mContext).load(getItem(position).getResource_id()).into(mImageView);
        mTextView.setText(getItem(position).getTitle());

        return convertView;
    }
}