package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.MemeBm;

/**
 * Created by kgalligan on 7/11/15.
 */
public class MySQLiteOpenHelper extends OrmLiteSqliteOpenHelper {

    public static final String MYDB = "mydb";
    public static final int VERSION = 1;

    private static MySQLiteOpenHelper INSTANCE;
    private Context context;


    public static synchronized MySQLiteOpenHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MySQLiteOpenHelper(context.getApplicationContext());
        }

        return INSTANCE;
    }

    private MySQLiteOpenHelper(Context context) {
        super(context, MYDB, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, madelyntav.c4q.nyc.memeproject.MemeBm.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, madelyntav.c4q.nyc.memeproject.MemeBm.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void insertData() throws SQLException {
        Dao<MemeBm, ?> dao = getDao(MemeBm.class);

        dao.delete(dao.deleteBuilder().prepare());

        Bitmap icon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.actual_advice_mallard);
        Bitmap icon3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.but_thats_none_of_my_business);
        Bitmap icon4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.creepy_condescending_wonka);
        Bitmap icon5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.futurama_fry);
        Bitmap icon6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.good_guy_greg);
        Bitmap icon7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.liam_neeson_taken);
        Bitmap icon8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.one_does_not_simply);
        Bitmap icon9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.scumbag_steve);
        Bitmap icon10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.shut_up_and_take_my_money_fry);
        Bitmap icon11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ten_guy);
        Bitmap icon12 = BitmapFactory.decodeResource(context.getResources(), R.drawable.the_most_interesting_man_in_the_world);
        Bitmap icon13 = BitmapFactory.decodeResource(context.getResources(), R.drawable.third_world_skeptical_kid);
        Bitmap icon14 = BitmapFactory.decodeResource(context.getResources(), R.drawable.unhelpful_high_school_teacher);
        Bitmap icon15 = BitmapFactory.decodeResource(context.getResources(), R.drawable.yao_ming);
        Bitmap icon16 = BitmapFactory.decodeResource(context.getResources(), R.drawable.you_the_real_mvp);

        insertRow(icon2);
        insertRow(icon3);
        insertRow(icon4);
        insertRow(icon5);
        insertRow(icon6);
        insertRow(icon7);
        insertRow(icon8);
        insertRow(icon9);
        insertRow(icon10);
        insertRow(icon11);
        insertRow(icon12);
        insertRow(icon13);
        insertRow(icon14);
        insertRow(icon15);
        insertRow(icon16);

    }

    private void insertRow(Bitmap bm) throws SQLException {

                MemeBm memeBm = new MemeBm();
                memeBm.setImageBlob(getBitmapAsByteArray(bm));
                getDao(MemeBm.class).create(memeBm);
    }


    public List<MemeBm> loadData() throws SQLException {
        return getDao(MemeBm.class).queryForAll();
    }

}
