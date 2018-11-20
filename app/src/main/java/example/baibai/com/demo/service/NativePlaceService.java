package example.baibai.com.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class NativePlaceService extends Service {

    private int sex;
    private String address;
    private String year;
    private String month;
    private String day;
    private int code;

    private Map<String, String> result;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                code = 1;
                //拿到用户输入的身份证号
                String id = intent.getStringExtra("ID");

                try {
                    //获得年月日
                    int yy = Integer.parseInt(id.substring(6, 10));
                    int MM = Integer.parseInt(id.substring(10, 12));
                    int dd = Integer.parseInt(id.substring(12, 14));

                    //获得性别
                    sex = Integer.parseInt(id.substring(16, 17));
                    //年月日(为使类似09的格式转为9)
                    year = String.valueOf(yy);
                    month = String.valueOf(MM);
                    day = String.valueOf(dd);
                    //sax解析
                    if (result == null) {
                        result = sax2xml(getResources().getAssets().open("idInfo"));
                    }
                    //籍贯
                    address = result.get(id.substring(0, 6));
                    if(address==null || address.equals(""))
                        code = -1;
                } catch (Exception e) {
                    code = -1;
                }
                Message message = new Message();
                message.what = code;
                Bundle bundle = new Bundle();
                bundle.putInt("sex", sex);
                bundle.putString("year", year);
                bundle.putString("month", month);
                bundle.putString("day", day);
                bundle.putString("address", address);

                message.setData(bundle);
                try {
                    ((Messenger)intent.getExtras().get("messenger")).send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * sax解析
     */
    public Map<String, String> sax2xml(InputStream is) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        //初始化Sax解析器
        SAXParser sp = spf.newSAXParser();
        //新建解析处理器
        MyHandler handler = new MyHandler();
        //将解析交给处理器
        sp.parse(is, handler);
        //返回List
        return handler.getXmlData();
    }

    public class MyHandler extends DefaultHandler {

        private Map<String, String> xmlData;
        //用于存储读取的临时变量
        private String tempKey;
        private String tempValue;

        @Override
        public void startDocument() throws SAXException {
            xmlData = new HashMap<>();
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("code".equals(qName)) {
                tempKey = attributes.getValue("id");
            }

            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("name".equals(qName)) {
                xmlData.put(tempKey, tempValue);
            }
            super.endElement(uri, localName, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            tempValue = new String(ch, start, length);
            super.characters(ch, start, length);
        }


        /*
            获取国家、省、城市、县级的集合
         */
        public Map<String, String> getXmlData() {
            return xmlData;
        }
    }

}
