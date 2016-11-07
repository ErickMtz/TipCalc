package com.erickmtz.tipcalc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatProperty;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.erickmtz.tipcalc.R;
import com.erickmtz.tipcalc.TipCalcApp;
import com.erickmtz.tipcalc.db.TipsDataBase;
import com.erickmtz.tipcalc.fragments.TipHistoryListFragment;
import com.erickmtz.tipcalc.fragments.TipHistoryListFragmentListener;
import com.erickmtz.tipcalc.entity.TipRecord;
import com.erickmtz.tipcalc.utils.TipUtils;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.Date;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.btnIncrease)
    Button btnIncrease;
    @Bind(R.id.btnDecrease)
    Button btnDecrease;
    @Bind(R.id.btnClear)
    Button btnClear;
    @Bind(R.id.txtTip)
    TextView txtTip;

    private TipHistoryListFragmentListener fragmentListener;

    private final static int TIP_STEP_CHANGE = 1;
    private final static int DEFAULT_TIP_CHANGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDb();

        TipHistoryListFragment fragment =(TipHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentList);

        fragment.setRetainInstance(true);

        fragmentListener = (TipHistoryListFragmentListener) fragment;
        fragmentListener.initList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initDb() {
        FlowManager.init(new FlowConfig.Builder(this).build()); //inicializa la base de datos
        FlowManager.getDatabase(TipsDataBase.class).getWritableDatabase(); //abre la base de datos
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            about();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSubmit)
    public void handleSubmit() {
        hideKeyboard();

        String strInputTotal = inputBill.getText().toString().trim();

        if(!strInputTotal.isEmpty()) { //con ! negamos el resultado
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getTipPercentage();

            TipRecord record = new TipRecord();
            record.setBill(total);
            record.setTipPercentage(tipPercentage);
            record.setTimestamp(new Date());

            String strTip = String.format(getString(R.string.global_message_tip), TipUtils.getTip(record));
            fragmentListener.addToList(record);

            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);

        }
    }

    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease() {
        // Cuando des click a + debe llamar a handleTipChange y sumar 1
        handleTipChange(TIP_STEP_CHANGE);
    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease() {
        handleTipChange((-1)*TIP_STEP_CHANGE);
        // Cuando des click a - debe llamar a handleTipChange y restar 1
    }

    public int getTipPercentage() {
        //1 crear una variable tipPercentage en la que guardemos DEFAULT_TIP_CHANGE
        int tipPercentage = DEFAULT_TIP_CHANGE;
        //2 crear una variable String strInputTipPercentage que tome el valor del inputPercentage (no olvidar el trim)
        String strInputTipPercentage = inputPercentage.getText().toString().trim();
        // 3 Verificar que la cadena no venga vacia
        if(!strInputTipPercentage.isEmpty()) {
            tipPercentage = Integer.parseInt(strInputTipPercentage);
        }
        else{
            inputPercentage.setText(String.valueOf(DEFAULT_TIP_CHANGE));
            //inputPercentage.setText(String.value0f(DEFAULT_TIP_PERCENTAGE));
        }
        // 3a Si no viene vacia sobreescribir tipPercentage con el valor de StrTipPercentage (no olvien convertirlo a entero)
        // 3b inputPercentage.setText(String.value0f(DEFAULT_TIP_PERCENTAGE));
        // 4 Devolver el valor de tipPercentage
        return tipPercentage;
    }

    public void handleTipChange(int change) {
        // 1 Llamar a getTipPercentage (en una variable)
        int percentage = getTipPercentage();
        // 2 Aplicar el incremento/decremento que vienen en la variable change
        percentage += change;
        // 3 si tipPercentage mayor que 0 entonces colocar el valor en el valor del incremento en el input
        if(percentage>0){
            inputPercentage.setText(String.valueOf(percentage));
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe){
            Log.e(getLocalClassName(),Log.getStackTraceString(npe));
        }
    }

    @OnClick(R.id.btnClear)
    public void handleClickClear(){
        hideKeyboard();
        fragmentListener.clearList();
    }

    private void about() {
        TipCalcApp app = (TipCalcApp) getApplication();
        String strUrl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }


}
