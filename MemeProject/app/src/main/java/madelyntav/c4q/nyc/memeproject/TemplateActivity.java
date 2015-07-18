package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class TemplateActivity extends Activity {

    ListView listView;
    TemplateDBAsyncTask templateDBAsyncTask;
    TemplateAdapter templateAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);

        listView = (ListView) findViewById(R.id.listView);
        templateDBAsyncTask.execute();

        try {
            templateAdapter = new TemplateAdapter(this, templateDBAsyncTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        listView.setAdapter(templateAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TemplateActivity.this, EditPhoto.class);

                //todo: select image from db and load
            }
        });
    }
}
