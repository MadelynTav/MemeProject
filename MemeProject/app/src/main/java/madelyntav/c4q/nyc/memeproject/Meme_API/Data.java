package madelyntav.c4q.nyc.memeproject.Meme_API;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private List<Meme> memes = new ArrayList<Meme>();

    /**
     *
     * @return
     * The memes
     */
    public List<Meme> getMemes() {
        return memes;
    }

    /**
     *
     * @param memes
     * The memes
     */
    public void setMemes(List<Meme> memes) {
        this.memes = memes;
    }

}