package madelyntav.c4q.nyc.memeproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String MYDB = "mydb.db";
    private static final int VERSION = 1;
    private static DatabaseHelper mHelper;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return mHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, MYDB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MemeTemplate.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MemeTemplate.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRow(String title, Integer resource_id) throws SQLException {
        MemeTemplate memeTemplate = new MemeTemplate(title, resource_id);
        getDao(MemeTemplate.class).create(memeTemplate);
    }

    public List<MemeTemplate> loadData() throws SQLException {
        return getDao(MemeTemplate.class).queryForAll();
    }
}
