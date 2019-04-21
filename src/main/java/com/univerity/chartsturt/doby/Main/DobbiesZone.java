package com.univerity.chartsturt.doby.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.univerity.chartsturt.doby.Frags.ListofDobies;
import com.univerity.chartsturt.doby.Frags.Operations;
import com.univerity.chartsturt.doby.Frags.UserAccount;
import com.univerity.chartsturt.doby.R;

public class DobbiesZone extends AppCompatActivity {

String email;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    ListofDobies listofDobies=new ListofDobies();

                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.Drawer,listofDobies,"Dobies");

                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_dashboard:


                    Operations operations=new Operations();

                    FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.Drawer,operations,"Dobies");

                    transaction.commit();

                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_user:

                    UserAccount userAccount=new UserAccount();

                    FragmentTransaction as=getSupportFragmentManager().beginTransaction();

                    as.replace(R.id.Drawer,userAccount,"Dobies");

                    as.commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobbies_zone);

        email=getIntent().getStringExtra("email");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
