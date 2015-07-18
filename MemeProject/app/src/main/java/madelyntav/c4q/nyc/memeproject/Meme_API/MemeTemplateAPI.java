package madelyntav.c4q.nyc.memeproject.Meme_API;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by c4q-anthonyf on 7/17/15.
 */
public interface MemeTemplateAPI {

    @GET("/get_memes")
    public void getTemplates(Callback<MemeTemplate> response);

}
