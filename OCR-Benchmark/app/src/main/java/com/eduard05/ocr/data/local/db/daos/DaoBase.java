package com.eduard05.ocr.data.local.db.daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface DaoBase<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert( T entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> entities);

    @Update
    void update(T entity);

    @Update
    void update(List<T> entities);

    @Delete
    void delete(T entity);

    @Delete
    void delete(List<T> entities);
}
