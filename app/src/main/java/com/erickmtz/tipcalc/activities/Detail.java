package com.erickmtz.tipcalc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.erickmtz.tipcalc.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Detail extends AppCompatActivity {

    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.tip)
    TextView tip;
    @Bind(R.id.date)
    TextView date;

    public static String detailAmount = "amount";
    public static String detailTip = "tip";
    public static String detailDate= "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        String strAmount = String.format(getString(R.string.tipdetail_message_bill), intent.getDoubleExtra(detailAmount, 0d));
        String strTip = String.format(getString(R.string.global_message_tip), intent.getDoubleExtra(detailTip, 0d));


        String strDate = intent.getStringExtra(detailDate);

        amount.setText(strAmount);
        tip.setText(strTip);
        date.setText(strDate);

    }




}
