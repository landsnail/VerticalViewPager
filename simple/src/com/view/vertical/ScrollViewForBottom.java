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
 *
 */
public class ScrollViewForBottom extends ScrollView {

    public interface OnScroll {
        public void onScrollChanged(ScrollViewForBottom scrollView, int x, int y, int oldx, int oldy);
    }

    private OnScroll onScroll;

    public ScrollViewForBottom(Context context) {
        super(context);
    }

    public ScrollViewForBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewForBottom(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    public void setOnScroll(OnScroll onScroll) {
        this.onScroll = onScroll;
    }

    float mLastMotionY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE: {

                float direction = mLastMotionY - event.getY();
                mLastMotionY = event.getY();

                Log.i("msg", "direction:" + direction);

                if (getScrollY() <= 0 && direction < -5) {
                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    ((View) getParent().getParent().getParent()).onTouchEvent(event);
                } else {
                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScroll != null) {
            onScroll.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
