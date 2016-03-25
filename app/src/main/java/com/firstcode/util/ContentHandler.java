package com.firstcode.util;

import android.text.TextUtils;

import com.firstcode.vo.Game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by wangjinliang on 2016/3/21.
 */
public class ContentHandler extends DefaultHandler {
    private static String TAG = "ContentHandler";
    private String nodeName = "";
    private ArrayList<Game> list = null;
    private Game game = null;

    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<Game>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName = localName;
        if("game".equals(nodeName)) {
            Utils.log(TAG, "创建Game对象");
            game = new Game();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();
        if(TextUtils.isEmpty(content))
            return;
        if("name".equals(nodeName)) {
            Utils.log(TAG, "Game name = " + content);
            game.setName(content);
        } else if("pic".equals(nodeName)) {
            Utils.log(TAG, "Game pic = " + content);
            game.setPic(content);
        } else if("packageName".equals(nodeName)) {
            Utils.log(TAG, "Game packageName = " + content);
            game.setPackageName(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        nodeName = localName;
        if("game".equals(localName)) {
            Utils.log(TAG, "add Game to list");
            list.add(game);
            game = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public ArrayList<Game> getArrayList(){
        return list;
    }
}
