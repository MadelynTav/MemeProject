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

import java.util.ArrayList;
import java.util.HashMap;

public class MemeListActivity extends Activity {

    private ListView mListView;
    private HashMap<Integer, String> mMemeHashMap;
    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);
        mListView = (ListView) findViewById(R.id.listView);

        mMemeHashMap = new HashMap<>();
        mMemeHashMap.put(R.drawable.actual_advice_mallard, "Actual Advice Mallard");
        mMemeHashMap.put(R.drawable.but_thats_none_of_my_business, "But That's None Of My Business");
        mMemeHashMap.put(R.drawable.creepy_condescending_wonka, "Creepy Condescending Wonka");
        mMemeHashMap.put(R.drawable.futurama_fry, "Skeptical Fry");
        mMemeHashMap.put(R.drawable.good_guy_greg, "Good Guy Greg");
        mMemeHashMap.put(R.drawable.liam_neeson_taken, "Liam Neeson Taken");
        mMemeHashMap.put(R.drawable.one_does_not_simply, "One Does Not Simply");
        mMemeHashMap.put(R.drawable.scumbag_steve, "Scumbag Steve");
        mMemeHashMap.put(R.drawable.shut_up_and_take_my_money_fry, "Shut Up And Take My Money");
        mMemeHashMap.put(R.drawable.ten_guy, "Ten Guy");
        mMemeHashMap.put(R.drawable.the_most_interesting_man_in_the_world, "The Most Interesting Man In The World");
        mMemeHashMap.put(R.drawable.third_world_skeptical_kid, "Third World Skeptical Kid");
        mMemeHashMap.put(R.drawable.unhelpful_high_school_teacher, "Unhelpful High School Teacher");
        mMemeHashMap.put(R.drawable.yao_ming, "Yao Ming");
        mMemeHashMap.put(R.drawable.you_the_real_mvp, "You The Real MVP");

        final ArrayList<Integer> memeImages = new ArrayList<Integer>();
        ArrayList<String> memeNames = new ArrayList<String>();
        addItemsToArrays(memeImages, memeNames);
        CustomArrayAdapter memeAdapter = new CustomArrayAdapter(getApplicationContext(), memeNames, memeImages);
        mListView.setAdapter(memeAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemeListActivity.this, EditPhotoActivity.class);
                mUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(memeImages.get(position)) + '/' + getResources().getResourceTypeName(memeImages.get(position)) + '/' + getResources().getResourceEntryName(memeImages.get(position)));
                intent.putExtra("image", mUri);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Please select VANILLA or DEMO layout to begin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addItemsToArrays(ArrayList<Integer> images, ArrayList<String> titles) {
        int position = 0;
        for (Integer image : mMemeHashMap.keySet()) {

            images.add(position, image);
            titles.add(position, mMemeHashMap.get(image));
            position++;
        }
    }
}