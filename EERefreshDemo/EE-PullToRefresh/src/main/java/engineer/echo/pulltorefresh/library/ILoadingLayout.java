package engineer.echo.pulltorefresh.library;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

 public interface ILoadingLayout {

	 void setLastUpdatedLabel(CharSequence label);


	 void setLoadingDrawable(Drawable drawable);


	 void setPullLabel(CharSequence pullLabel);



	 void resetPullLabel();


	 void setRefreshingLabel(CharSequence refreshingLabel);


	 void setReleaseLabel(CharSequence releaseLabel);


	 void setTextTypeface(Typeface tf);

}
