package com.example.drchruc.charityfundmanagerv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drchruc.charityfundmanagerv2.Adapters.CharityListAdapter;
import com.example.drchruc.charityfundmanagerv2.Database.CharityRoomDatabase;
import com.example.drchruc.charityfundmanagerv2.Entities.Charity;
import com.example.drchruc.charityfundmanagerv2.Repositories.CharityRepository;
import com.example.drchruc.charityfundmanagerv2.ViewModels.CharityViewModel;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements CharityListAdapter.CharityClickListener{

    private RecyclerView recyclerView;
    private List<Charity> charityList;
    private CharityListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;
    private TextView charityName;
    private CharityRepository mRepository;

    //Constants used when calling the edit activity
    public static final String EXTRA_CHARITY = "charity";
    public static final String EXTRA_FUNDS = "funds";
    private static final int EDIT_CHARITY_ACTIVITY_REQUEST_CODE = 1234;
    private int mModifyPosition;

    private static final int NEW_CHARITY_ACTIVITY_REQUEST_CODE = 1;

    public final static int TASK_GET_ALL_CHARITIES = 0;
    public final static int TASK_DELETE_CHARITY = 1;
    public final static int TASK_UPDATE_CHARITY = 2;
    public final static int TASK_INSERT_CHARITY = 3;

    private CharityViewModel mCharityViewModel;

    static CharityRoomDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        db = CharityRoomDatabase.getDatabase(this);

        new CharityAsyncTask(TASK_GET_ALL_CHARITIES).execute();

        charityList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        updateUI();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        new CharityAsyncTask(TASK_DELETE_CHARITY).execute(charityList.get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCharityActivity.class);
                startActivityForResult(intent, NEW_CHARITY_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCharityViewModel = ViewModelProviders.of(this).get(CharityViewModel.class);

        mCharityViewModel.getAllCharities().observe(this, new Observer<List<Charity>>() {
            @Override
            public void onChanged(@Nullable List<Charity> charities) {
                mAdapter.setCharities(charities);
            }
        });
    }

    public void onReminderDbUpdated(List<Charity> list) {

        charityList = list;
        updateUI();

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new CharityListAdapter(charityList, this, this);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(charityList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void charityOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, EditCharityActivity.class);
        mModifyPosition = i;
        intent.putExtra(EXTRA_CHARITY, charityList.get(i));
        startActivityForResult(intent, EDIT_CHARITY_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CHARITY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Charity charity = data.getParcelableExtra(NewCharityActivity.EXTRA_REPLYSTRING);
                new CharityAsyncTask(TASK_INSERT_CHARITY).execute(charity);
//             charityList.add(charity);
//             updateUI();
            }
        }
        if(requestCode == EDIT_CHARITY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Charity updatedCharity = data.getParcelableExtra(MainActivity.EXTRA_CHARITY);
                new CharityAsyncTask(TASK_UPDATE_CHARITY).execute(updatedCharity);
                updateUI();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    public class CharityAsyncTask extends AsyncTask<Charity, Void, List> {

        private int taskCode;

        CharityAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }


        @Override
        protected List doInBackground(Charity... charities) {
            switch (taskCode) {
                case TASK_DELETE_CHARITY:
                    db.charityDao().delete(charities[0]);
                    break;

                case TASK_UPDATE_CHARITY:
                    db.charityDao().update(charities[0]);
                    break;

                case TASK_INSERT_CHARITY:
                    db.charityDao().insert(charities[0]);
                    break;
            }


            //To return a new list with the updated data, we get all the data from the database again.
            return db.charityDao().getAllCharities();
        }


        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onReminderDbUpdated(list);
        }

    }
}
