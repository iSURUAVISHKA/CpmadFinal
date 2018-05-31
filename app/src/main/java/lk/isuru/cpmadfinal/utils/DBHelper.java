package lk.isuru.cpmadfinal.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {

    FirebaseDatabase database;
    DatabaseReference reference;

    public DatabaseReference getReference() {
        return reference;
    }

    public DatabaseReference getReference(String path){
        return reference.child(path);
    }

    public DBHelper() {
        super();
        database = FirebaseDatabase.getInstance();
        try{
            database.setPersistenceEnabled(true);
        }catch (Exception ignored){
        }
        reference = database.getReference();
    }

    public String pushObject(String path, Object object){
        String key = reference.child(path).push().getKey();
        reference.child(path).child(key).setValue(object);
        return key;
    }

    public void updateObject(String path, Object object){
        reference.child(path).setValue(object);
    }

    public void deleteObject(String path){
        reference.child(path).setValue(null);
    }
}
