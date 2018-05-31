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

    public Expense(String type, String value, String description, Long published, String userKey) {
        this.type = type;
        this.value = value;
        this.description = description;
        this.published = published;
        this.userKey = userKey;
    }

    public Expense setKey(String key){
        this.key = key;
        return this;
    }
}
