package com.univerity.chartsturt.doby.Frags;


import android.app.ProgressDialog;
import android.content.Context;
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
import com.univerity.chartsturt.doby.Model.DobieDetails;
import com.univerity.chartsturt.doby.Model.Transactions;
import com.univerity.chartsturt.doby.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Operations extends Fragment {
    private ListView listView;

    ProgressDialog progressDialog;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<Transactions> arrayList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;


    public Operations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_operations, container, false);

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

        reference = database.getReference("Transactions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    Transactions transactions = new Transactions();

//                    String phone,name,date,status;
                    String name = (String) messageSnapshot.child("name").getValue();

                    String phone = (String) messageSnapshot.child("phone").getValue();

                    String date = (String) messageSnapshot.child("date").getValue();

                    String status =(String) messageSnapshot.child("status").getValue();

                    transactions.setName(name);

                    transactions.setPhone(phone);

                    transactions.setDate(date);

                    transactions.setPhone(status);

                    arrayList.add(transactions);

                    adapterClass.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class AdapterClass extends ArrayAdapter {

        ArrayList<Transactions> status;

        public AdapterClass(Context context, ArrayList<Transactions> status) {

            super(context, R.layout.each, R.id.aa, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.each, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.aa);

            Transactions details = status.get(position);

            String name=details.getName();
            String phone = details.getPhone();
            String date = details.getDate();
            String status= details.getStatus();
            textView.setText("Name:" + name + "\nPhone Number:" + phone + "\ndate:" + date + "\nstatus:"+status);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            return view;
        }
    }

}


