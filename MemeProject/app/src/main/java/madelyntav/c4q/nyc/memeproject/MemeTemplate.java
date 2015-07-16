package madelyntav.c4q.nyc.memeproject;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MemeTemplate {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private Integer resource_id;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] data;

    public MemeTemplate() {

    }

    public MemeTemplate(String title, Integer resource_id) {
        this.title = title;
        this.resource_id = resource_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + resource_id;
    }
}
