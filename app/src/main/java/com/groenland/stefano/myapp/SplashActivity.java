package com.groenland.stefano.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SplashActivity extends Activity {


    RadioGroup rg;
    RadioButton rb;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        SplashRunnable splashRunnable = new SplashRunnable();
        Handler handler = new Handler();
        handler.postDelayed(splashRunnable,10000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
    private class SplashRunnable implements Runnable{
        @Override
        public void run(){
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            text = (EditText) findViewById(R.id.text);
            i.putExtra("Keuze",rb.getText().toString());
            i.putExtra("Text" ,text.getText().toString());
            startActivity(i);
            finish();
        }
    }
}
