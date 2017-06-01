package demo.zjd.com.taiwandemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import demo.zjd.com.taiwandemo.R;
import demo.zjd.com.taiwandemo.bean.CityPath;
import demo.zjd.com.taiwandemo.bean.ViewAttr;
import demo.zjd.com.taiwandemo.calback.ParserCallBack;
import demo.zjd.com.taiwandemo.utils.SVGXmlParserUtils;

/**
 * Created by zhangjd on 2017/6/1.
 */

public class MapView extends View implements ParserCallBack {
    private List<CityPath> list;
    private ViewAttr mViewAttr;
    private Paint mPaint;
    private Path mPath;
    private float scale = 0.5f;
    private int viewWidth;
    private int viewHeight;
    //是否计算完成
    private boolean isCalculation;

    public MapView(Context context) {
        super(context);
        init();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(getMeasureSize(300, widthMeasureSpec),
                getMeasureSize(300, heightMeasureSpec));
    }


    private int getMeasureSize(int defultSize, int measureSpce) {
        int mode = MeasureSpec.getMode(measureSpce);
        int size = MeasureSpec.getSize(measureSpce);
        int measureSize = defultSize;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                measureSize = Math.max(defultSize, size);
                break;
            case MeasureSpec.AT_MOST:
                measureSize = defultSize;
                break;
        }
        return measureSize;
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //解析svg xml
        SVGXmlParserUtils.parserXml(getResources().openRawResource(R.raw.taiwan), this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        myDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (list == null) {
            return;
        }
        //        Matrix mMatrix = new Matrix();
//        mMatrix.postScale(0.5f,0.5f);
        //这个set方法不可以
//        mMatrix.setScale(0.5f,0.5f);
//        canvas.concat(mMatrix);
        //上面的方法也可以
//        canvas.restore();
        canvas.scale(scale, scale);
        canvas.drawColor(Color.YELLOW);
        for (int i = 0; i < list.size(); i++) {
            CityPath path = list.get(i);
            //绘制边的颜色
            mPaint.setStrokeWidth(2);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.GRAY);
            canvas.drawPath(path.getmPath(), mPaint);
        }
        if (mPath != null) {
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GREEN);
            canvas.drawPath(mPath, mPaint);
        }

    }

    @Override
    public void callback(List<CityPath> list, ViewAttr mViewAttr) {
        this.list = list;
        this.mViewAttr = mViewAttr;
        myDraw();
    }

    private void myDraw(){
        if(!isCalculation&&mViewAttr!=null) {
            if (mViewAttr.getWidth() > 0 && mViewAttr.getHeight() > 0 && viewWidth > 0 && viewHeight > 0) {
                isCalculation = true;
                float widthScale = viewWidth * 1.00f / mViewAttr.getWidth();
                float heightScale = viewHeight * 1.00f / mViewAttr.getHeight();
                scale = Math.min(widthScale, heightScale);
            }
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    CityPath cityPath = list.get(i);
                    if (cityPath.isArea(x / scale, y / scale)) {
                        mPath = cityPath.getmPath();
                        postInvalidate();
                        Toast.makeText(getContext(), cityPath.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
        }
        return super.onTouchEvent(event);
    }
}
