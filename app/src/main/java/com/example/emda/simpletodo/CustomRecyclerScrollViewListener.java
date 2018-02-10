package com.example.emda.simpletodo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by emda on 2/9/2018.
 */

public abstract class CustomRecyclerScrollViewListener extends RecyclerView.OnScrollListener {

    static final float MINIMUM = 20;
    int scrollDist = 0;
    boolean isVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(isVisible && scrollDist>MINIMUM){
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if(!isVisible && scrollDist < -MINIMUM){
            show();
            scrollDist = 0;
            isVisible =true;
        }
        if((isVisible && dy>0) || (!isVisible && dy<0)){
            scrollDist += dy;
        }
    }
    public abstract void show();
    public abstract void hide();
}
