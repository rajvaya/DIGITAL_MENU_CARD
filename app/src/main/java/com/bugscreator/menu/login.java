package com.bugscreator.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.bugscreator.menu.admin.shpf;

public class login extends AppCompatActivity {
    Button logbut;
    EditText passx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logbut=(Button) findViewById(R.id.lb);
        passx=(EditText) findViewById(R.id.pass);
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
                 String value = sharedPreferences.getString("TNAME", "");


                if (passx.getText().toString().equals("anon"))
                {
                    //SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
                    //String shp = sharedPreferences.getString(shpf,"");
                    Intent login = new Intent(login.this,admin.class);
                    startActivity(login);
                    Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Do something in response to button click
    }



    }


