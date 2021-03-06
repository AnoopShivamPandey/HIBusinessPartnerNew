package com.example.mlm.CustomHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mlm.UI.Fragment.AccountFragment;
import com.example.mlm.UI.Fragment.BankFragment;
import com.example.mlm.UI.Fragment.PersonalFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new PersonalFragment(); //ChildFragment1 at position 0
            case 1:
                return new BankFragment(); //ChildFragment2 at position 1
            case 2:
                return new AccountFragment(); //ChildFragment3 at position 2
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }
}
