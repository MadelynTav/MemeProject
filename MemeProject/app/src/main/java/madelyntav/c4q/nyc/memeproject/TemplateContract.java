package madelyntav.c4q.nyc.memeproject;

import android.provider.BaseColumns;

public class TemplateContract {

    public static final String PRIMARY_KEY = " PRIMARY KEY";
    public static final String INT_TYPE = " INTEGER";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TemplateEntry.TABLE_NAME +
                    " (" +
                    TemplateEntry._ID + INT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    TemplateEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    TemplateEntry.COLUMN_IMAGE + INT_TYPE +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TemplateEntry.TABLE_NAME;

    public static abstract class TemplateEntry implements BaseColumns {
        public static final String TABLE_NAME = "TEMPLATES";
        //inherit _ID constant from BaseColumns for the unique id of each row
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_IMAGE = "IMAGE";
    }
}
