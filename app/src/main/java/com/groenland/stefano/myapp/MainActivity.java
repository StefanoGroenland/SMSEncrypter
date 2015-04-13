package com.groenland.stefano.myapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    EditText em;
    TextView tv;
    EditText rc;
    SeekBar sk;
    CharSequence receive;
    int value;
    CharSequence tekst;
    String Output;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        es = (EditText) findViewById(R.id.editSMS);
        em = (EditText) findViewById(R.id.editMirror);
        es.addTextChangedListener(this);
        tv = (TextView) findViewById(R.id.textEncryptedOutput);
        rc = (EditText) findViewById(R.id.editReceiver);
        sk = (SeekBar) findViewById(R.id.seekBar);
        receive = rc.getText();
        Bundle extras = getIntent().getExtras();
        String text = extras.getString("Text");
        em.setText(text);
        String keuze = extras.getString("Keuze");
        es.setText(keuze);
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

    private String mirrorSms(String str){
        String result = new String();
        for(int i = str.length() - 1; i >= 0; i--){
            result = result + str.charAt(i);
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
        em.setText(" ");
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
        em.setText(mirrorSms(Output));
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

    @Override
    protected void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String storedSMS = prefs.getString("keySMS", "Lege waardes");
        String storedReceiver = prefs.getString("keyReceiver", "Lege waardes");
        es.setText(storedSMS);
        rc.setText(storedReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefs =
                PreferenceManager.getDefaultSharedPreferences(this).edit();
        prefs.putString("keySMS",es.getText().toString());
        prefs.putString("keyReceiver",rc.getText().toString());
                prefs.commit();
    }
}