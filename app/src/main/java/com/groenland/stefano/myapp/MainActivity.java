package com.groenland.stefano.myapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements TextWatcher, SeekBar.OnSeekBarChangeListener{

    EditText es;
    TextView tv;
    EditText rc;
    SeekBar sk;
    CharSequence receive;
    int value;
    CharSequence tekst;
    String Output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        es = (EditText) findViewById(R.id.editSMS);
        es.addTextChangedListener(this);
        tv = (TextView) findViewById(R.id.textEncryptedOutput);
        rc = (EditText) findViewById(R.id.editReceiver);
        sk = (SeekBar) findViewById(R.id.seekBar);
        receive = rc.getText();


        sk.setOnSeekBarChangeListener(this);
    }

    private String encryptString(String string, int key){
        String result = new String();
        char c;
        for(int i = 0; i < string.length(); i++){
            c = string.charAt(i);
            c = (char) (c + key);
            result = result + c;

        }
        return result;
    }



    public void btnSend (View view){
        Output = encryptString(tekst.toString() , value);
        tv.setText(Output);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(receive.toString() ,null , Output ,null,null);
        //Toast.makeText(getApplicationContext() , "Dit is een tekst", Toast.LENGTH_LONG).show();
    }
    public void btnSec (View view){

        EditText es = (EditText) findViewById(R.id.editSMS);
        es.setText(" ");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        value = sk.getProgress();
        tekst = es.getText();
        Output = encryptString(tekst.toString() , value);
        tv.setText(Output);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        value = sk.getProgress();
        tekst = es.getText();
        Output = encryptString(tekst.toString() , value);
        tv.setText(Output);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}