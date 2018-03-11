package com.bugscreator.menu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.bugscreator.menu.admin.shpf;

public class foodlist extends AppCompatActivity {
    private ArrayList<foodlistitem> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    Connection conn;
    public ElegantNumberButton num;
    private static final String DB_URL = "jdbc:mysql://192.168.1.121:3306/mdb";
    private static final String USER = "anon";
    private static final String PASS = "root";
    String newString;
    String abc;
    int a,b,c,d;
    String aa,bb,cc,dd,dfn,dfp,dfq;
    Dialog MyDialog;
    Button cancle,confirm;
    TextView CTTV1,CTTV2,CTTV3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        recyclerView = (RecyclerView) findViewById(R.id.frv); //REcyclerview Declaration
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        itemArrayList = new ArrayList<foodlistitem>(); // Arraylist Initialization
        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                newString = null;
            } else {
                newString = extras.getString("table");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("table");
        }
    }
    public void MyCustomAlertDialog(){
        MyDialog = new Dialog(foodlist.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.custum_dailog);
        MyDialog.setTitle("YOUR ORDER");
        confirm = (Button) MyDialog.findViewById(R.id.ok);
        cancle = (Button) MyDialog.findViewById(R.id.cancle);
        CTTV1 = (TextView)MyDialog.findViewById(R.id.CTV1);
        CTTV2 = (TextView)MyDialog.findViewById(R.id.CTV2);
        CTTV3 = (TextView)MyDialog.findViewById(R.id.CTV3);
        dfp= dfp.substring(0, dfp.length() - 4);
        final int dftp = Integer.valueOf(dfp)*Integer.valueOf(dfq);

        CTTV1.setText("Item : "+dfn);
        CTTV3.setText("Total : "+dftp);
        CTTV2.setText("Quantity : "+dfq);
        confirm.setEnabled(true);
        cancle.setEnabled(true);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences sharedPreferences=getSharedPreferences(shpf,0);
                    String value = sharedPreferences.getString("TNAME", "");

                    StrictMode.ThreadPolicy  policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Class.forName("com.mysql.jdbc.Driver");
                    String ins = "insert into "+value+" (name,price,qty,tprice) Values(?,?,?,?)";
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);
                    PreparedStatement stmt = conn.prepareStatement(ins);
                    //Statement stmt = conn.createStatement(ins);
                    stmt.setString(1,dfn);
                    stmt.setString(2,dfp);
                    stmt.setString(3,dfq);
                    stmt.setString(4,String.valueOf(dftp));
                    stmt.executeUpdate();
                    Toast.makeText(getApplicationContext(),"ORDER PLACED", Toast.LENGTH_LONG).show();
                    MyDialog.cancel();
                } catch (SQLException e) {

                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    String msg1 = writer.toString();
                    Toast.makeText(getApplicationContext(),msg1, Toast.LENGTH_LONG).show();
                    MyDialog.cancel();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    MyDialog.cancel();
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });
        MyDialog.show();

    }

 private class SyncData extends AsyncTask<String, String, String>
 {

     String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
     ProgressDialog progress;

     @Override
     protected void onPreExecute() //Starts the progress dailog
     {
         progress = ProgressDialog.show(foodlist.this, "Message For You",
                 "MenuItem is Loading! Please Wait...", true);
     }
     @Override
     protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
     {
         try
         {
             StrictMode.ThreadPolicy  policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
             StrictMode.setThreadPolicy(policy);
             Class.forName("com.mysql.jdbc.Driver");
             conn = DriverManager.getConnection(DB_URL,USER,PASS); //Connection Object
             System.out.print("CONNECTED");
             if (conn == null)
             {
                 success = false;
                 System.out.print("NOT CONNECTED");
             }
             else {
                 // Change below query according to your own database.

                 String query = "SELECT * FROM "+newString;

                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query);
                 if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                 {
                     while (rs.next())
                     {
                         try {
                             itemArrayList.add(new foodlistitem(rs.getBytes("img"),rs.getString("name"),rs.getInt("price"),rs.getString("des")));
                         } catch (Exception ex) {
                             ex.printStackTrace();
                         }
                     }
                     msg = "Menu Is Here";
                     success = true;
                 } else {
                     msg = "No Data found!";
                     success = false;
                 }
             }
         } catch (Exception e)
         {
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
        Toast.makeText(foodlist.this, msg + "", Toast.LENGTH_LONG).show();
        if (success == false)
        {
        }
        else {
            try
            {
                myAppAdapter = new MyAppAdapter(itemArrayList , foodlist.this);
                recyclerView.setAdapter(myAppAdapter);
            } catch (Exception ex)
            {

            }

        }
    }
}

    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<foodlistitem> values;
        public Context context;
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            public TextView textName1;
            public TextView textName2;
            public TextView textView3;
            public ImageView imageView;
            public Button order;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                textName1 = (TextView) v.findViewById(R.id.tv1);
                textName2 = (TextView) v.findViewById(R.id.tv2);
                textView3 = (TextView) v.findViewById(R.id.tv3);
                order=(Button)v.findViewById(R.id.ob);
              num = (ElegantNumberButton)findViewById(R.id.nb);
                imageView = (ImageView) v.findViewById(R.id.img1);
            }
        }

        // Constructor
        public MyAppAdapter(List<foodlistitem> myDataset,Context context)
        {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.foodlayout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            int p;
            final foodlistitem foodlistitem = values.get(position);
           p=foodlistitem.getPrice();
            holder.textName1.setText(foodlistitem.getName());
            holder.textName2.setText(p+" Rs.");
            holder.textView3.setText(foodlistitem.getDes());
            byte[] imageBlob = foodlistitem.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            holder.imageView.setImageBitmap(bmp);
            holder.order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String fn,fp,fq;

                    dfn= fn = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tv1)).getText().toString();
                    dfp= fp = ((TextView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tv2)).getText().toString();
              dfq=fq=((ElegantNumberButton)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.nb)).getNumber();
                    Toast.makeText(context,"clicked on "+position, Toast.LENGTH_SHORT).show();
                MyCustomAlertDialog();


                }
            });
        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }


    }
}


