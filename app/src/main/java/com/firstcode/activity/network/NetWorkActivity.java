package com.firstcode.activity.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.ContentHandler;
import com.firstcode.util.HttpUtil;
import com.firstcode.util.Utils;
import com.firstcode.vo.Game;
import com.firstcode.vo.GameObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class NetWorkActivity extends BaseActivity {
    private static String TAG = "NetWorkActivity";
    private static Activity mInstance = null;

    private Button webviewBtn = null;
    private Button httpUrlConnectionBtn = null;
    private TextView httpUrlConnectionContentTv = null;

    private Button httpUrlConnectionPostBtn = null;
    private TextView httpUrlConnectionContentPostTv = null;

    private Button httpClientGetBtn = null;
    private TextView httpClientGetTv = null;

    private Button httpClientPostBtn = null;
    private TextView httpClientPostTv = null;

    private Button pullXMLBtn = null;
    private TextView pullXMLTv = null;

    private Button saxXMLBtn = null;
    private TextView saxXMLTv = null;

    private Button jsonObjBtn = null;
    private TextView jsonObjTv = null;

    private Button gsonBtn = null;
    private TextView gsonTv = null;

    private Button netWorkBtn = null;

    private static final int HTTPURL_CONNECTION_GET_BAIDU = 1;
    private static final int HTTPURL_CONNECTION_GET_NULL = 2;
    private static final int HTTPURL_CONNECTION_POST_BAIDU = 3;
    private static final int HTTPURL_CONNTECTION_POST_NULL = 4;
    private static final int HTTP_CLIENT_GET_STATE = 5;
    private static final int HTTP_CLIENT_POST_STATE = 6;
    private static final int PARSER_XML_PULL = 7;
    private static final int SAX_PARSER_XML = 8;
    private static final int JSON_OBJECT_PARSER_JSON = 9;
    private static final int GSON_PARSER_JSON = 10;
    private static String ENJOYGAME_JSON_ADDRESS = "https://s3-ap-southeast-1.amazonaws.com/enjoygame/enjoygames.json";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HTTPURL_CONNECTION_GET_BAIDU:
                    httpUrlConnectionContentTv.setText((String)msg.obj);
                    break;
                case HTTPURL_CONNECTION_GET_NULL:
                     httpUrlConnectionContentTv.setText("GET没有读到数据");
                    break;
                case HTTPURL_CONNECTION_POST_BAIDU:
                    httpUrlConnectionContentPostTv.setText((String)msg.obj);
                    break;
                case HTTPURL_CONNTECTION_POST_NULL:
                    httpUrlConnectionContentTv.setText("POST没有读到数据");
                    break;
                case HTTP_CLIENT_GET_STATE:
                    httpClientGetTv.setText((String)msg.obj);
                    break;
                case HTTP_CLIENT_POST_STATE:
                    httpClientPostTv.setText((String)msg.obj);
                    break;
                case PARSER_XML_PULL:
                    pullXMLTv.setText((String)msg.obj);
                    break;
                case SAX_PARSER_XML:
                    saxXMLTv.setText((String)msg.obj);
                    break;
                case JSON_OBJECT_PARSER_JSON:
                    jsonObjTv.setText((String)msg.obj);
                    break;
                case GSON_PARSER_JSON:
                    gsonTv.setText((String)msg.obj);
                    break;
            }
        }
    };

    public interface NetWorkCallBack{
        void onFinish(String content);
        void onError(String e);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initListeners() {
        webviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            WebViewShowActivity.startActivity(mInstance);
            }
        });

        httpUrlConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequestGET();
            }
        });

        httpUrlConnectionPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpURlConnectionContentPost();
            }
        });

        httpClientGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpClientGetData();
            }
        });

        httpClientPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpClientPostData();
            }
        });

        pullXMLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpClientGetPULLXML();
            }
        });

        saxXMLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saxParserXML();
            }
        });

        jsonObjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonObjParserJsonNet();
            }
        });

        gsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsonParsresjson();
            }
        });

        netWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.getNetWork(ENJOYGAME_JSON_ADDRESS, callBack);
            }
        });
    }

    private NetWorkCallBack callBack = new NetWorkCallBack() {
        @Override
        public void onFinish(String content) {
            parserJson(content);
        }

        @Override
        public void onError(String e) {
            Utils.log(TAG, e);
            Utils.showToastShort(mInstance, "request get json exception");
        }
    };

    private void gsonParsresjson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(ENJOYGAME_JSON_ADDRESS);
                String content = "";
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        content = EntityUtils.toString(entity, "utf-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(TextUtils.isEmpty(content)) {
                    Utils.log(TAG, "gson parser json fail");
                } else {
                    Gson gson = new Gson();
                    List<GameObject> list = gson.fromJson(content, new TypeToken<List<GameObject>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = GSON_PARSER_JSON;
                    message.obj = list.toString();
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void jsonObjParserJsonNet() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               HttpClient httpClient = new DefaultHttpClient();
               HttpGet httpGet = new HttpGet("https://s3-ap-southeast-1.amazonaws.com/enjoygame/enjoygames.json");
               String content = "";
               try {
                   HttpResponse httpResponse = httpClient.execute(httpGet);
                   if(httpResponse.getStatusLine().getStatusCode() == 200) {
                       HttpEntity entity = httpResponse.getEntity();
                       content = EntityUtils.toString(entity, "utf-8");
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
               if(TextUtils.isEmpty(content)) {
                   Utils.log(TAG, "json is null");
               } else {
                   Utils.log(TAG, "jsonObject parser json");
                   parserJson(content);
               }
           }
       }).start();
    }

    private void parserJson(String content) {
        ArrayList<GameObject> list = null;
        try {
            list = new ArrayList<GameObject>();
            GameObject gameObject = null;
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                gameObject = new GameObject();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                gameObject.setGameName(jsonObject.getString("gameName"));
                gameObject.setApkUrl(jsonObject.getString("apkUrl"));
                gameObject.setPicUrl(jsonObject.getString("picUrl"));
                gameObject.setPackageName(jsonObject.getString("packageName"));
                gameObject.setUid(jsonObject.getString("uid"));
                list.add(gameObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = JSON_OBJECT_PARSER_JSON;
        message.obj = list.toString();
        handler.sendMessage(message);
    }

    private void initViews() {
        webviewBtn = (Button)findViewById(R.id.eg_game_webview_btn);
        httpUrlConnectionBtn = (Button)findViewById(R.id.eg_game_httpurlconnection_btn);
        httpUrlConnectionContentTv = (TextView)findViewById(R.id.eg_game_httpurlconnection_tv);
        httpUrlConnectionPostBtn = (Button)findViewById(R.id.eg_game_httpurlconnection_post_btn);
        httpUrlConnectionContentPostTv = (TextView)findViewById(R.id.eg_game_httpurlconnection_post_tv);
        httpClientGetBtn = (Button)findViewById(R.id.eg_game_httpclient_get_btn);
        httpClientGetTv = (TextView)findViewById(R.id.eg_game_httpclient_get_tv);
        httpClientPostBtn = (Button)findViewById(R.id.eg_game_httpclient_post_btn);
        httpClientPostTv = (TextView)findViewById(R.id.eg_game_httpclient_post_tv);
        pullXMLBtn = (Button)findViewById(R.id.eg_game_pull_xml_btn);
        pullXMLTv = (TextView)findViewById(R.id.eg_game_pull_xml_tv);
        saxXMLBtn = (Button)findViewById(R.id.eg_game_sax_xml_btn);
        saxXMLTv = (TextView)findViewById(R.id.eg_game_sax_xml_tv);
        jsonObjBtn = (Button)findViewById(R.id.eg_game_jsonobject_btn);
        jsonObjTv = (TextView)findViewById(R.id.eg_game_jsonobject_tv);
        gsonBtn = (Button)findViewById(R.id.eg_game_gson_btn);
        gsonTv = (TextView)findViewById(R.id.eg_game_gson_tv);
        netWorkBtn = (Button)findViewById(R.id.eg_game_network_btn);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, NetWorkActivity.class);
        mActivity.startActivity(intent);
    }


    private void saxParserXML() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://s3-ap-southeast-1.amazonaws.com/enjoygame/enjoygame.xml");
                String content = "";
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httEntity = httpResponse.getEntity();
                        content = EntityUtils.toString(httEntity, "utf-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(TextUtils.isEmpty(content)) {
                    Utils.log(TAG, "sax get content fail");
                } else {
                    saxParserXMLByContent(content);
                }
            }
        }).start();
    }

    private void saxParserXMLByContent(String content) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        String contentResult = "";
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(content)));
            if(contentHandler.getArrayList() != null) {
                contentResult = contentHandler.getArrayList().toString();
            } else {
                Utils.log(TAG, "network get data fail(saxParserXMLByContent)");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = SAX_PARSER_XML;
        message.obj = contentResult;
        handler.sendMessage(message);
    }

    private void httpClientGetPULLXML() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://s3-ap-southeast-1.amazonaws.com/enjoygame/enjoygame.xml");
                String content = "";
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        content = EntityUtils.toString(entity, "utf-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(TextUtils.isEmpty(content)) {
                    Utils.log(TAG, "please check your network");
                } else {
                    Utils.log(TAG, "please wait for some time");
                    pullParserXML(content);
                }
            }
        }).start();
    }

    private void pullParserXML(String content) {
        ArrayList<Game> list = null;
        Game game = null;
        Utils.log(TAG, content);
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(content));
            int eventType = xmlPullParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("game".equals(nodeName)) {
                            game = new Game();
                        } else if("name".equals(nodeName)) {
                            game.setName(xmlPullParser.nextText());
                        } else if("pic".equals(nodeName)) {
                            game.setPic(xmlPullParser.nextText());
                        } else if("packageName".equals(nodeName)) {
                            game.setPackageName(xmlPullParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("game".equals(nodeName)){
                            list.add(game);
                            game = null;
                        }
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<Game>();
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            list = null;
            e.printStackTrace();
        } catch (IOException e) {
            list = null;
            e.printStackTrace();
        }
        Utils.log(TAG, list.toString());
        if(list == null) {
            Utils.log(TAG, "Pull parser xml fail");
        } else {
            Message message = new Message();
            message.what = PARSER_XML_PULL;
            message.obj = list.toString();
            handler.sendMessage(message);
        }
    }

    private void httpURlConnectionContentPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = "";
                HttpURLConnection httpUrlConnection = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setConnectTimeout(5000);
                    httpUrlConnection.setReadTimeout(5000);
                    OutputStream outputStream = httpUrlConnection.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeChars("password=123&use=admin");
                    int code = httpUrlConnection.getResponseCode();
                    if(code == 200) {
                        InputStream inputStream = httpUrlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        while((line = bufferedReader.readLine()) != null) {
                            content += line;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(httpUrlConnection != null) {
                        httpUrlConnection.disconnect();
                    }
                }
                Message message = new Message();
                if(TextUtils.isEmpty(content)) {
                    message.what = HTTPURL_CONNTECTION_POST_NULL;
                } else {
                    message.what = HTTPURL_CONNECTION_POST_BAIDU;
                }
                message.obj = content;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void httpClientPostData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://www.baidu.com");
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", "admin"));
                params.add(new BasicNameValuePair("password", "123456"));
                String content = "";
                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf-8");
                    httpPost.setEntity(urlEncodedFormEntity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        content = EntityUtils.toString(entity, "utf-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = HTTP_CLIENT_POST_STATE;
                if(TextUtils.isEmpty(content))
                    message.obj = "";
                else
                    message.obj = content;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void httpClientGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://www.baidu.com");
                String content = "";
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        content = EntityUtils.toString(entity, "utf-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = HTTP_CLIENT_GET_STATE;
                if(!TextUtils.isEmpty(content))
                    message.obj = content;
                else
                    message.obj = "";
                handler.sendMessage(message);
            }
        }).start();
    }

    private void getRequestGET() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpUrlConnection = null;
                String content = "";
                try {
                    URL url = new URL("http://www.baidu.com");
                    httpUrlConnection = (HttpURLConnection)url.openConnection();
                    httpUrlConnection.setRequestMethod("GET");
                    httpUrlConnection.setConnectTimeout(5000);
                    httpUrlConnection.setReadTimeout(5000);
                    int responseCode = httpUrlConnection.getResponseCode();
                    if(responseCode == 200) {
                        InputStream stream = httpUrlConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            content += line;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(httpUrlConnection != null) {
                        httpUrlConnection.disconnect();
                    }
                }
                Message message = new Message();
                if(TextUtils.isEmpty(content))
                    message.what = HTTPURL_CONNECTION_GET_NULL;
                else
                    message.what = HTTPURL_CONNECTION_GET_BAIDU;
                message.obj = content;
                handler.sendMessage(message);
            }
        }).start();
    }
}
