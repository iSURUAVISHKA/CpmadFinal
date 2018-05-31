package lk.nirmalcode.cpmadfinal.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {

    public String key;
    public String id;
    public String name;
    public String year;
    public String faculty;
    public String batch;
    public String course;
    public String email;
    public String contact;

    public Student() {
    }

    public Student(String id, String name, String year, String faculty, String batch, String course, String email, String contact) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.faculty = faculty;
        this.batch = batch;
        this.course = course;
        this.email = email;
        this.contact = contact;
    }

    public Student setKey(String key){
        this.key = key;
        return this;
    }
}
