package com.example.mlm.UI.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.UI.Activity.SendOtp;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.PrefrenceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {


    public More() {
        // Required empty public constructor
    }

    String logoutUrl = "logout";
    View view;
    UserData userData;
    LinearLayout ll_mynetwork,
            ll_withdrawals,
            ll_staticpages;

    RelativeLayout rl_mynetwork,
            rl_withdrawals,
            rl_staticpages;

    TextView tv_mynetwork,
            tv_myright,
            tv_myleft,
            tv_mynetworktree,
            tv_withdrawals,
            tv_request,
            tv_history,
            tv_aboutus,
            tv_photogallery,
            tv_videogallery,
            tv_staticpages,
            tv_terms,
            tv_privacy,
            tv_contactus,
            tv_help,
            tv_faq;

    ImageView iv_mynetwork,
            iv_withdrawals,
            iv_staticpages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);
        init();

        return view;
    }

    public void init() {
        if (getActivity() != null) {
            try {
                userData = (UserData) PrefrenceHandler.getPreferences(getActivity()).getObjectValue(new UserData());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            TextView headerTv = view.findViewById(R.id.txt_headerName);
            headerTv.setText("History");
            ImageView img_logout = view.findViewById(R.id.img_logout);
            img_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure want to exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLogOut();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                }
            });

            ll_mynetwork = view.findViewById(R.id.ll_mynetwork);
            ll_withdrawals = view.findViewById(R.id.ll_withdrawals);
            ll_staticpages = view.findViewById(R.id.ll_staticpages);

            tv_mynetwork = view.findViewById(R.id.tv_mynetwork);
            tv_myright = view.findViewById(R.id.tv_myright);
            tv_myleft = view.findViewById(R.id.tv_myleft);
            tv_mynetworktree = view.findViewById(R.id.tv_mynetworktree);
            tv_withdrawals = view.findViewById(R.id.tv_withdrawals);
            tv_request = view.findViewById(R.id.tv_request);
            tv_history = view.findViewById(R.id.tv_history);
            tv_aboutus = view.findViewById(R.id.tv_aboutus);
            tv_photogallery = view.findViewById(R.id.tv_photogallery);
            tv_videogallery = view.findViewById(R.id.tv_videogallery);
            tv_staticpages = view.findViewById(R.id.tv_staticpages);
            tv_terms = view.findViewById(R.id.tv_terms);
            tv_privacy = view.findViewById(R.id.tv_privacy);
            tv_contactus = view.findViewById(R.id.tv_contactus);
            tv_help = view.findViewById(R.id.tv_help);
            tv_faq = view.findViewById(R.id.tv_faq);

            iv_mynetwork = view.findViewById(R.id.iv_mynetwork);
            iv_withdrawals = view.findViewById(R.id.iv_withdrawals);
            iv_staticpages = view.findViewById(R.id.iv_staticpages);

            rl_mynetwork = view.findViewById(R.id.rl_mynetwork);
            rl_withdrawals = view.findViewById(R.id.rl_withdrawals);
            rl_staticpages = view.findViewById(R.id.rl_staticpages);

            rl_mynetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ll_mynetwork.getVisibility() == View.GONE) {
                        setTextViewDrawableColor(tv_mynetwork, R.color.yello);
                        tv_mynetwork.setTextColor(getResources().getColor(R.color.yello));
                        ll_mynetwork.setVisibility(View.VISIBLE);
                        iv_mynetwork.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
                    } else {
                        setTextViewDrawableColor(tv_mynetwork, R.color.black);
                        ll_mynetwork.setVisibility(View.GONE);
                        iv_mynetwork.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_more));
                        tv_mynetwork.setTextColor(getResources().getColor(R.color.black));
                    }
                }
            });

            rl_withdrawals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ll_withdrawals.getVisibility() == View.GONE) {
                        setTextViewDrawableColor(tv_withdrawals, R.color.yello);
                        ll_withdrawals.setVisibility(View.VISIBLE);
                        iv_withdrawals.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
                        tv_withdrawals.setTextColor(getResources().getColor(R.color.yello));
                    } else {
                        setTextViewDrawableColor(tv_withdrawals, R.color.black);
                        ll_withdrawals.setVisibility(View.GONE);
                        iv_withdrawals.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_more));
                        tv_withdrawals.setTextColor(getResources().getColor(R.color.black));
                    }
                }
            });

            rl_staticpages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ll_staticpages.getVisibility() == View.GONE) {
                        setTextViewDrawableColor(tv_staticpages, R.color.yello);
                        ll_staticpages.setVisibility(View.VISIBLE);
                        iv_staticpages.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
                        tv_staticpages.setTextColor(getResources().getColor(R.color.yello));
                    } else {
                        setTextViewDrawableColor(tv_staticpages, R.color.black);
                        ll_staticpages.setVisibility(View.GONE);
                        iv_staticpages.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_expand_more));
                        tv_staticpages.setTextColor(getResources().getColor(R.color.black));
                    }
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
                Toast.makeText(getActivity(), "Some thing went wrong", Toast.LENGTH_SHORT).show();

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

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
}
