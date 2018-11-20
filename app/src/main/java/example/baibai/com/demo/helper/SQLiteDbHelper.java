package example.baibai.com.demo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.baibai.com.demo.bean.Student;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "database.db";

    public static final int DB_VERSION = 1;

    public static final String TABLE_STUDENT = "students";

    //创建 students 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_SQL = "create TABLE IF NOT EXISTS " + TABLE_STUDENT + "("
            + "student_id integer primary key,"
            + "name varchar not null,"
            + "id_number varchar not null,"
            + "sex varchar not null,"
            + "native_place varchar not null,"
            + "birthday varchar not null,"
            + "college varchar not null,"
            + "major varchar not null,"
            + "student_class varchar not null,"
            + "phone varchar,"
            + "email varchar,"
            + "weixin_number varchar,"
            + "special_skill varchar"
            + ");";

    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
