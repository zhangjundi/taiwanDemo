package demo.zjd.com.taiwandemo.bean;

import android.graphics.RectF;

/**
 * Created by zhangjd on 2017/6/1.
 */

public class ViewAttr {
    private Float left;
    private Float top;
    private Float right;
    private Float bottom;

    public int getWidth() {
        if(left==null||right==null)
            return 0;
        return (int) Math.abs(left - right);
    }

    public int getHeight() {
        if(top==null||bottom==null)
            return 0;
        return (int) Math.abs(top - bottom);
    }

    public void colSize(RectF mRectF) {
        if (left == null)
            left = mRectF.left;
        else
            left = Math.min(mRectF.left, left);
        if (top == null)
            top = mRectF.top;
        else
            top = Math.min(mRectF.top, top);
        if (right == null)
            right = mRectF.right;
        else
            right = Math.max(mRectF.right, right);
        if (bottom == null)
            bottom = mRectF.bottom;
        else
            bottom = Math.max(mRectF.bottom, bottom);
    }
}
