package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TemplateAdapter extends BaseAdapter {

    Context context;
    List<Template> templates;
    LayoutInflater layoutInflater;
    ImageView memeImage;
    TextView memeName;

    public TemplateAdapter(Context context, List<Template> templates) {
        this.context = context;
        this.templates = templates;
    }

    @Override
    public int getCount() {
        return templates.size();
    }

    @Override
    public Object getItem(int position) {
        return templates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            memeImage = (ImageView) convertView.findViewById(R.id.memeImage);
            memeName = (TextView) convertView.findViewById(R.id.memeName);
        }

        memeImage.setImageResource(templates.get(position).get_image());
        memeName.setText(templates.get(position).get_name());

        return convertView;
    }
}
