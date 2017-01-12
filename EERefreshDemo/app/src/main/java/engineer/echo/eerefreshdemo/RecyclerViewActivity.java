package engineer.echo.eerefreshdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import engineer.echo.eerefreshdemo.adapter.SectionRecyclerViewAdapter;
import engineer.echo.pulltorefresh.library.PullToRefreshBase;
import engineer.echo.pulltorefresh.library.PullToRefreshRecycleView;

public class RecyclerViewActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener2 {

    @ViewInject(R.id.pullToRefreshRecycleView)
    PullToRefreshRecycleView pullToRefreshRecycleView;

    SectionRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        pullToRefreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshRecycleView.setOnRefreshListener(this);
        pullToRefreshRecycleView.setScrollingWhileRefreshingEnabled(true);

        adapter = new SectionRecyclerViewAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getSpanSize(position);
            }
        });
        pullToRefreshRecycleView.getRefreshableView().setLayoutManager(manager);
        pullToRefreshRecycleView.getRefreshableView().setAdapter(adapter);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getData(TYPE_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getData(TYPE_LOADMORE);
    }

    private void getData(final int state) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2109);
                    handler.sendEmptyMessage(state);
                } catch (Exception e) {
                    //耗时4321
                }
            }
        }).start();
    }

    public static final int TYPE_REFRESH = 0;
    public static final int TYPE_LOADMORE = 1;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pullToRefreshRecycleView.onRefreshComplete();
            switch (msg.what) {
                case TYPE_REFRESH:
                    break;
                case TYPE_LOADMORE:
                    pullToRefreshRecycleView.showNoMore();
                    break;
            }

        }
    };
}
