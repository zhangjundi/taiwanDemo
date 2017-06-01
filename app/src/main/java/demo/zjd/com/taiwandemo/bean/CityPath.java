package demo.zjd.com.taiwandemo.bean;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

import demo.zjd.com.taiwandemo.utils.PathParser;

/**
 * Created by zhangjd on 2017/6/1.
 */

public class CityPath {
    private String id;
    private String title;
    private String pathData;
    private Path mPath;
    private Region re=new Region();
    public Path getmPath() {
        return mPath;
    }

    /**
     * 初始化path值
     */
    public void initPath(){
        mPath = PathParser.createPathFromPathData(pathData);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }
    public boolean isArea(float x,float y){
        RectF r=new RectF();
        //计算控制点的边界
        mPath.computeBounds(r, true);
        //设置区域路径和剪辑描述的区域
        re.setPath(mPath, new Region((int)r.left,(int)r.top,(int)r.right,(int)r.bottom));
        return  re.contains((int)x, (int)y);
    }
}
