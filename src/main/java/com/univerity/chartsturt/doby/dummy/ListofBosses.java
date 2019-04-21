package com.univerity.chartsturt.doby.dummy;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.univerity.chartsturt.doby.Each.EachMember;
import com.univerity.chartsturt.doby.Model.DobieDetails;
import com.univerity.chartsturt.doby.Frags.ListofDobies;
import com.univerity.chartsturt.doby.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListofBosses extends Fragment {
    private ListView listView;

    ProgressDialog progressDialog;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<DobieDetails> arrayList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;



    public ListofBosses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listof_bosses, container, false);

        listView = (ListView) view.findViewById(R.id.list);

        adapterClass = new AdapterClass(getContext(), arrayList);

        listView.setAdapter(adapterClass);

        addData();
        return view;
    }


    private void addData() {


        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Bosses");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    DobieDetails details = new DobieDetails();

                    String firstName = (String) messageSnapshot.child("firstName").getValue();

                    String lastname = (String) messageSnapshot.child("lastname").getValue();

                    String location = (String) messageSnapshot.child("location").getValue();

                    String phone_number = (String) messageSnapshot.child("phone_number").getValue();

                    details.setFirstName(firstName);

                    details.setLastname(lastname);

                    details.setLocation(location);

                    details.setPhone_number(phone_number);

                    arrayList.add(details);

                    adapterClass.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }
    private class AdapterClass extends ArrayAdapter {

        ArrayList<DobieDetails> status;

        public AdapterClass(Context context, ArrayList<DobieDetails> status) {

            super(context, R.layout.each, R.id.aa, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.each, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.aa);

            DobieDetails details = status.get(position);

            final String firstname = details.getFirstName();

            final String lastname = details.getLastname();

            final String location = details.getLocation();

            final String phone = details.getPhone_number();

            textView.setText("First Name:" + firstname
                    + "\nLast Name:" + lastname + "\nPhone Number:" + phone + "\nLocation:" + location);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    seeEach(firstname,lastname,location, phone);
                }
            });

            return view;
        }
    }

    private void seeEach(String firstname, String lastname, String location, String phone) {

        Intent ime =new Intent(getContext(),EachMember.class);

        Bundle bundle=new Bundle();

        bundle.putString("firstname",firstname);

        bundle.putString("lastname",lastname);

        bundle.putString("location",location);

        bundle.putString("phone",phone);

        ime.putExtras(bundle);

        getContext().startActivity(ime);
    }

}
