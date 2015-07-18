package madelyntav.c4q.nyc.memeproject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by elvisboves on 7/17/15.
 */

@DatabaseTable
public class PreExistingMeme {


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int preMeme;

    public PreExistingMeme(){

    }

    public PreExistingMeme (int preMeme) {

        this.preMeme = preMeme;
    }


}
