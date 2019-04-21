package com.univerity.chartsturt.doby.Dobies;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.univerity.chartsturt.doby.Frags.MyAccount;
import com.univerity.chartsturt.doby.R;
import com.univerity.chartsturt.doby.dummy.ListofBosses;

import java.util.ArrayList;

public class Dobies_Dashboard extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobies__dashboard);

        tabLayout = (TabLayout)findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.pager);

        setup(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void setup(ViewPager viewPager) {

        Adapt frag =new Adapt(getSupportFragmentManager());
        frag.addFrag(new ListofBosses(),"Bosses");
        frag.addFrag(new MyAccount(),"UserAccount");

        viewPager.setAdapter(frag);
    }

    private class Adapt extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String>fragS = new ArrayList<>();

        public Adapt(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragS.get(position);
        }

        public void addFrag(Fragment names, String login) {
            fragments.add(names);
            fragS.add(login);

        }
    }
}
