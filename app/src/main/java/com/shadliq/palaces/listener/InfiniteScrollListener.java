package com.shadliq.palaces.listener;

import androidx.recyclerview.widget.RecyclerView;

public class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private boolean loading; // LOAD MORE Progress dialog
    private OnLoadMoreListener listener;
    private int currentPage = 0;

    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
    private static final int PAGE_SIZE = 3;

    public InfiniteScrollListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!loading && !recyclerView.canScrollVertically(1)) {
            currentPage++;
            listener.onLoadMore(currentPage);
            loading = true;
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int offset);
    }

}