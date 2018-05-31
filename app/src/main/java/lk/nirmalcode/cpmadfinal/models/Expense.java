package lk.nirmalcode.cpmadfinal.models;

public class Expense {

    public String key;
    public String type;
    public String value;
    public String description;
    public Long published;
    public String userKey;

    public Expense() {
    }

    public Expense(String type, String value, String description) {
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public Expense setKey(String key){
        this.key = key;
        return this;
    }
}
