package example.baibai.com.demo.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import example.baibai.com.demo.bean.Student;

public class MyApplication extends Application {
    private Student student;

    public MyApplication() {
        student = new Student();
    }

    public Student getStudent() {
        return student;
    }


    public void toastRequest(Context context){
        Toast.makeText(context,"请填写必要信息",Toast.LENGTH_SHORT).show();
    }
}
