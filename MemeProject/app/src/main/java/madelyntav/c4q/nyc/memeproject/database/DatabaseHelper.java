package madelyntav.c4q.nyc.memeproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.R;

/**
 * Created by c4q-Allison on 7/22/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAMES = "memes.sqlite";
    public static final int DB_VERSION = 1;
    private Dao<MemeTemplate, String> memeTemplateDao;


    public DatabaseHelper(Context context) {
        super(context, DB_NAMES, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MemeTemplate.class);
            loadTemplateData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<MemeTemplate, String> getMemeTemplateDao() throws SQLException {
        if (memeTemplateDao == null) {
            memeTemplateDao = getDao(MemeTemplate.class);
        }
        return memeTemplateDao;

    }

    public static int getImageDrawable(String imageName) {

        //default to R.drawable.good_guy_greg;

        if (imageName == null) {
            return R.drawable.good_guy_greg;
        }

        if (imageName.equals("actual_advice_mallard")) return R.drawable.actual_advice_mallard;
        if (imageName.equals("but_thats_none_of_my_business")) return R.drawable.but_thats_none_of_my_business;
        if (imageName.equals("creepy_condescending_wonka")) return R.drawable.creepy_condescending_wonka;
        if (imageName.equals("futurama_fry")) return R.drawable.futurama_fry;
        if (imageName.equals("good_guy_greg")) return R.drawable.good_guy_greg;
        if (imageName.equals("liam_neeson_taken")) return R.drawable.liam_neeson_taken;
        if (imageName.equals("one_does_not_simply")) return R.drawable.one_does_not_simply;
        if (imageName.equals("scumbag_steve")) return R.drawable.scumbag_steve;
        if (imageName.equals("shut_up_and_take_my_money_fry")) return R.drawable.shut_up_and_take_my_money_fry;
        if (imageName.equals("ten_guy")) return R.drawable.ten_guy;
        if (imageName.equals("the_most_interesting_man_in_the_world")) return R.drawable.the_most_interesting_man_in_the_world;
        if (imageName.equals("unhelpful_high_school_teacher")) return R.drawable.unhelpful_high_school_teacher;
        if (imageName.equals("yao_ming")) return R.drawable.yao_ming;
        if (imageName.equals("you_the_real_mvp")) return R.drawable.you_the_real_mvp;

        //default to R.drawable.good_guy_greg;

        return R.drawable.good_guy_greg;

    }


    protected void loadTemplateData() {
        List<MemeTemplate> memeTemplateList = new ArrayList();
        MemeTemplate memeTemplate = new MemeTemplate("actual_advice_mallard", "Actual Advice Mallard");
        memeTemplateList.add(memeTemplate);
        memeTemplateList.add(new MemeTemplate("but_thats_none_of_my_business", "But That's None Of My Business"));
        memeTemplateList.add(new MemeTemplate("creepy_condescending_wonka", "Creepy Condescending Wonka"));
        memeTemplateList.add(new MemeTemplate("futurama_fry", "Skeptical Fry"));
        memeTemplateList.add(new MemeTemplate("good_guy_greg", "Good Guy Greg"));
        memeTemplateList.add(new MemeTemplate("liam_neeson_taken", "Liam Neeson Taken"));
        memeTemplateList.add(new MemeTemplate("one_does_not_simply", "One Does Not Simply"));
        memeTemplateList.add(new MemeTemplate("scumbag_steve", "Scumbag Steve"));
        memeTemplateList.add(new MemeTemplate("shut_up_and_take_my_money_fry", "Shut Up And Take My Money"));
        memeTemplateList.add(new MemeTemplate("ten_guy", "Ten Guy"));
        memeTemplateList.add(new MemeTemplate("the_most_interesting_man_in_the_world", "The Most Interesting Man In The World"));
        memeTemplateList.add(new MemeTemplate("unhelpful_high_school_teacher", "Unhelpful High School Teacher"));
        memeTemplateList.add(new MemeTemplate("yao_ming", "Yao Ming"));
        memeTemplateList.add(new MemeTemplate("you_the_real_mvp", "You The Real MVP"));


        try {
            Dao<MemeTemplate, String> memeTemplateDao = getMemeTemplateDao();
            memeTemplateDao.create(memeTemplateList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
