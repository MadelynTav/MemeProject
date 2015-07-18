package madelyntav.c4q.nyc.memeproject.Meme_DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import madelyntav.c4q.nyc.memeproject.Meme_API.Meme;

/**
 * Created by c4q-anthonyf on 7/17/15.
 */
public class MySQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    final static String MYDB = "myDb";
    final static int VERSION = 1;

    public static MySQLiteOpenHelper INSTANCE;

    public static synchronized MySQLiteOpenHelper getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new MySQLiteOpenHelper(context.getApplicationContext());
        }

        return INSTANCE;
    }

    public MySQLiteOpenHelper(Context context) {
        super(context, MYDB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void insertRow(Meme meme){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MemeTemplateDB.COLUMN_NAME_MEME_ID, meme.getId());
        values.put(MemeTemplateDB.COLUMN_NAME_NAMES, meme.getName());
        values.put(MemeTemplateDB.COLUMN_NAME_URLS, meme.getUrl());

        db.insertOrThrow(
                MemeTemplateDB.TABLE_NAME,
                null,
                values);
    }

    public List<Meme> loadData()
    {
        String[] projection = {
                MemeTemplateDB._ID,
                MemeTemplateDB.COLUMN_NAME_NAMES,
                MemeTemplateDB.COLUMN_NAME_URLS
        };

        SQLiteDatabase db = getWritableDatabase();

        List<Meme> memes = new ArrayList<>();

        Cursor cursor = db.query(
                MemeTemplateDB.TABLE_NAME,
                projection,
                null, //where
                null, //where args
                null, //group by (ignore)
                null, //having (ignore)
                MemeTemplateDB._ID + " desc");//order by
        while(cursor.moveToNext())
        {
            memes.add(new Meme(cursor.getString(
                            cursor.getColumnIndex(MemeTemplateDB.COLUMN_NAME_NAMES)),
                    cursor.getString(
                            cursor.getColumnIndex(MemeTemplateDB.COLUMN_NAME_URLS))));
        }

        cursor.close();

        return memes;
    }

    public static abstract class MemeTemplateDB implements BaseColumns {
        public static final String TABLE_NAME = "popular_memes";
        public static final String COLUMN_NAME_MEME_ID = "meme_id";
        public static final String COLUMN_NAME_NAMES = "names";
        public static final String COLUMN_NAME_URLS = "urls";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MemeTemplateDB.TABLE_NAME + " (" +
            MemeTemplateDB._ID + " INTEGER PRIMARY KEY," +
            MemeTemplateDB.COLUMN_NAME_MEME_ID + " TEXT NOT NULL UNIQUE, " +
            MemeTemplateDB.COLUMN_NAME_NAMES + " TEXT," +
            MemeTemplateDB.COLUMN_NAME_URLS + " TEXT" +
            " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MemeTemplateDB.TABLE_NAME;
    
}
