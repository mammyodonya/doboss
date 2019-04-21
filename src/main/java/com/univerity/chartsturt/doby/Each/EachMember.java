package com.univerity.chartsturt.doby.Each;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.univerity.chartsturt.doby.Model.Transactions;
import com.univerity.chartsturt.doby.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EachMember extends AppCompatActivity {
    ImageView mess, cal;
    String firstname,lastname,location, phone;
    AlertDialog al;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;
    private TextView NAME,LOC,PHONE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_member);

        firstname=getIntent().getStringExtra("firstname");

        lastname=getIntent().getStringExtra("lastname");

        location=getIntent().getStringExtra("location");

        phone=getIntent().getStringExtra("phone");

        NAME=(TextView)findViewById(R.id.name);

        NAME.setText(firstname+"\t"+lastname);

        LOC=(TextView)findViewById(R.id.location);

        LOC.setText(location);

        PHONE=(TextView)findViewById(R.id.phone);

        PHONE.setText(phone);

        mess= (ImageView)findViewById(R.id.sms);

        cal= (ImageView) findViewById(R.id.call);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Intent.ACTION_CALL);
                String phonecall=  "phone";
                i.setData(Uri.parse("tel:+ "+phone));
                if (ActivityCompat.checkSelfPermission(EachMember.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(EachMember.this, "PLEASE GRANT THE PERMISSION TO CALL", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
                else {
                    startActivity(i);
                }
            }

            private void requestPermission() {
                ActivityCompat.requestPermissions(EachMember.this,new String[]{Manifest.permission.CALL_PHONE},1);
            }
        });
           mess.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   sendSms();


               }
           });

            }

    private void sendSms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater lay =LayoutInflater.from(this);
        final View viewdata = lay.inflate(R.layout.message, null);
        TextView textView= (TextView) viewdata.findViewById(R.id.aa);
         textView.setText(phone);

        final AutoCompleteTextView textView1=(AutoCompleteTextView) viewdata.findViewById(R.id.bb);

        builder.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message= textView1.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(EachMember.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }else{
                    sendnow(message);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                al.dismiss();

            }
        });
        builder.setView(viewdata);
        al = builder.create();
        al.show();

        
    }

    private void sendnow(String message) {
        int permissionCheck=ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);

        if (permissionCheck==getPackageManager().PERMISSION_GRANTED){
            myMessage(message);
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    private void myMessage(String message) {

        if ((phone==null || phone.equals("")) || (message==null || message.equals(""))){
            Toast.makeText(this, "Fields cant be empty", Toast.LENGTH_SHORT).show();

        }else {
            if (TextUtils.isDigitsOnly(phone)){
                SmsManager smsManager= SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,message, null,null);
                Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "please use integers only", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch (requestCode){

            case MY_PERMISSIONS_REQUEST_SEND_SMS:{
                if (grantResults.length>=0 && grantResults[0]== getPackageManager().PERMISSION_GRANTED){
                    myMessage("");
                }else {
                    Toast.makeText(this, "you don't have required permissions", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void Assign(View view) {

        String a,b,c,d;
        a=phone;
        b=firstname+"   "+lastname;
        DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date();
        c=dateFormat.format(date);

        d="Not Paid";
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Creating account, please wait");

        progressDialog.setIndeterminate(false);

        progressDialog.show();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Transactions");
        String imelda = myRef.push().getKey();
        Transactions names = new Transactions(a, b, c,d );

        myRef.child(imelda).setValue(names);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(EachMember.this, "Boss Successfully Added!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(EachMember.this, "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}


