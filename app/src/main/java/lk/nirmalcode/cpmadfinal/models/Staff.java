package lk.nirmalcode.cpmadfinal.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Staff {

    public String key;
    public String id;
    public String name;
    public String email;
    public String password;
    public String contact;


    public Staff() {
    }

    public Staff(String id, String name, String email, String password, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    public Staff setKey(String key){
        this.key = key;
        return this;
    }
}
