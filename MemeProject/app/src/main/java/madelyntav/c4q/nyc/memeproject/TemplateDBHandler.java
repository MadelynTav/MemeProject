package madelyntav.c4q.nyc.memeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TemplateDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "templates.db";

    public TemplateDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TemplateContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TemplateContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertTemplate(String name, int image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TemplateContract.TemplateEntry.COLUMN_NAME, name);
        values.put(TemplateContract.TemplateEntry.COLUMN_IMAGE, image);
        db.insert(TemplateContract.TemplateEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteTemplate(Template template) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TemplateContract.TemplateEntry.TABLE_NAME + " WHERE " + TemplateContract.TemplateEntry.COLUMN_NAME + "=\"" + template.get_name() + "\";");
    }

    public ArrayList<Template> loadTemplates() {

        String[] projection = {
                TemplateContract.TemplateEntry._ID,
                TemplateContract.TemplateEntry.COLUMN_NAME,
                TemplateContract.TemplateEntry.COLUMN_IMAGE
        };

        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Template> templates = new ArrayList<Template>();

        Cursor cursor = db.query(TemplateContract.TemplateEntry.TABLE_NAME, projection, null, null, null, null, TemplateContract.TemplateEntry.COLUMN_NAME + " ASC");
        while (cursor.moveToNext()) {
            templates.add(
                    new Template(
                            cursor.getInt(cursor.getColumnIndex(TemplateContract.TemplateEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(TemplateContract.TemplateEntry.COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(TemplateContract.TemplateEntry.COLUMN_IMAGE))
                    )
            );
        }

        cursor.close();

        return templates;
    }

}
