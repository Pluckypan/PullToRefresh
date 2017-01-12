package engineer.echo.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Plucky on 16/5/31-上午11:18.
 * 功能描述:Android-PullToRefresh RecycleView扩展
 */
public class PullToRefreshRecycleView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecycleView(Context context) {
        super(context);
    }

    public PullToRefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecycleView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecycleView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }


    int ddy = 0;

    public void setBackUpView(final View backUpView) {
        final RecyclerView recyclerView = getRefreshableView();
        if (recyclerView == null || backUpView == null) return;

        backUpView.setVisibility(INVISIBLE);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ddy += dy;

                if (ddy > 1200) {
                    backUpView.setVisibility(VISIBLE);
                } else {
                    backUpView.setVisibility(INVISIBLE);
                }
            }
        });
        backUpView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ddy = 0;
                backUpView.setVisibility(INVISIBLE);
                recyclerView.scrollToPosition(0);
            }
        });
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    private boolean isFirstItemVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getRefreshableView().getLayoutManager();
        if (null == layoutManager || layoutManager.getItemCount() == 0) {
            return true;
        } else {
            if (layoutManager.findFirstCompletelyVisibleItemPosition() <= 1) {
                final View firstVisibleChild = getRefreshableView().getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= getRefreshableView().getTop();
                }
            }
        }

        return false;
    }

    private boolean isLastItemVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getRefreshableView().getLayoutManager();

        if (null == layoutManager || layoutManager.getItemCount() == 0) {
            return true;
        } else {
            final int lastItemPosition = layoutManager.getItemCount() - 1;
            final int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
            if (lastVisiblePosition >= lastItemPosition) {
                final int childIndex = lastVisiblePosition - layoutManager.findFirstCompletelyVisibleItemPosition();
                final View lastVisibleChild = getRefreshableView().getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= getRefreshableView().getBottom();
                }
            }
        }

        return false;
    }

    /**
     * scrollBy when loadmore complete
     */
    public void onAppendData() {
        getRefreshableView().scrollBy(0, 100);
    }
}
