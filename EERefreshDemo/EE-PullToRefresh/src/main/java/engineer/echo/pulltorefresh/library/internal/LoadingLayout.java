/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package engineer.echo.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import engineer.echo.pulltorefresh.library.ILoadingLayout;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.Mode;
import engineer.echo.pulltorefresh.library.PullToRefreshBase.Orientation;
import engineer.echo.pulltorefresh.library.R;

public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {

    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

    private FrameLayout mInnerLayout;

    //Added By Plucky
    protected final ImageView ivRefreshTop;
    protected final LinearLayout LRefreshTips;

    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;

    private boolean mUseIntrinsicAnimation;

    private final TextView mHeaderText;
    private final TextView mSubHeaderText;

    protected final Mode mMode;
    protected final Orientation mScrollDirection;

    private CharSequence mPullLabel, defaultPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    public LoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
        super(context);
        mMode = mode;
        mScrollDirection = scrollDirection;

        switch (scrollDirection) {
            case HORIZONTAL:
                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_horizontal, this);
                break;
            case VERTICAL:
            default:
                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);
                break;
        }

        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        mHeaderImage = (ImageView) findViewById(R.id.pull_to_refresh_image);
        mHeaderProgress = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);

        mHeaderText = (TextView) findViewById(R.id.pull_to_refresh_text);
        mSubHeaderText = (TextView) findViewById(R.id.pull_to_refresh_sub_text);

        /**
         * Added By Plucky  2016-05-31 15:20
         * add a picture above refreshView
         */
        ivRefreshTop = (ImageView) findViewById(R.id.ivRefreshTop);
        LRefreshTips = (LinearLayout) findViewById(R.id.LRefreshTips);

        /**
         * set picture
         */
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrRefreshTop) && mode == Mode.PULL_FROM_START) {
            Drawable refreshTop = attrs.getDrawable(R.styleable.PullToRefresh_ptrRefreshTop);
            if (null != refreshTop && ivRefreshTop != null) {
                ivRefreshTop.setImageDrawable(refreshTop);
            }
        }
        /**
         * set if show refreshTips
         */
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrShowRefreshTips) && mode == Mode.PULL_FROM_START) {
            boolean isShow = attrs.getBoolean(R.styleable.PullToRefresh_ptrShowRefreshTips, true);
            if (LRefreshTips != null) {
                LRefreshTips.setVisibility(isShow ? VISIBLE : GONE);
            }
        }
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrShowLoadMoreTips) && mode == Mode.PULL_FROM_END) {
            boolean isShow = attrs.getBoolean(R.styleable.PullToRefresh_ptrShowLoadMoreTips, true);
            if (LRefreshTips != null) {
                LRefreshTips.setVisibility(isShow ? VISIBLE : GONE);
            }
        }
        /**
         * 设置Progress样式
         */
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrProgressIndeterminateDrawable)) {
            Drawable ptrProgressIndeterminateDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrProgressIndeterminateDrawable);
            if (mHeaderProgress != null && ptrProgressIndeterminateDrawable != null) {
                mHeaderProgress.setIndeterminateDrawable(ptrProgressIndeterminateDrawable);
            }
        }


        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInnerLayout.getLayoutParams();

        switch (mode) {
            case PULL_FROM_END:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.TOP : Gravity.LEFT;

                // Load in labels

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrFooterPullLable)) {
                    mPullLabel = attrs.getString(R.styleable.PullToRefresh_ptrFooterPullLable);
                } else {
                    mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrFooterLoadingLable)) {
                    mRefreshingLabel = attrs.getString(R.styleable.PullToRefresh_ptrFooterLoadingLable);
                } else {
                    mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrFooterReleaseLable)) {
                    mReleaseLabel = attrs.getString(R.styleable.PullToRefresh_ptrFooterReleaseLable);
                } else {
                    mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrFooterDefaultLable)) {
                    defaultPullLabel = attrs.getString(R.styleable.PullToRefresh_ptrFooterDefaultLable);
                } else {
                    defaultPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                }

                break;

            case PULL_FROM_START:
            default:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.BOTTOM : Gravity.RIGHT;

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderPullLable)) {
                    mPullLabel = attrs.getString(R.styleable.PullToRefresh_ptrHeaderPullLable);
                } else {
                    mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderLoadingLable)) {
                    mRefreshingLabel = attrs.getString(R.styleable.PullToRefresh_ptrHeaderLoadingLable);
                } else {
                    mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderReleaseLable)) {
                    mReleaseLabel = attrs.getString(R.styleable.PullToRefresh_ptrHeaderReleaseLable);
                } else {
                    mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
                }

                if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderDefaultLable)) {
                    defaultPullLabel = attrs.getString(R.styleable.PullToRefresh_ptrHeaderDefaultLable);
                } else {
                    defaultPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                }

                break;
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderBackground)) {
            Drawable background = attrs.getDrawable(R.styleable.PullToRefresh_ptrHeaderBackground);
            if (null != background) {
                ViewCompat.setBackground(this, background);
            }
        }

        int textAppea = mode == Mode.PULL_FROM_START ? R.styleable.PullToRefresh_ptrHeaderTextAppearance : R.styleable.PullToRefresh_ptrFooterTextAppearance;
        int textAppeaSub = mode == Mode.PULL_FROM_START ? R.styleable.PullToRefresh_ptrSubHeaderTextAppearance : R.styleable.PullToRefresh_ptrSubFooterTextAppearance;

        if (attrs.hasValue(textAppea)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(textAppea, styleID);
            setTextAppearance(styleID.data);
        }
        if (attrs.hasValue(textAppeaSub)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(textAppeaSub, styleID);
            setSubTextAppearance(styleID.data);
        }

        int txtColor = mode == Mode.PULL_FROM_START ? R.styleable.PullToRefresh_ptrHeaderTextColor : R.styleable.PullToRefresh_ptrFooterTextColor;
        int txtColorSub = mode == Mode.PULL_FROM_START ? R.styleable.PullToRefresh_ptrHeaderSubTextColor : R.styleable.PullToRefresh_ptrFooterSubTextColor;
        // Text Color attrs need to be set after TextAppearance attrs
        if (attrs.hasValue(txtColor)) {
            ColorStateList colors = attrs.getColorStateList(txtColor);
            if (null != colors) {
                setTextColor(colors);
            }
        }
        if (attrs.hasValue(txtColorSub)) {
            ColorStateList colors = attrs.getColorStateList(txtColorSub);
            if (null != colors) {
                setSubTextColor(colors);
            }
        }

        // Try and get defined drawable from Attrs
        Drawable imageDrawable = null;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawable)) {
            imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawable);
        }

        // Check Specific Drawable from Attrs, these overrite the generic
        // drawable attr above
        switch (mode) {
            case PULL_FROM_START:
            default:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableStart)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableStart);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableTop)) {
                    Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableTop);
                }
                break;

            case PULL_FROM_END:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableEnd)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableEnd);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableBottom)) {
                    Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableBottom);
                }
                break;
        }

        // If we don't have a user defined drawable, load the default
        if (null == imageDrawable) {
            imageDrawable = context.getResources().getDrawable(getDefaultDrawableResId());
        }

        // Set Drawable, and save width/height
        setLoadingDrawable(imageDrawable);

        reset();
    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = width;
        requestLayout();
    }

    public final int getContentSize() {
        /**
         * Added By Plucky
         * 头部图片不应该算在整个高度中
         * 否则会导致需要下拉到整个内容的顶部才触发刷新
         */
        int topHeight = ivRefreshTop != null ? ivRefreshTop.getHeight() : 0;

        switch (mScrollDirection) {
            case HORIZONTAL:
                return mInnerLayout.getWidth();
            case VERTICAL:
            default:
                return mInnerLayout.getHeight() - topHeight;
        }
    }

    public final void hideAllViews() {
        if (View.VISIBLE == mHeaderText.getVisibility()) {
            mHeaderText.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mHeaderProgress.getVisibility()) {
            mHeaderProgress.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mHeaderImage.getVisibility()) {
            mHeaderImage.setVisibility(View.INVISIBLE);
        }
        if (View.VISIBLE == mSubHeaderText.getVisibility()) {
            mSubHeaderText.setVisibility(View.INVISIBLE);
        }
    }

    public final void onPull(float scaleOfLayout) {
        if (!mUseIntrinsicAnimation) {
            onPullImpl(scaleOfLayout);
        }
    }

    public final void pullToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mPullLabel);
        }

        // Now call the callback
        pullToRefreshImpl();
    }

    public final void refreshing() {
        if (null != mHeaderText) {
            mHeaderText.setText(mRefreshingLabel);
        }

        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).start();
        } else {
            // Now call the callback
            refreshingImpl();
        }

        if (null != mSubHeaderText) {
            mSubHeaderText.setVisibility(View.GONE);
        }
    }

    public final void releaseToRefresh() {
        if (null != mHeaderText) {
            mHeaderText.setText(mReleaseLabel);
        }

        // Now call the callback
        releaseToRefreshImpl();
    }

    public final void reset() {
        if (null != mHeaderText) {
            mHeaderText.setText(mPullLabel);
        }
        mHeaderImage.setVisibility(View.VISIBLE);

        if (mUseIntrinsicAnimation) {
            ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        } else {
            // Now call the callback
            resetImpl();
        }

        if (null != mSubHeaderText) {
            if (TextUtils.isEmpty(mSubHeaderText.getText())) {
                mSubHeaderText.setVisibility(View.GONE);
            } else {
                mSubHeaderText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        setSubHeaderText(label);
    }

    public final void setLoadingDrawable(Drawable imageDrawable) {
        // Set Drawable
        mHeaderImage.setImageDrawable(imageDrawable);
        mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);

        // Now call the callback
        onLoadingDrawableSet(imageDrawable);
    }

    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    public void resetPullLabel() {
        mPullLabel = defaultPullLabel;
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

    @Override
    public void setTextTypeface(Typeface tf) {
        mHeaderText.setTypeface(tf);
    }

    public final void showInvisibleViews() {
        if (View.INVISIBLE == mHeaderText.getVisibility()) {
            mHeaderText.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mHeaderProgress.getVisibility()) {
            mHeaderProgress.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mHeaderImage.getVisibility()) {
            mHeaderImage.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == mSubHeaderText.getVisibility()) {
            mSubHeaderText.setVisibility(View.VISIBLE);
        }
    }

    protected abstract int getDefaultDrawableResId();

    protected abstract void onLoadingDrawableSet(Drawable imageDrawable);

    protected abstract void onPullImpl(float scaleOfLayout);

    protected abstract void pullToRefreshImpl();

    protected abstract void refreshingImpl();

    protected abstract void releaseToRefreshImpl();

    protected abstract void resetImpl();

    private void setSubHeaderText(CharSequence label) {
        if (null != mSubHeaderText) {
            if (TextUtils.isEmpty(label)) {
                mSubHeaderText.setVisibility(View.GONE);
            } else {
                mSubHeaderText.setText(label);

                // Only set it to Visible if we're GONE, otherwise VISIBLE will
                // be set soon
                if (View.GONE == mSubHeaderText.getVisibility()) {
                    mSubHeaderText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setSubTextAppearance(int value) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setSubTextColor(ColorStateList color) {
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextColor(color);
        }
    }

    private void setTextAppearance(int value) {
        if (null != mHeaderText) {
            mHeaderText.setTextAppearance(getContext(), value);
        }
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setTextColor(ColorStateList color) {
        if (null != mHeaderText) {
            mHeaderText.setTextColor(color);
        }
        if (null != mSubHeaderText) {
            mSubHeaderText.setTextColor(color);
        }
    }

}
