package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

public class MemeListActivity extends Activity {

    private ListView mListView;
    private Uri mUri;
    private DatabaseHelper mHelper;
    private MemeTemplateAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);
        mListView = (ListView) findViewById(R.id.listView);

        new DatabaseTask().execute();


    }

    private class DatabaseTask extends AsyncTask<Void, Void, List<MemeTemplate>> {

        @Override
        protected List<MemeTemplate> doInBackground(Void... voids) {
            mHelper = DatabaseHelper.getInstance(getApplicationContext());
            try {
                if (mHelper.loadData().size() == 0) {
                    mHelper.insertRow("Actual Advice Mallard", R.drawable.actual_advice_mallard);
                    mHelper.insertRow("But That's None Of My Business", R.drawable.but_thats_none_of_my_business);
                    mHelper.insertRow("Creepy Condescending Wonka", R.drawable.creepy_condescending_wonka);
                    mHelper.insertRow("Skeptical Fry", R.drawable.futurama_fry);
                    mHelper.insertRow("Good Guy Greg", R.drawable.good_guy_greg);
                    mHelper.insertRow("Liam Neeson Taken", R.drawable.liam_neeson_taken);
                    mHelper.insertRow("One Does Not Simply", R.drawable.one_does_not_simply);
                    mHelper.insertRow("Scumbag Steve", R.drawable.scumbag_steve);
                    mHelper.insertRow("Shut Up And Take My Money", R.drawable.shut_up_and_take_my_money_fry);
                    mHelper.insertRow("Ten Guy", R.drawable.ten_guy);
                    mHelper.insertRow("The Most Interesting Man In The World", R.drawable.the_most_interesting_man_in_the_world);
                    mHelper.insertRow("Third World Skeptical Kid", R.drawable.third_world_skeptical_kid);
                    mHelper.insertRow("Unhelpful High School Teacher", R.drawable.unhelpful_high_school_teacher);
                    mHelper.insertRow("Yao Ming", R.drawable.yao_ming);
                    mHelper.insertRow("You The Real MVP", R.drawable.you_the_real_mvp);
                }
                return mHelper.loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<MemeTemplate> memeTemplates) {
            mAdapter = new MemeTemplateAdapter(MemeListActivity.this, memeTemplates);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MemeListActivity.this, EditPhotoActivity.class);
                    int resourceId = mAdapter.getItem(position).getResource_id();
                    mUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(resourceId) + '/' + getResources().getResourceTypeName(resourceId) + '/' + getResources().getResourceEntryName(resourceId));
                    intent.putExtra("image", mUri);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}