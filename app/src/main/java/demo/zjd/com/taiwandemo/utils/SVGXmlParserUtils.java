package demo.zjd.com.taiwandemo.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import demo.zjd.com.taiwandemo.bean.CityPath;
import demo.zjd.com.taiwandemo.calback.ParserCallBack;

/**
 * Created by zhangjd on 2017/6/1.
 * 解析svg xml
 */

public class SVGXmlParserUtils {

    public static void parserXml(final InputStream in, final ParserCallBack mParserCallBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CityPath> list=new ArrayList<>();
                parserXml(in,list);
                if(mParserCallBack!=null){
                    mParserCallBack.callback(list);
                }
            }
        }).start();
    }

    private static void parserXml(InputStream in, List<CityPath> list){
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "UTF-8");
            int eventType = parser.getEventType();
            String name = null;
            CityPath mCityPath = null;
            list.clear();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        name = parser.getName();
                        if ("path".equals(name)) {
                            mCityPath = new CityPath();
                            mCityPath.setId(parser.getAttributeValue(null, "id"));
                            mCityPath.setTitle(parser.getAttributeValue(null, "title"));
                            mCityPath.setPathData(parser.getAttributeValue(null, "d"));
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        name = parser.getName();
                        if ("path".equals(name)) {
                            mCityPath.initPath();
                            list.add(mCityPath);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
