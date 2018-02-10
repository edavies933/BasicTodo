package com.example.emda.simpletodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by emda on 2/9/2018.
 */

public class RecyclerViewEmptySupport extends RecyclerView {

    private View mEmptyView;
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };



    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public void showEmptyView(){

        Adapter<?> adapter = getAdapter();
        if(adapter!=null && mEmptyView !=null){
            if(adapter.getItemCount()==0){
                mEmptyView.setVisibility(VISIBLE);
                RecyclerViewEmptySupport.this.setVisibility(GONE);
            }
            else{
                mEmptyView.setVisibility(GONE);
                RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }

    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter!=null){
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    public void setmEmptyView(View v){
        mEmptyView = v;
    }
}
