package demo.zjd.com.taiwandemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import demo.zjd.com.taiwandemo.R;
import demo.zjd.com.taiwandemo.bean.CityPath;
import demo.zjd.com.taiwandemo.calback.ParserCallBack;
import demo.zjd.com.taiwandemo.utils.SVGXmlParserUtils1;

/**
 * Created by zhangjd on 2017/6/1.
 */

public class MapView extends View implements ParserCallBack {
    private List<CityPath> list;
    private Paint mPaint;
    private Path mPath;

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

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //解析svg xml
        SVGXmlParserUtils1.parserXml(getResources().openRawResource(R.raw.china), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix mMatrix=new Matrix();
        mMatrix.postScale(0.5f,0.5f);
        canvas.concat(mMatrix);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            CityPath path = list.get(i);
            //绘制边的颜色
            mPaint.setStrokeWidth(2);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.GRAY);
            canvas.drawPath(path.getmPath(), mPaint);
        }
        if(mPath!=null){
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GREEN);
            canvas.drawPath(mPath, mPaint);
        }
    }

    @Override
    public void callback(List<CityPath> list) {
        this.list = list;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    CityPath cityPath = list.get(i);
                    if (cityPath.isArea(x/0.5f, y/0.5f)) {
                        mPath = cityPath.getmPath();
                        postInvalidate();
                        Toast.makeText(getContext(),cityPath.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
        }
        return super.onTouchEvent(event);
    }
}
