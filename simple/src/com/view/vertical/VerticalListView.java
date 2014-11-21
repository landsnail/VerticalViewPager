package com.view.vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;


/**
 * Created by landsnail on 14-10-12.
 */
public class VerticalListView extends ListView {

    public VerticalListView(Context context) {
        super(context);
    }

    public VerticalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    /**
     * @param direction -1 页面从上往下走。
     * @return
     */
    public boolean canScrollVertical(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        else return (direction < 0) ? (offset > 0) : (offset < range - 1);
    }

    float mLastMotionY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE: {

                float direction = mLastMotionY - event.getY();
                mLastMotionY = event.getY();

                Log.i("msg", "scroll:" + getScrollY() + "   direction:" + direction);

                if (!canScrollVertical(-1) && direction < -5) {
                    getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    ((View) getParent().getParent().getParent().getParent()).onTouchEvent(event);
                } else {
                    getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(event);
    }

}
