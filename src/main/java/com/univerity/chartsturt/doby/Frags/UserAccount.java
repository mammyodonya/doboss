package com.univerity.chartsturt.doby.Frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.univerity.chartsturt.doby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAccount extends Fragment {


    public UserAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account2, container, false);
    }

}
