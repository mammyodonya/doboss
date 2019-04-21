package com.univerity.chartsturt.doby.Login;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.univerity.chartsturt.doby.Dobies.Dobies_Dashboard;
import com.univerity.chartsturt.doby.Main.DobbiesZone;
import com.univerity.chartsturt.doby.R;
import com.univerity.chartsturt.doby.Start;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText pass;
    private TextView forget;
    private Button login;

    private Spinner spinner;

    String[] Combo = {"SELECT MODE", "DOBI", "BOSS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        spinner = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Combo);
        spinner.setAdapter(arrayAdapter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        forget = (TextView) findViewById(R.id.regester);
        forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Login.this, Start.class);
                startActivity(intent);
            }
        });

    }

    private void check() {
        String AA, BB;
        AA = username.getText().toString();
        BB = pass.getText().toString();

        if (AA.isEmpty()) {
            username.setError("Enter Email");
            username.requestFocus();
        } else if (BB.isEmpty()) {
            pass.setError("Enter password");
            pass.requestFocus();
        } else {

            String selector = spinner.getSelectedItem().toString();
            if (selector.equalsIgnoreCase("SELECT MODE")) {
                Toast.makeText(this, "Select operator", Toast.LENGTH_SHORT).show();
            } else if (selector.equalsIgnoreCase("DOBI")) {

                load(AA, BB);
            } else if (selector.equalsIgnoreCase("BOSS")) {

                loadBoss(AA, BB);
            }

        }

    }

    private void loadBoss(final String aa, final String bb) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Authenticating....");

        progressDialog.setIndeterminate(false);

        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Bosses");
        myRef.orderByChild("email").equalTo(aa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String password = (String) snapshot.child("password").getValue();
                        if (bb.equalsIgnoreCase(password)) {
//                            proceed to main menu

                            Intent intent=new Intent(Login.this, DobbiesZone.class);

                            Bundle bundle=new Bundle();

                            bundle.putString("email",aa);

                            intent.putExtras(bundle);

                            startActivity(intent);

                            finish();


//                            Toast.makeText(Sign_up.this, "Welcome", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "No such Record", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void load(final String aa, final String bb) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Authenticating....");

        progressDialog.setIndeterminate(false);

        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Dobbies");
        myRef.orderByChild("email").equalTo(aa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String password = (String) snapshot.child("password").getValue();
                        if (bb.equalsIgnoreCase(password)) {
//                            proceed to main menu

                            Intent intent=new Intent(Login.this, Dobies_Dashboard.class);

                            startActivity(intent);

                            finish();

//                            Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "No such Record", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}