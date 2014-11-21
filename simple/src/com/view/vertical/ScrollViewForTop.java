package com.view.vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


/**
 * Created by landsnail on 14-9-30.
 *
 * @author hongyun.fang
 * @email  fanghongyun@gmail.com
 */
public class ScrollViewForTop extends ScrollView {

    public interface OnScroll {
        public void onScrollChanged(ScrollViewForTop scrollView, int x, int y, int oldx, int oldy);
    }

    private OnScroll onScroll;

    public ScrollViewForTop(Context context) {
        super(context);
    }

    public ScrollViewForTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewForTop(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    public void setOnScroll(OnScroll onScroll) {
        this.onScroll = onScroll;
    }

    float mLastMotionX = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

//        getParent().requestDisallowInterceptTouchEvent(false);

        final float x = ((MotionEvent) event).getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE: {

                if ((getChildAt(0).getMeasuredHeight() <= (getScrollY() + getHeight()))) {
                    Log.i("msg", "offset---------" + getParent().toString());
                    getParent().requestDisallowInterceptTouchEvent(false);
                    //获得 VerticalViewPager 的实例
                    ((View) getParent()).onTouchEvent(event);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        try {
            return super.dispatchTouchEvent(event);
        }catch (Exception ex){
            Log.d("dispatchTouchEvent Exception:", ex.getLocalizedMessage());
            return false;
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScroll != null) {
            onScroll.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
