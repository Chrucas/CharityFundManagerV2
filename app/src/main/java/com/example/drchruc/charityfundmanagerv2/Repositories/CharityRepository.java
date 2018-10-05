package com.example.drchruc.charityfundmanagerv2.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.drchruc.charityfundmanagerv2.Adapters.CharityListAdapter;
import com.example.drchruc.charityfundmanagerv2.Database.CharityRoomDatabase;
import com.example.drchruc.charityfundmanagerv2.DAO.CharityDao;
import com.example.drchruc.charityfundmanagerv2.Entities.Charity;
import com.example.drchruc.charityfundmanagerv2.MainActivity;

import java.util.List;

public class CharityRepository {

    public final static int TASK_DELETE_CHARITY = 1;
    public final static int TASK_UPDATE_CHARITY = 2;
    public final static int TASK_INSERT_CHARITY = 3;

    private final CharityDao mCharityDao;
    private LiveData<List<Charity>> mAllCharities;
    private List<Charity> mCharities;
    private CharityListAdapter mAdapter;



    public CharityRepository(Application application) {
        CharityRoomDatabase db = CharityRoomDatabase.getDatabase(application);
        mCharityDao = db.charityDao();
        mAllCharities = mCharityDao.getAllCharitiesLive();
    }

    public LiveData<List<Charity>> getAllCharities(){
        return mAllCharities;
    }

    public void insert(Charity charity){
        new insertAsyncTask(mCharityDao).execute(charity);
    }

    public void update(Charity charity){
        new updateAsyncTask(mCharityDao).execute(charity);
    }

    public void delete(Charity charity){
        new deleteAsyncTask(mCharityDao).execute(charity);
    }

    private static class insertAsyncTask extends AsyncTask<Charity, Void, Void> {

        private CharityDao mAsyncTaskDao;

        insertAsyncTask(CharityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Charity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class updateAsyncTask extends AsyncTask<Charity, Void, Void> {

        private CharityDao mAsyncTaskDao;

        updateAsyncTask(CharityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Charity... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Charity, Void, Void> {

        private CharityDao mAsyncTaskDao;

        deleteAsyncTask(CharityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Charity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void onReminderDbUpdated(List list) {

        mCharities = list;
        updateUI();

    }


    private void updateUI() {
            mAdapter.swapList(mCharities);
    }
}


