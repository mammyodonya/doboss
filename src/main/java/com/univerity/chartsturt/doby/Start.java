package com.univerity.chartsturt.doby;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.univerity.chartsturt.doby.Model.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start extends AppCompatActivity {

    private EditText finame;
    private EditText laname;
    private Button login;
    private EditText email;
    private EditText lcation;
    private EditText phone;

    private EditText pass;
    private EditText cpass;
    TextView fog;
    private Spinner spinner;
    String[] Combo = {"SELECT MODE", "DOBI", "BOSS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        finame = (EditText) findViewById(R.id.fname);
        laname = (EditText) findViewById(R.id.lname);
        lcation = (EditText) findViewById(R.id.location);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.cpassword);
        login = (Button) findViewById(R.id.login);
        spinner = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Combo);
        spinner.setAdapter(arrayAdapter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkThem();
            }
        });

    }

    private void checkThem() {
        String AA, BB, CC, DD, EE, FF, GG;
        AA = finame.getText().toString();
        BB = laname.getText().toString();
        CC = lcation.getText().toString();
        DD = phone.getText().toString();
        EE = email.getText().toString();
        FF = pass.getText().toString();
        GG = cpass.getText().toString();

        if (AA.isEmpty()) {
            finame.setError("Enter first name");
            finame.requestFocus();
        } else if (BB.isEmpty()) {
            laname.setError("Enter Last name");
            laname.requestFocus();
        } else if (CC.isEmpty()) {
            lcation.setError("specify location");
            lcation.requestFocus();
        } else if (DD.isEmpty()) {
            phone.setError("Enter phone Number");
            phone.requestFocus();
        } else if (EE.isEmpty()) {
            email.setError("Enter Email address");
            email.requestFocus();
        } else if (FF.isEmpty()) {
            pass.setError("Enter Password");
            pass.requestFocus();
        } else if (GG.isEmpty()) {
            cpass.setError("Confirm password");
            cpass.requestFocus();
        } else {
            if (!validEmail(EE)) {

                email.setError("Invalid Email Address");

                email.requestFocus();
            } else {

                if (FF.equalsIgnoreCase(GG)) {

                    String selector = spinner.getSelectedItem().toString();
                    if (selector.equalsIgnoreCase("SELECT MODE")) {
                        Toast.makeText(this, "Select operator", Toast.LENGTH_SHORT).show();
                    } else if (selector.equalsIgnoreCase("DOBI")) {

                        loginNow(AA, BB, CC, DD, EE, FF);
                    } else if (selector.equalsIgnoreCase("BOSS")) {

                        loginNowBoss(AA, BB, CC, DD, EE, FF);
                    }
                } else {

                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void loginNowBoss(String aa, String bb, String cc, String dd, String ee, String ff) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Creating account, please wait");

        progressDialog.setIndeterminate(false);

        progressDialog.show();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Bosses");
        String imelda = myRef.push().getKey();
        Register names = new Register(aa, bb, cc, dd, ee, ff);
        myRef.child(imelda).setValue(names);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(Start.this, "Boss Successfully Added!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(Start.this, "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validEmail(String email) {

        Pattern pattern;

        Matcher matcher;

        final String EMAIL_PATTERN = "^[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private void loginNow(String AA, String BB, String CC, String DD, String EE, String FF) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Creating account, please wait");

        progressDialog.setIndeterminate(false);

        progressDialog.show();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Dobbies");
        String imelda = myRef.push().getKey();
        Register names = new Register(AA, BB, CC, DD, EE, FF);
        myRef.child(imelda).setValue(names);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(Start.this, "Dobby Successfully Added!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(Start.this, "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
