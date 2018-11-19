package example.baibai.com.demo.bean;

import android.content.Context;

import java.io.Serializable;

import example.baibai.com.demo.R;

public class Student implements Serializable {
    private String college;
    private String major;
    private String cls;
    private String name;
    private String ID;
    private String sex;
    private String NO;
    private String phone;
    private String email;
    private String weChat;
    private String address;
    private String birthday;
    private String skill;

    public Student() {
    }

    public String getCollege() {
        return college;
    }

    public Student setCollege(String college) {
        this.college = college;
        return this;
    }

    public String getMajor() {
        return major;
    }

    public Student setMajor(String major) {
        this.major = major;
        return this;
    }

    public String getCls() {
        return cls;
    }

    public Student setCls(String cls) {
        this.cls = cls;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getID() {
        return ID;
    }

    public Student setID(String ID) {
        this.ID = ID;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public Student setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getNO() {
        return NO;
    }

    public Student setNO(String NO) {
        this.NO = NO;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Student setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getWeChat() {
        return weChat;
    }

    public Student setWeChat(String weChat) {
        this.weChat = weChat;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Student setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public Student setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getSkill() {
        return skill;
    }

    public Student setSkill(String skill) {
        this.skill = skill;
        return this;
    }

    public String show(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getResources().getString(R.string.name_label)).append(this.name).append("\n")
                .append(context.getResources().getString(R.string.stu_id_label)).append(this.NO).append("\n")
                .append(context.getResources().getString(R.string.id_number_label)).append(this.ID).append("\n")
                .append(context.getResources().getString(R.string.sex_label)).append(this.sex).append("\n")
                .append(context.getResources().getString(R.string.native_place_label)).append(this.address).append("\n")
                .append(context.getResources().getString(R.string.birthday_label)).append(this.birthday).append("\n")
                .append(context.getResources().getString(R.string.school_label)).append(this.college).append("\n")
                .append(context.getResources().getString(R.string.stu_class_label)).append(this.cls).append("\n")
                .append(context.getResources().getString(R.string.phone_label)).append(this.phone).append("\n")
                .append(context.getResources().getString(R.string.email_label)).append(this.email).append("\n")
                .append(context.getResources().getString(R.string.weixin_label)).append(this.weChat).append("\n")
                .append(context.getResources().getString(R.string.special_skill_label)).append(this.skill);

        return stringBuilder.toString();
    }
}
