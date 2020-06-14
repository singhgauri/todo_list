package com.example.todoList;
import java.io.Serializable;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName= "item_table")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String name;

    private final String description;

    private final Date date;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private final byte [] image;

    public Item(String name, String description, Date date, byte [] image) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getImage() {
        return image;
    }
}
