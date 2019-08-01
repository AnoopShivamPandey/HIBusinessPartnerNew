package com.example.mlm.UI.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mlm.CustomHelper.UserData;
import com.example.mlm.R;
import com.example.mlm.UI.Activity.SendOtp;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.CommonUtill;
import com.example.mlm.Util.PrefrenceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {


    public Dashboard() {
        // Required empty public constructor
    }

    View view = null;
    TextView txt_referal_link, txt_remaining, txt_cashback, txt_direct, txt_unilevel, txt_withdraw, txt_pairing, txt_total;
    UserData userData;
    String url = "dashboard";
    String pkg = "com.example.mlm";
    String msg = "Hey Download This awasome app";
    String logoutUrl = "logout";
    ImageView iv_copytext;
    String contactusurl;

    String packagename;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        findViewByIds(view);
        initView();
        getData();
        return view;
    }

    private void initView() {
        if (getActivity() != null) {
            contactUs();
            packagename = getActivity().getPackageName();
            try {
                userData = (UserData) PrefrenceHandler.getPreferences(getActivity()).getObjectValue(new UserData());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (userData != null) {
                txt_referal_link = view.findViewById(R.id.txt_referal_link);
                txt_referal_link.setText(userData.getReferral_link());
            }

            view.findViewById(R.id.ll_shareapp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtill.shareApp(getActivity(), msg, packagename);
                }
            });
            view.findViewById(R.id.ll_contactus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtill.openBrowser(getActivity(), contactusurl);
                }
            });
            ImageView img_logout = view.findViewById(R.id.img_logout);
            img_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure want to exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLogOut(getActivity(), logoutUrl, userData.getId());
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

            iv_copytext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClipboard(getActivity(), userData.getReferral_link());
                }
            });
        }

    }

    public void findViewByIds(View view) {
        txt_remaining = view.findViewById(R.id.txt_remaining);
        txt_cashback = view.findViewById(R.id.txt_cashback);
        txt_direct = view.findViewById(R.id.txt_direct);
        txt_unilevel = view.findViewById(R.id.txt_unilevel);
        txt_withdraw = view.findViewById(R.id.txt_withdraw);
        txt_pairing = view.findViewById(R.id.txt_pairing);
        txt_total = view.findViewById(R.id.txt_total);
        iv_copytext = view.findViewById(R.id.iv_copytext);
    }

    public void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        txt_remaining.setText(jsonObject1.getString("remainingBalance"));
                        txt_cashback.setText(jsonObject1.getString("cashBack"));
                        txt_direct.setText(jsonObject1.getString("directReferralBonus"));
                        txt_unilevel.setText(jsonObject1.getString("unilevelIncome"));
                        txt_withdraw.setText(jsonObject1.getString("withdrawn"));
                        txt_pairing.setText(jsonObject1.getString("pairingIncome"));
                        txt_total.setText(jsonObject1.getString("totalIncome"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public static void setLogOut(final Context context, String url, final String userid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        PrefrenceHandler.getPreferences(context).setLogout();
                        context.startActivity(new Intent(context, SendOtp.class));
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();
        }
    }

    public void contactUs() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + "general-settings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        contactusurl = jsonObject1.getString("contact_us");
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}