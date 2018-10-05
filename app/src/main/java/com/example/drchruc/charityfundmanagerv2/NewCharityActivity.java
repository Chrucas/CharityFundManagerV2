package com.example.drchruc.charityfundmanagerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drchruc.charityfundmanagerv2.Entities.Charity;

public class NewCharityActivity extends AppCompatActivity {

    public static final String EXTRA_REPLYSTRING = "EXTRA_REPLY";
    public static final String EXTRA_REPLYINT = "EXTRA_REPLY_INT";


    private EditText mNewCharityView;
    private EditText mNewFundsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_charity);
        mNewCharityView = findViewById(R.id.new_charity);
        mNewFundsView = findViewById(R.id.new_funds);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mNewCharityView.getText())||TextUtils.isEmpty(mNewFundsView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String text = mNewCharityView.getText().toString();
                    int funds = Integer.parseInt(mNewFundsView.getText().toString());
                    Charity charity = new Charity(text, funds);

                    replyIntent.putExtra(EXTRA_REPLYSTRING, charity);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
