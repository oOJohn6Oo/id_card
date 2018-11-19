package example.baibai.com.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import example.baibai.com.demo.application.MyApplication;

public class BasicInfoActivity extends Activity {

    EditText et_schoolNO;
    EditText et_name;
    EditText et_ID;
    RadioButton radioButton_man;
    RadioButton radioButton_woman;
    EditText et_address;
    EditText et_year;
    EditText et_month;
    EditText et_day;
    MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        initView();

        //身份证验证
        et_ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches(getResources().getString(R.string.id_regex))) {
                    et_ID.setTextColor(Color.RED);
                } else {
                    et_ID.setTextColor(Color.BLACK);
                    //解析失败
                    if (!ID2Info()) {
                        et_ID.setTextColor(Color.RED);
                        Toast.makeText(BasicInfoActivity.this, "解析失败，身份证号码可能无效", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 按钮点击事件
        findViewById(R.id.btn_next_basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myNO = et_schoolNO.getText().toString().trim();
                if ("".equals(myNO)) {
                    application.toastRequest(BasicInfoActivity.this);
                    return;
                }
                String myName = et_name.getText().toString().trim();
                if ("".equals(myName)) {
                    application.toastRequest(BasicInfoActivity.this);
                    return;
                }
                String myID = et_ID.getText().toString().trim();
                if ("".equals(myID)) {
                    application.toastRequest(BasicInfoActivity.this);
                    return;
                }

                if(!myID.matches(getResources().getString(R.string.id_regex))){
                    Toast.makeText(BasicInfoActivity.this, "身份证格式不正确,如果包含‘x’请大写哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sex = null;
                if (radioButton_man.isChecked()) {
                    sex = getResources().getString(R.string.sex_label_male);
                } else if (radioButton_woman.isChecked()) {
                    sex = getResources().getString(R.string.sex_label_female);
                } else {
                    Toast.makeText(BasicInfoActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                    return;
                }

                application.getStudent().setNO(myNO)
                        .setName(myName)
                        .setID(myID)
                        .setSex(sex)
                        .setAddress(et_address.getText().toString())
                        .setBirthday(getResources().getString(R.string.birthday,et_year.getText().toString(),
                                et_month.getText().toString(),et_day.getText().toString()));

                startActivity(new Intent(BasicInfoActivity.this, SchoolInfoActivity.class));
            }
        });
    }

    private void initView() {
        application = (MyApplication) getApplication();
        et_schoolNO = findViewById(R.id.et_school_no);
        et_name = findViewById(R.id.et_name);
        et_ID = findViewById(R.id.et_id);
        radioButton_man = findViewById(R.id.radio_man);
        radioButton_woman = findViewById(R.id.radio_woman);
        et_address = findViewById(R.id.et_address);
        et_year = findViewById(R.id.et_year);
        et_month = findViewById(R.id.et_month);
        et_day = findViewById(R.id.et_day);
    }

    private boolean ID2Info() {
        //拿到用户输入的身份证号
        String id = et_ID.getText().toString();
        //获得年月日
        int yy = Integer.parseInt(id.substring(6, 10));
        int MM = Integer.parseInt(id.substring(10, 12));
        int dd = Integer.parseInt(id.substring(12, 14));

        //获得性别
        int sex = Integer.parseInt(id.substring(16,17));

        //设置性别
        if(sex%2==0){
            radioButton_woman.setChecked(true);
        }else{
            radioButton_man.setChecked(true);
        }
        //填入年月日(为使类似09的格式转为9)
        et_year.setText(String.valueOf(yy));
        et_month.setText(String.valueOf(MM));
        et_day.setText(String.valueOf(dd));
        //sax解析
        try {
            Map<String,String> result = sax2xml(getResources().getAssets().open("idInfo"));
            //填入籍贯
            et_address.setText(result.get(id.substring(0,6)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
                        xmlData.put(tempKey,tempValue);
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
