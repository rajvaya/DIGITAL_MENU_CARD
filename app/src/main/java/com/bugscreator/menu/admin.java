package com.bugscreator.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin extends AppCompatActivity {
    EditText et;
    Button save,exit;
   public static final String shpf = "sharedPrefs";
   //public  String TNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        save = (Button) findViewById(R.id.stn);
        exit = (Button) findViewById(R.id.exit);
        et = (EditText)findViewById(R.id.etbn);
    }
    public void exit(View v) {
        Intent login = new Intent(admin.this,orderlist.class);
        startActivity(login);

        Toast.makeText(getApplicationContext(),"TESTING", Toast.LENGTH_SHORT).show();
    }
    public void set(View v) {
        SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TNAME",et.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"DATA SAVED", Toast.LENGTH_SHORT).show();

    }
}
