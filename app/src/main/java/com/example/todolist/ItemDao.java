package com.example.todolist;


import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Item item);


    @Query("UPDATE item_table SET name=:name,description=:description,date=:date,image=:image WHERE id = :id")
    void update(int id, String name,String description, Date date, byte [] image);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item_table")
    void deleteAllItems();

    @Query("SELECT * FROM item_table ORDER BY date DESC ")
    List<Item> getAllItems();



}
