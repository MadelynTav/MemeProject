package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by elvisboves on 7/17/15.
 */
public class ImageDatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String MYBD = "mydb.db";
    private static final int VERSION = 1;
    private static ImageDatabaseHelper mImageDatabaseHelper;


    public static synchronized ImageDatabaseHelper getInstance (Context mContext) {
        if (mImageDatabaseHelper == null) {
            mImageDatabaseHelper = new ImageDatabaseHelper(mContext.getApplicationContext());
        }
        return mImageDatabaseHelper;
    }

    public ImageDatabaseHelper(Context context) {
        super(context, MYBD, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, PreExistingMeme.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, PreExistingMeme.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRow(int picture) throws SQLException{
        PreExistingMeme coder = new PreExistingMeme(picture);
        getDao(PreExistingMeme.class).create(coder);
    }

    public List<PreExistingMeme> loadData() throws SQLException {
        return getDao(PreExistingMeme.class).queryForAll();
    }
}
