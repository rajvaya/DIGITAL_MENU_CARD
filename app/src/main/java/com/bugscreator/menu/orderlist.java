package com.bugscreator.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.bugscreator.menu.admin.shpf;

/**
 * Created by admin on 01-03-2018.
 */

public class orderlist extends AppCompatActivity {
    private ArrayList<orderlistitem> itemList;  //List items Array
    private MyAdapter myAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    Connection conn;
    private static final String DB_URL = "jdbc:mysql://192.168.1.121:3306/mdb";
    private static final String USER = "anon";
    private static final String PASS = "root";
    TextView tqty,tpr;
    int sum = 0,c;
    int sqty=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders)    ;
        recyclerView = (RecyclerView) findViewById(R.id.orv); //REcyclerview Declaration
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        tpr = (TextView) findViewById(R.id.tpr);
        tqty = (TextView) findViewById(R.id.tqty);
        itemList = new ArrayList<orderlistitem>(); // Arraylist Initialization
        // Calling Async Task
        SyncOrder order = new SyncOrder();
        order.execute("");


    }


    public void pay(View v) {


        Intent payment = new Intent(orderlist.this,payment.class);
        startActivity(payment);


    }

    private class SyncOrder extends AsyncTask<String, String, String> {

        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(orderlist.this, "Message For You",
                    "Orders is Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS); //Connection Object
                System.out.print("CONNECTED");
                if (conn == null) {
                    success = false;
                    System.out.print("NOT CONNECTED");
                } else {
                    // Change below query according to your own database.
                    SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
                    String value = sharedPreferences.getString("TNAME", "");
                    String query = "SELECT * FROM "+value;
                    Statement stmt = conn.createStatement();


                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemList.add(new orderlistitem(rs.getString("name"), rs.getInt("price"), rs.getInt("qty"), rs.getInt("tprice")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }


                        String sql2 = "Select Sum(tprice) From "+value;
                        Statement stmt2 = conn.prepareStatement(sql2);
                        ResultSet rs2 =stmt2.executeQuery(sql2);
                        while (rs2.next()) {
                            c = rs2.getInt(1);
                            sum=sum + c;

                        }
                        c=0;
                        String sql3 = "Select Sum(qty) From "+value;
                        Statement stmt3 = conn.prepareStatement(sql3);
                        ResultSet rs3 =stmt3.executeQuery(sql3);
                        while (rs3.next()) {
                            c = rs3.getInt(1);
                            sqty=sqty + c;

                        }
                        msg = " Your Orders Is Here";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my gridview
        {


            progress.dismiss();
            Toast.makeText(orderlist.this, msg + "", Toast.LENGTH_LONG).show();



            if (success == false)
            {
            }
            else {
                try
                {
                    myAdapter = new MyAdapter(itemList, orderlist.this);
                    recyclerView.setAdapter(myAdapter);
                    tpr.setText(Integer.toString(sum)+" RS.");
                    tqty.setText((Integer.toString(sqty)));
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
        private List<orderlistitem> values;
        public Context context;
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            public TextView textName1;
            public TextView textName2;
            public TextView textView3;
            public TextView textView4;

            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                textName1 = (TextView) v.findViewById(R.id.oname);
                textName2 = (TextView) v.findViewById(R.id.oprice);
                textView3 = (TextView) v.findViewById(R.id.oqty);
                textView4 = (TextView) v.findViewById(R.id.otprice);

            }
        }

        // Constructor
        public MyAdapter(ArrayList<orderlistitem> myDataset, Context context)
        {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.orderslayout, parent, false);
            MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
            int p;
            final orderlistitem orderlistitem = values.get(position);
            p=orderlistitem.getPrice();

            holder.textName1.setText(orderlistitem.getName());
            holder.textName2.setText(p+" Rs.");
            holder.textView3.setText(String.valueOf(orderlistitem.getQty()));
            holder.textView4.setText(String.valueOf(orderlistitem.getTprice()));


        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }


    }
}



