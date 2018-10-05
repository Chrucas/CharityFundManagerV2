package com.example.drchruc.charityfundmanagerv2.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drchruc.charityfundmanagerv2.Entities.Charity;
import com.example.drchruc.charityfundmanagerv2.R;

import java.util.List;

public class CharityListAdapter extends RecyclerView.Adapter<CharityListAdapter.CharityViewHolder> {

    private List<Charity> charityList;
    private final LayoutInflater mInflater;

    private final CharityClickListener mCharityClickListener;

    public interface CharityClickListener{
        void charityOnClick (int i);
    }

    public CharityListAdapter(List<Charity> charityList, CharityClickListener mCharityClickListener, Context context) {
        this.charityList = charityList;
        this.mCharityClickListener = mCharityClickListener;
        mInflater = LayoutInflater.from(context);
    }

    public class CharityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView charityItemView;
        private final TextView fundsItemView;
        public RelativeLayout viewBackground, viewForeground;

        private CharityViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            charityItemView = itemView.findViewById(R.id.charity_name);
            fundsItemView = itemView.findViewById(R.id.charity_funds);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }

        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mCharityClickListener.charityOnClick(clickedPosition);
        }
    }


    @Override
    public CharityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CharityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CharityViewHolder holder, int position) {
        if (charityList != null) {
            Charity current = charityList.get(position);
            holder.charityItemView.setText(current.getCharity());
            holder.fundsItemView.setText(String.valueOf(current.getFunds()));

        } else {
            // Covers the case of data not being ready yet.
            holder.charityItemView.setText(R.string.no_charity);
        }
    }

    public void setCharities(List<Charity> charities){
        charityList = charities;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (charityList != null)
            return charityList.size();
        else return 0;
    }

    public void swapList (List<Charity> newList) {
        charityList = newList;

        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
