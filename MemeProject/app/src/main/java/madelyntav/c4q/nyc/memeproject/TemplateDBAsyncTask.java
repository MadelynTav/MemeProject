package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class TemplateDBAsyncTask extends AsyncTask<Void, Void, List<Template>> {

    public Context mContext;

    @Override
    protected List<Template> doInBackground(Void... params) {
        TemplateDBHandler handler = new TemplateDBHandler(mContext);
        handler.insertTemplate("Actual Advice Mallard", R.drawable.actual_advice_mallard);
        handler.insertTemplate("But That's None Of My Business", R.drawable.but_thats_none_of_my_business);
        handler.insertTemplate("Creepy Condescending Wonka", R.drawable.creepy_condescending_wonka);
        handler.insertTemplate("Skeptical Fry", R.drawable.futurama_fry);
        handler.insertTemplate("Good Guy Greg", R.drawable.good_guy_greg);
        handler.insertTemplate("Liam Neeson Taken", R.drawable.liam_neeson_taken);
        handler.insertTemplate("One Does Not Simply", R.drawable.one_does_not_simply);
        handler.insertTemplate("Scumbag Steve", R.drawable.scumbag_steve);
        handler.insertTemplate("Shut Up And Take My Money", R.drawable.shut_up_and_take_my_money_fry);
        handler.insertTemplate("Ten Guy", R.drawable.ten_guy);
        handler.insertTemplate("The Most Interesting Man In The World", R.drawable.the_most_interesting_man_in_the_world);
        handler.insertTemplate("Third World Skeptical Kid", R.drawable.third_world_skeptical_kid);
        handler.insertTemplate("Unhelpful High School Teacher", R.drawable.unhelpful_high_school_teacher);
        handler.insertTemplate("Yao Ming", R.drawable.yao_ming);
        handler.insertTemplate("You The Real MVP", R.drawable.you_the_real_mvp);
        return handler.loadTemplates();
    }

    @Override
    protected void onPostExecute(List<Template> templates) {
        super.onPostExecute(templates);
    }
}
