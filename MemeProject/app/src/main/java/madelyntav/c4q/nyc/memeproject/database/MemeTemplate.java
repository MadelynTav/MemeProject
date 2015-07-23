package madelyntav.c4q.nyc.memeproject.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by c4q-Allison on 7/22/15.
 */
@DatabaseTable(tableName = "meme_template")
public class MemeTemplate {



    @DatabaseField(id = true, columnName = "image_name")
    public String imageName;

    @DatabaseField(columnName = "title")
    public String title;

    public MemeTemplate(String imageName, String title) {
        this.imageName = imageName;
        this.title = title;
    }
    public MemeTemplate() {

    }






}
