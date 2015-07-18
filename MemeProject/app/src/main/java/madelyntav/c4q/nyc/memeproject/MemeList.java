package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.Meme_API.Meme;
import madelyntav.c4q.nyc.memeproject.Meme_API.MemeTemplate;
import madelyntav.c4q.nyc.memeproject.Meme_API.MemeTemplateAPI;
import madelyntav.c4q.nyc.memeproject.Meme_DB.MySQLiteOpenHelper;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kadeemmaragh on 6/5/15.
 */
public class MemeList extends Activity {

    private static final String DB_FULL_PATH = "//data/data/madelyntav.c4q.nyc.memeproject/databases/myDb";
    ListView listView;
    private Uri uri;
    List<Meme> memeList;
    List<String> imageUrls;
    List<String> imageTitles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);

        listView = (ListView) findViewById(R.id.listView);

        if(dataBaseExists()){

            memeList = loadData();

            imageUrls = new ArrayList<String>();
            imageTitles = new ArrayList<String>();
            for(Meme meme : memeList){
                imageUrls.add(meme.getUrl());
                imageTitles.add(meme.getName());
            }

            listView.setAdapter(new CustomArrayAdapter(getApplicationContext(), imageTitles, imageUrls, true));
        }else{

            retrofitGetMemeTemplates();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemeList.this, EditPhoto.class);

                //uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(memeImages.get(position)) + '/' + getResources().getResourceTypeName(memeImages.get(position)) + '/' + getResources().getResourceEntryName(memeImages.get(position)));

                intent.putExtra("image", uri);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private List<Meme> loadData() {

        MySQLiteOpenHelper db = MySQLiteOpenHelper.getInstance(this);
        return db.loadData();
    }

    private void insertData(Meme meme) {
        MySQLiteOpenHelper db = MySQLiteOpenHelper.getInstance(this);
        db.insertRow(meme);
    }

    private void retrofitGetMemeTemplates(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.imgflip.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MemeTemplateAPI memeTemplateAPI = restAdapter.create(MemeTemplateAPI.class);

        memeTemplateAPI.getTemplates(new Callback<MemeTemplate>() {
            @Override
            public void success(MemeTemplate memeTemplate, Response response) {
                memeList = memeTemplate.getData().getMemes();
                imageUrls = new ArrayList<String>();
                imageTitles = new ArrayList<String>();
                for(Meme meme : memeList){
                    imageUrls.add(meme.getUrl());
                    imageTitles.add(meme.getName());
                }

                loadDBSetAdapter();
            }

            @Override
            public void failure(RetrofitError error) {
                (Toast.makeText(MemeList.this,"Couldn't load popular Memes",Toast.LENGTH_LONG)).show();
            }
        });

    }

    private void loadDBSetAdapter(){

        (new AsyncTask<Void, Void, List<Meme>>() {
            @Override
            protected List<Meme> doInBackground(Void... voids) {

                for(Meme meme : memeList) {
                    insertData(meme);
                }

                return loadData();
            }

            @Override
            protected void onPostExecute(List<Meme> memes) {
                super.onPostExecute(memes);

                listView.setAdapter(new CustomArrayAdapter(getApplicationContext(), imageTitles, imageUrls, true));
            }
        }).execute();

    }

    private boolean dataBaseExists() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


}
