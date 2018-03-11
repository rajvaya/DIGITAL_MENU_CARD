package com.bugscreator.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import static com.bugscreator.menu.admin.shpf;

public class payment extends AppCompatActivity {
    private static final String DB_URL = "jdbc:mysql://192.168.1.121:3306/mdb";
    private static final String USER = "anon";
    private static final String PASS = "root";
       public TextView totp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        totp = (TextView) findViewById(R.id.otp);

    }
    public void setotp(View v) {
        Connection conn;
        Random random = new Random();
        int value = random.nextInt(10000);
        if(value>999)
        {
            totp.setText(Integer.toString(value));
        }
        else{
            value=value+1111;
            totp.setText(Integer.toString(value));
        }

        try {
            SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
            String tname = sharedPreferences.getString("TNAME", "");

            StrictMode.ThreadPolicy  policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            String ins = "insert into billotp (tname,otp) Values(?,?)";

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(ins);
            //Statement stmt = conn.crea teStatement(ins);
            stmt.setString(1,tname);
            stmt.setInt(2,value);

            stmt.executeUpdate();
            Toast.makeText(getApplicationContext(),"ORDER COMPLETED", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {

            e.printStackTrace();
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String msg1 = writer.toString();
            Toast.makeText(getApplicationContext(),msg1, Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

    }
}
