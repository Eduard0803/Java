package com.eduard05.ocr.data.local.db.localRepositories;

import android.content.Context;

import androidx.room.Room;

import com.eduard05.ocr.data.local.db.AppDatabase;
import com.eduard05.ocr.data.local.db.daos.DaoBase;

import java.util.List;

public abstract class  Repository<T> {

    protected static volatile AppDatabase db;
    protected DaoBase<T> dao;

    public Repository(Context context){
        if ( db == null)
            db = create(context);
    }

    private static AppDatabase create(final Context context) {
        return  Room.databaseBuilder( context, AppDatabase.class, "appCollector" )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }

    public long insert( T entity){
        return dao.insert(entity);
    }

    public void insert(List<T> entities){
        dao.insert(entities);
    }

    public void update(T entity){
        dao.update(entity);
    }

    public void update(List<T> entities){
        dao.update(entities);

    }

    public void delete(T entity){
        dao.delete(entity);
    }

    public void delete(List<T> entities){
        dao.delete(entities);
    }

    public void setDao(DaoBase<T> dao) {
        this.dao = dao;
    }
}
