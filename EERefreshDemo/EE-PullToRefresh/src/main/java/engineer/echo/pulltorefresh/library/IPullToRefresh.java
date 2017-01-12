/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package engineer.echo.pulltorefresh.library;

import android.view.View;
import android.view.animation.Interpolator;

import engineer.echo.pulltorefresh.library.PullToRefreshBase.Mode;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.State;

public interface IPullToRefresh<T extends View> {

	 boolean demo();

	 Mode getCurrentMode();

	 boolean getFilterTouchEvents();

	 ILoadingLayout getLoadingLayoutProxy();

	 ILoadingLayout getLoadingLayoutProxy(boolean includeStart, boolean includeEnd);

	 Mode getMode();

	 T getRefreshableView();
	 boolean getShowViewWhileRefreshing();

	 State getState();

	 boolean isPullToRefreshEnabled();

	 boolean isPullToRefreshOverScrollEnabled();

	 boolean isRefreshing();

	 boolean isScrollingWhileRefreshingEnabled();

	 void onRefreshComplete();

	 void setFilterTouchEvents(boolean filterEvents);

	 void setMode(Mode mode);

	 void setOnPullEventListener(OnPullEventListener<T> listener);

	 void setOnRefreshListener(OnRefreshListener<T> listener);

	 void setOnRefreshListener(OnRefreshListener2<T> listener);

	 void setPullToRefreshOverScrollEnabled(boolean enabled);

	 void setRefreshing();

	 void setRefreshing(boolean doScroll);

	 void setScrollAnimationInterpolator(Interpolator interpolator);

	 void setScrollingWhileRefreshingEnabled(boolean scrollingWhileRefreshingEnabled);

	 void setShowViewWhileRefreshing(boolean showView);

}