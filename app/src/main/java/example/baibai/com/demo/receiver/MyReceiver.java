package example.baibai.com.demo.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import example.baibai.com.demo.bean.Student;
import example.baibai.com.demo.helper.SQLiteDbHelper;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final Student student = (Student) intent.getSerializableExtra("stu");
        new AlertDialog.Builder(context)
                .setTitle("个人信息确认")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDbHelper helper = new SQLiteDbHelper(context);
                SQLiteDatabase database = helper.getWritableDatabase();
                if (database.insertOrThrow(SQLiteDbHelper.TABLE_STUDENT, null, getContentValues(student)) != -1) {
                    Toast.makeText(context, "写入数据库成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "写入数据库失败,数据已存在", Toast.LENGTH_SHORT).show();
                }
                dialogInterface.dismiss();
            }
        }).setMessage(student.show(context)).show();
    }

    private ContentValues getContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", student.getNO());
        contentValues.put("name", student.getName());
        contentValues.put("id_number", student.getID());
        contentValues.put("sex", student.getSex());
        contentValues.put("native_place", student.getAddress());
        contentValues.put("birthday", student.getBirthday());
        contentValues.put("college", student.getCollege());
        contentValues.put("major", student.getMajor());
        contentValues.put("student_class", student.getCls());
        contentValues.put("phone", student.getPhone());
        contentValues.put("email", student.getEmail());
        contentValues.put("weixin_number", student.getWeChat());
        contentValues.put("special_skill", student.getSkill());
        return contentValues;
    }
}
