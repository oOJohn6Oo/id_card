package example.baibai.com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import example.baibai.com.demo.application.MyApplication;

public class SchoolInfoActivity extends Activity {
    Spinner spinner;
    EditText et_major;
    EditText et_class;
    MyApplication application;

    ArrayAdapter<CharSequence> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        initView();

        findViewById(R.id.btn_next_school).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String major = et_major.getText().toString().trim();
                String cls = et_class.getText().toString().trim();
                if ("".equals(major) || "".equals(cls)) {
                    application.toastRequest(SchoolInfoActivity.this);
                    return;
                } else {
                    application.getStudent().setCollege(myAdapter.getItem(spinner.getSelectedItemPosition()).toString())
                            .setMajor(major)
                            .setCls(cls);
                }
                startActivity(new Intent(SchoolInfoActivity.this, OtherInfoActivity.class));
            }
        });
    }

    private void initView() {
        spinner = findViewById(R.id.spinner);
        et_major = findViewById(R.id.et_major);
        et_class = findViewById(R.id.et_class);
        application = (MyApplication) getApplication();

        myAdapter = ArrayAdapter.createFromResource(this, R.array.school_list, android.R.layout.simple_spinner_dropdown_item);

        //设置下拉列表
        spinner.setAdapter(myAdapter);
        spinner.setSelection(0);
    }
}
