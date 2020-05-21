package com.example.todolist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static android.content.ContentValues.TAG;

@Database(entities = {Item.class},version = 3,exportSchema = false)
@TypeConverters({DateConverter.class})


public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),ItemDatabase.class, "test_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private ItemDao itemDao;

        private PopulateDbAsyncTask(ItemDatabase db) {
            itemDao = db.itemDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //itemDao.insert(new Item("Name 1","Description 1",16));
            //itemDao.insert(new Item("Name 2","Description 2",17));
            //itemDao.insert(new Item("Name 3","Description 3",18));
            Log.d(TAG,"inserted");

            return null;
        }
    }

}
