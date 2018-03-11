package com.bugscreator.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton ib1,ib2,ib3,ib4,ib5,ib6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ib1 = (ImageButton)findViewById(R.id.ib1);
        ib2 = (ImageButton)findViewById(R.id.ib2);
        ib3 = (ImageButton)findViewById(R.id.ib3);
        ib4 = (ImageButton)findViewById(R.id.ib4);
        ib5 = (ImageButton)findViewById(R.id.ib5);
        ib6 = (ImageButton)findViewById(R.id.ib6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            Intent intent = new Intent(MainActivity.this,login.class);
            startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
    public void open(View v) {

    switch (v.getId())
    {
        case R.id.ib1:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","starter");
            startActivity(i);
            break;
        }
        case R.id.ib2:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","indian");
            startActivity(i);
            break;
        }
        case R.id.ib3:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","chinese");
            startActivity(i);
            break;
        }
        case R.id.ib4:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","italian");
            startActivity(i);
            break;
        }
        case R.id.ib5:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","juice");
            startActivity(i);
            break;
        }
        case R.id.ib6:
        {
            Intent i = new Intent(this,foodlist.class);
            i.putExtra("table","dessert");
            startActivity(i);
            break;
        }
    }

    }
}
