# EE-PullToRefresh
基于Android-PullToRefresh(com.handmark.pulltorefresh),支持gradle引用,扩展了刷新视图控制
### gradle引用
compile 'engineer.echo.pulltorefresh.library:EE-PullToRefresh:1.0.0'
### 使用案例
> 具体请参考Demo

#### 布局文件中
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="engineer.echo.eerefreshdemo.RecyclerViewActivity">

    <engineer.echo.pulltorefresh.library.PullToRefreshRecycleView
        android:id="@+id/pullToRefreshRecycleView"
        style="@style/FHRefreshStyle"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</RelativeLayout>
```
#### JAVA
```
private void initView() {
    pullToRefreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
    pullToRefreshRecycleView.setOnRefreshListener(this);
    pullToRefreshRecycleView.setScrollingWhileRefreshingEnabled(true);
}
@Override
public void onPullDownToRefresh(PullToRefreshBase refreshView) {
   //执行刷新
}

@Override
public void onPullUpToRefresh(PullToRefreshBase refreshView) {
    //执行加载更多
}    
```