package com.example.mlm;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.mlm.Interface.OnBackPressedListener;
import com.example.mlm.UI.Fragment.AddUser;
import com.example.mlm.UI.Fragment.Dashboard;
import com.example.mlm.UI.Fragment.HistoryFragment;
import com.example.mlm.UI.Fragment.Network;
import com.example.mlm.UI.Fragment.Profile;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;
    String UrlImp = "https://www.pipiscrew.com/2014/01/android-listview-with-image-checkbox/";
    protected OnBackPressedListener onBackPressedListener;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loading the default fragment
        loadFragment(new Dashboard());
        navView = findViewById(R.id.nav_view);
        //  mTextMessage = mTextMessage.findViewById();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);


        //  BottomNavigationViewHelper.disableShiftMode(navView);
/*
        findViewById(R.id.img_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefrenceHandler.getPreferences(MainActivity.this).setLogout();
                startActivity(new Intent(MainActivity.this, SendOtp.class));
            }
        });
*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    fragment = new Dashboard();
                    break;
                case R.id.navigation_network:
                    fragment = new Network();
                    break;

                case R.id.navigation_profile:
                    fragment = new Profile();
                    break;

                case R.id.navigation_add_user:
                    fragment = new AddUser();
                    break;

                case R.id.navigation_more:
                    fragment = new HistoryFragment();
                    break;

            }
            return loadFragment(fragment);

        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (navView.getSelectedItemId() == R.id.navigation_dashboard) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
               // super.onBackPressed();
               // return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        } else {
         /*   if (onBackPressedListener != null)
                onBackPressedListener.doBack();
            else
                super.onBackPressed();*/
            /*Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
                super.onBackPressed();
            }*/
          navView.setSelectedItemId(R.id.navigation_dashboard);
       }

    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
