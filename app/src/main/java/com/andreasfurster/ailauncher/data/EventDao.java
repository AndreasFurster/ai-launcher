package com.andreasfurster.ailauncher.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EventDao {
    @Query("SELECT * FROM eventDbo LIMIT :amount")
    List<EventDbo> selectTop(int amount);

    @Insert
    void insert(EventDbo... items);

    @Update
    void update(EventDbo... items);

    @Delete
    void delete(EventDbo item);
}
