package com.example.mlm.UI.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mlm.CustomHelper.BaseBackPressedListener;
import com.example.mlm.CustomHelper.UserData;

import com.example.mlm.CustomHelper.ViewPagerAdapter;
import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.UI.Activity.SendOtp;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.PrefrenceHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUser extends Fragment  {


    public AddUser() {
        // Required empty public constructor
    }

    View view = null;
    String logoutUrl = "logout";
    UserData userData;
    public static ViewPager viewPager;
    public static ImageView img_personal,
            img_account,
            img_bank;
    public static TextView tv_bank,
            tv_personal,
            tv_account;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        init();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void init() {
        if (getActivity() != null) {
            try {
                userData = (UserData) PrefrenceHandler.getPreferences(getActivity()).getObjectValue(new UserData());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            TextView headerTv = view.findViewById(R.id.txt_headerName);
            headerTv.setText("Register");
            ImageView img_logout = view.findViewById(R.id.img_logout);
            img_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure want to exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Dashboard.setLogOut(getActivity(), logoutUrl, userData.getId());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                 /*   builder.setNeutralButton("wait", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });*/
                    builder.show();

                }
            });

            img_personal = view.findViewById(R.id.img_personal);
            tv_personal = view.findViewById(R.id.tv_personal);
            img_bank = view.findViewById(R.id.img_bank);
            tv_bank = view.findViewById(R.id.tv_bank);
            img_account = view.findViewById(R.id.img_account);
            tv_account = view.findViewById(R.id.tv_account);
            viewPager = view.findViewById(R.id.viewpager);
            viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
            AddUser.tv_personal.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            img_personal.setBackground(getResources().getDrawable(R.drawable.ic_user_g));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    if (i == 0) {
                        setcolor();
                    } else if (i == 1) {
                        setcolor1();
                    } else if (i == 2) {
                        setcolor2();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

        }
    }

    public void setLogOut() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + logoutUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        PrefrenceHandler.getPreferences(getActivity()).setLogout();
                        startActivity(new Intent(getActivity(), SendOtp.class));
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Some Thing went wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", userData.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    void setcolor() {
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.black));
        img_personal.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_user_g));
        img_bank.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bank_b));
        img_account.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_b));
    }

    void setcolor1() {
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.black));
        img_personal.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_user_b));
        img_bank.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bank_g));
        img_account.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_b));
    }

    void setcolor2() {
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.black));
        img_personal.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_user_b));
        img_bank.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bank_b));
        img_account.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_g));
    }

}
