package madelyntav.c4q.nyc.memeproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kadeemmaragh on 6/5/15.
 */
public class MemeList extends Activity{

    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_list);

        int[] images = {R.drawable.actual_advice_mallard, R.drawable.but_thats_none_of_my_business, R.drawable.creepy_condescending_wonka, R.drawable.futurama_fry, R.drawable.good_guy_greg, R.drawable.liam_neeson_taken, R.drawable.one_does_not_simply, R.drawable.scumbag_steve, R.drawable.shut_up_and_take_my_money_fry, R.drawable.ten_guy, R.drawable.the_most_interesting_man_in_the_world, R.drawable.third_world_skeptical_kid, R.drawable.unhelpful_high_school_teacher, R.drawable.yao_ming, R.drawable.you_the_real_mvp};


        ArrayList<Integer> memeImages = new ArrayList<Integer>();
        memeImages.add(R.drawable.actual_advice_mallard);
        memeImages.add(R.drawable.but_thats_none_of_my_business);
//
//        ArrayList<Integer> memeImages = new ArrayList<Integer>();
//        for(int image : images){
//            memeImages.add(image,image);
//        }

        ArrayList<String> memeNames = getImageNames(memeImages);


        CustomArrayAdapter memeAdapter = new CustomArrayAdapter(this,memeNames,memeImages);

        //listView.setAdapter(memeAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(memeList.this, EditPhoto.class);
//                intent.putExtra("image",)
//                Bitmap imageBitmap = view.getDrawingCache();
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
//                intent.putExtra("byteArray", bs.toByteArray());
//                startActivity(intent);
//
//            }
//        });

    }

    public ArrayList<String> getImageNames(ArrayList<Integer> images){
        ArrayList<String> nameList = new ArrayList<String>();

        for(int i = 0; i< images.size(); i++){
            nameList.add(i, this.getResources().getResourceEntryName(images.get(i)));
            if(nameList.get(i) == null){
                nameList.remove(i);
                nameList.add(i,"Hello");
            }
        }


        return nameList;
    }

}
