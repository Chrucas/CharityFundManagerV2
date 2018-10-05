package com.example.drchruc.charityfundmanagerv2.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.drchruc.charityfundmanagerv2.Entities.Charity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@SuppressWarnings("unused")
@Dao
public interface CharityDao {

    @Insert
    void insert(Charity charity);

    /**
     * update queries
     *
     */

    @Update
    void update(Charity charity);

    /**
     *  delete queries
     *
     */

    @Delete
    void delete(Charity charity);

    @Query("DELETE FROM charity_table")
    void deleteAll();


    /**
     *  Select queries
     *
     */
    @Query("SELECT * from charity_table ORDER BY charity ASC")
    LiveData<List<Charity>> getAllCharitiesLive();

    @Query("SELECT * from charity_table ORDER BY charity ASC")
    List<Charity> getAllCharities();

    @Query("SELECT * FROM charity_table WHERE charity=:name")
    Charity getCharity(String name);


    @Query("SELECT * from charity_table WHERE charity = :name")
    LiveData<List<Charity>> getCharityByName(String name);




}
