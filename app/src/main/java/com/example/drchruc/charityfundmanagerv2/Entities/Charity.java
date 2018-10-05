package com.example.drchruc.charityfundmanagerv2.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "charity_table")
public class Charity implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private Long id;

    static Long count = 1L;



    @ColumnInfo(name = "charity")
    @NonNull
    private String mCharity;

    @ColumnInfo(name = "funds")
    @NonNull
    private int mFunds;

    @NonNull
    public Long getId() {
        return id;
    }

    public int getFunds() {
        return mFunds;
    }

    public String getCharity() {
        return mCharity;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setCharity(@NonNull String mCharity) {
        this.mCharity = mCharity;
    }

    public void setFunds(@NonNull int mFunds) {
        this.mFunds = mFunds;
    }

    public Charity(@NonNull String charity, int funds) {
        this.mCharity = charity;
        mFunds = funds;
        this.id = count++;
    }

    @Override
    public String toString() {
        return "Charity{" +
                "mCharity='" + mCharity + '\'' +
                ", mFunds=" + mFunds +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parc, int i) {
        parc.writeLong(this.id);
        parc.writeString(this.mCharity);
        parc.writeInt(this.mFunds);

    }

    protected Charity(Parcel in) {
        //if (in.readByte() == 0) {
            //id = null;
        //} else {
            id = in.readLong();
        //}
        mCharity = in.readString();
        mFunds = in.readInt();
    }

    public static final Creator<Charity> CREATOR = new Creator<Charity>() {
        @Override
        public Charity createFromParcel(Parcel in) {
            return new Charity(in);
        }

        @Override
        public Charity[] newArray(int size) {
            return new Charity[size];
        }
    };


}
