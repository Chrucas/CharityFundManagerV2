package com.example.drchruc.charityfundmanagerv2.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.drchruc.charityfundmanagerv2.DAO.CharityDao;
import com.example.drchruc.charityfundmanagerv2.Entities.Charity;

@Database(entities = {Charity.class}, version = 2, exportSchema = false)
public abstract class CharityRoomDatabase extends RoomDatabase {

    public abstract CharityDao charityDao();

    private final static String NAME_DATABASE = "charity_db";

    private static volatile CharityRoomDatabase INSTANCE;



    public static CharityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CharityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CharityRoomDatabase.class, NAME_DATABASE)
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
//
//        @Override
//        public void onOpen (@NonNull SupportSQLiteDatabase db){
//            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    };
//
//        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//            private final CharityDao mDao;
//
//            PopulateDbAsync(CharityRoomDatabase db) {
//                mDao = db.charityDao();
//            }
//
//            @Override
//            protected Void doInBackground(final Void... params){
//                mDao.deleteAll();
//                Charity charity = new Charity("Red Cross", 23);
//                mDao.insert(charity);
//                charity = new Charity("Green Peace", 44);
//                mDao.insert(charity);
//                return null;
//            }
//        }

}
