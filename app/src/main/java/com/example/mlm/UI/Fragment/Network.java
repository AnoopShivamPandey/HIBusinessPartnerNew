package com.example.mlm.UI.Fragment;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.example.mlm.CustomHelper.BaseBackPressedListener;
import com.example.mlm.CustomHelper.UserData;
import com.example.mlm.Interface.IOnBackPressed;
import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.Util.CommonUtill;
import com.example.mlm.Util.PrefrenceHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class Network extends Fragment {


    public Network() {
        // Required empty public constructor
    }

    View view = null;
    private TabLayout tabLayout;
    private LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_network, container, false);
        initView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        if (getActivity() != null) {
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
            container = (LinearLayout) view.findViewById(R.id.fragment_container);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            //create tabs title
            tabLayout.addTab(tabLayout.newTab().setText("Left Nodes"));
            tabLayout.addTab(tabLayout.newTab().setText("Right Nodes"));
            //  tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ccc"));
            //  tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
            tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
            //  tabLayout.addTab(tabLayout.newTab().setText("Games"));
            //replace default fragment
            replaceFragment(new LeftNode());


            //handling tab click event
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        replaceFragment(new LeftNode());
                    } else if (tab.getPosition() == 1) {
                        replaceFragment(new RightNode());
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


}
