package custom.entity;

import com.conferences.annotation.Column;
import com.conferences.annotation.Table;

@Table(name = "test_table")
public class AllAutoGeneratedFields {

    @Column(name = "id", key = true, autoGenerated = true)
    private int id;

    @Column(name = "primary_key", autoGenerated = true)
    private String primaryKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}