package demo.zjd.com.taiwandemo.calback;

import java.util.List;

import demo.zjd.com.taiwandemo.bean.CityPath;
import demo.zjd.com.taiwandemo.bean.ViewAttr;

/**
 * Created by zhangjd on 2017/6/1.
 */

public interface ParserCallBack {
    public void callback(List<CityPath> list,ViewAttr mViewAttr);
}
