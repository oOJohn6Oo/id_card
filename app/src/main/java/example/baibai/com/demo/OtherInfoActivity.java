package example.baibai.com.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.baibai.com.demo.application.MyApplication;
import example.baibai.com.demo.receiver.MyReceiver;

public class OtherInfoActivity extends Activity {

    EditText et_phone;
    EditText et_email;
    EditText et_weChat;
    MyApplication application;
    List<CheckBox> checkBoxes;

    private BroadcastReceiver mBroadcastReceiver = new MyReceiver();
    private static final String BROADCAST_ACTION = "com.example.baibai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);

        initView();

        //手机号格式验证
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入的号码不规范  《==》1.字体颜色设置为红色
                if (!charSequence.toString().matches(getString(R.string.phone_regex))) {
                    et_phone.setTextColor(Color.RED);
                } else {
                    et_phone.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //邮箱格式验证
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入的邮箱不规范  《==》1.字体颜色设置为红色
                if (!charSequence.toString().matches(getString(R.string.email_regex))) {
                    et_email.setTextColor(Color.RED);
                } else {
                    et_email.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // “提交”按钮的点击事件
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();
                String wechat = et_weChat.getText().toString();
                // 格式判断
                if ((!"".equals(phone) && et_phone.getCurrentTextColor() == Color.RED)
                        || (!"".equals(email) && et_email.getCurrentTextColor() == Color.RED)) {
                    Toast.makeText(OtherInfoActivity.this, "输入的手机号或者邮箱格式不正确哦...", Toast.LENGTH_SHORT).show();
                } else {
                    //拼凑技能
                    StringBuilder skill = new StringBuilder();
                    for (CheckBox checkBox : checkBoxes) {
                        if (checkBox.isChecked()) {
                            skill.append(checkBox.getText()).append("、");
                        }
                    }
                    skill.deleteCharAt(skill.length() - 1);

                    //设置信息（手机号，邮箱，微信，技能）
                    application.getStudent().setPhone(phone).setEmail(email).setWeChat(wechat).setSkill(skill.toString());

                   //发送广播
                    Intent intent = new Intent();
                    intent.setAction(BROADCAST_ACTION);
                    intent.putExtra("stu", application.getStudent());
                    OtherInfoActivity.this.sendBroadcast(intent);
                }
            }
        });

    }


    private void initView() {
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        et_weChat = findViewById(R.id.et_weChat);
        application = (MyApplication) getApplication();
        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.music));
        checkBoxes.add((CheckBox) findViewById(R.id.arts));
        checkBoxes.add((CheckBox) findViewById(R.id.handwriting));
        checkBoxes.add((CheckBox) findViewById(R.id.basketball));
        checkBoxes.add((CheckBox) findViewById(R.id.football));
        checkBoxes.add((CheckBox) findViewById(R.id.swimming));

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        registerReceiver(mBroadcastReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
