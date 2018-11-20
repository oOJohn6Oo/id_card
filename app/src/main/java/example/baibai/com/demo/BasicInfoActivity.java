package example.baibai.com.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import example.baibai.com.demo.application.MyApplication;
import example.baibai.com.demo.service.NativePlaceService;

public class BasicInfoActivity extends Activity {

    private Intent getInfoFormID;
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

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                et_ID.setTextColor(Color.RED);
                Toast.makeText(BasicInfoActivity.this, "解析失败，身份证号码可能无效(带‘X’的号码请大写哦)", Toast.LENGTH_SHORT).show();
            } else {
                et_year.setText(msg.getData().getString("year", ""));
                et_month.setText(msg.getData().getString("month", ""));
                et_day.setText(msg.getData().getString("day", ""));
                et_address.setText(msg.getData().getString("address", ""));

                if (msg.getData().getInt("sex") % 2 == 0) {
                    radioButton_woman.setChecked(true);
                } else {
                    radioButton_man.setChecked(true);
                }
            }
        }
    };

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
                //身份证格式错误，字体红色
                if (!charSequence.toString().matches(getResources().getString(R.string.id_regex))) {
                    et_ID.setTextColor(Color.RED);
                } else { //格式正确，字体黑色
                    et_ID.setTextColor(Color.BLACK);

                    //输入的身份证字符存入intent
                    getInfoFormID.putExtra("ID", charSequence.toString());
                    getInfoFormID.putExtra("messenger", new Messenger((handler)));
                    //启动服务
                    startService(getInfoFormID);
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

                if (!myID.matches(getResources().getString(R.string.id_regex))) {
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
                        .setBirthday(getResources().getString(R.string.birthday, et_year.getText().toString(),
                                et_month.getText().toString(), et_day.getText().toString()));

                startActivity(new Intent(BasicInfoActivity.this, SchoolInfoActivity.class));
            }
        });
    }

    private void initView() {
        getInfoFormID = new Intent(this, NativePlaceService.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(getInfoFormID);
    }
}
