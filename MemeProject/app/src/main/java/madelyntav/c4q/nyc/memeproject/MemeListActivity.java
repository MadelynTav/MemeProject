package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.database.DatabaseHelper;
import madelyntav.c4q.nyc.memeproject.database.MemeTemplate;

/**
 * Created by kadeemmaragh on 6/5/15.
 */
public class MemeListActivity extends Activity {

    ListView listView;
    HashMap<Integer, String> memePairs;
    private Uri uri;
    private DatabaseHelper databaseHelper = null;
    private Dao<MemeTemplate, String> memeTemplateDao;
    private List<MemeTemplate> memeTemplateList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);
        try {
            memeTemplateDao = getDbHelper().getMemeTemplateDao();
            memeTemplateList = memeTemplateDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listView);



        final ArrayList<Integer> memeImages = new ArrayList<Integer>();
        ArrayList<String> memeNames = new ArrayList<String>();
        addItemsToArrays(memeImages, memeNames);

        CustomArrayAdapter memeAdapter = new CustomArrayAdapter(getApplicationContext(), memeNames, memeImages);

        listView.setAdapter(memeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemeListActivity.this, EditPhoto.class);

                uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(memeImages.get(position)) + '/' + getResources().getResourceTypeName(memeImages.get(position)) + '/' + getResources().getResourceEntryName(memeImages.get(position)));

                intent.putExtra("image", uri);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();


            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public void addItemsToArrays(ArrayList<Integer> images, ArrayList<String> titles) {
        int position = 0;

        for (MemeTemplate memeTemplate :  memeTemplateList)   {

            String imageName = memeTemplate.imageName;
            int imageDrawable =  DatabaseHelper.getImageDrawable(imageName) ;
            images.add(position,  imageDrawable);
            titles.add(position, memeTemplate.title);
            position++;
        }
    }


    private DatabaseHelper getDbHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
