package engineer.echo.eerefreshdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import engineer.echo.pulltorefresh.library.PullToRefreshBase;
import engineer.echo.pulltorefresh.library.PullToRefreshRecycleView;

public class RecyclerViewActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener {

    @ViewInject(R.id.pullToRefreshRecycleView)
    PullToRefreshRecycleView pullToRefreshRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        x.view().inject(this);
    }

    private void initView() {
        pullToRefreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshRecycleView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

    }
}
