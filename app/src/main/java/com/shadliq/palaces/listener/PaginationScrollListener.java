package com.shadliq.palaces.listener;

import android.util.Log;

import com.shadliq.palaces.listener.InfiniteScrollListener;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PaginationScrollListener implements NestedScrollView.OnScrollChangeListener {

    private LinearLayoutManager linearLayoutManager;
    private boolean loading; // LOAD MORE Progress dialog
    private InfiniteScrollListener.OnLoadMoreListener listener;
    private int offset = 0;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager, InfiniteScrollListener.OnLoadMoreListener listener) {
        this.linearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        // To check if at the top of recycler view
        if(linearLayoutManager.findFirstCompletelyVisibleItemPosition()==0){
            // Its at top
            Log.i("in the top", "");
        }

        if (v.getChildAt(v.getChildCount() - 1) != null) {
            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                    scrollY > oldScrollY) {

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    System.out.println("onScrollChange, loading" +  loading);
                    offset++;
                    loading = true;
                    listener.onLoadMore(offset);
                }
            }
        }
    }

    public void resetOffset(){
        offset = 0;
    }

    public void setLoaded() {
        loading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int offset);
    }

}