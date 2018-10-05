package com.example.drchruc.charityfundmanagerv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drchruc.charityfundmanagerv2.Adapters.CharityListAdapter;
import com.example.drchruc.charityfundmanagerv2.Entities.Charity;

public class EditCharityActivity extends AppCompatActivity {

    private static final String EXTRA_REPLYSTRING = "EXTRA_REPLY";
    private static final String EXTRA_REPLYINT = "EXTRA_REPLY_INT";
    EditText mEditCharityView;
    EditText mEditFundsView;
    Button button;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_charity);

        mEditCharityView = findViewById(R.id.edit_charity);
        mEditFundsView = findViewById(R.id.edit_funds);

        button = findViewById(R.id.edit_button_save);

        final Charity updatedCharity = getIntent().getParcelableExtra(MainActivity.EXTRA_CHARITY);
        mEditCharityView.setText(updatedCharity.getCharity());
        mEditFundsView.setText(Integer.toString(updatedCharity.getFunds()));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditCharityView.getText().toString();

                String charity = mEditCharityView.getText().toString();
                int funds = Integer.parseInt(mEditFundsView.getText().toString());

                if(!TextUtils.isEmpty(text) && !TextUtils.isEmpty(String.valueOf(funds))) {
                    Intent replyIntent = new Intent();
                    updatedCharity.setCharity(charity);
                    updatedCharity.setFunds(funds);
                    replyIntent.putExtra(MainActivity.EXTRA_CHARITY, updatedCharity);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                    } else {
                    Snackbar.make(view, "Enter some charity data", Snackbar.LENGTH_LONG);
                }


            }
        });

    }

}
