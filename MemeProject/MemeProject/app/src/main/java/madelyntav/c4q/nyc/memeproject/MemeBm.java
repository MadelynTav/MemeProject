package madelyntav.c4q.nyc.memeproject;

/**
 * Created by alvin2 on 7/15/15.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MemeBm {

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] imageBlob;


    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
    }


}
