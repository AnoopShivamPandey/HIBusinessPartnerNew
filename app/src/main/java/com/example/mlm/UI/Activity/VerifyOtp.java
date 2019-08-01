package com.example.mlm.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mlm.CustomHelper.ConnectionDetector;
import com.example.mlm.CustomHelper.UserData;
import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.PrefrenceHandler;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class VerifyOtp extends AppCompatActivity {
    String mobile;
    EditText edt_enterOtp;
    String validateOtp = "validate-otp";
    String sendOtp = "send-otp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        mobile = getIntent().getStringExtra("mobile");
        edt_enterOtp = findViewById(R.id.edt_enterOtp);
        findViewById(R.id.btn_verifyOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.isConnected(VerifyOtp.this))
                    validateOtp(mobile, edt_enterOtp.getText().toString().trim());
                else
                    Toast.makeText(VerifyOtp.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_resendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.isConnected(VerifyOtp.this))
                    reSendOtp(mobile);
                else
                    Toast.makeText(VerifyOtp.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateOtp(final String MobileNo, final String Otp) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + validateOtp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {

                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        Gson gson = new Gson();
                        String jsonInString = jsonObject.getString("data");
                        // CommonUtill.printClassData(user);try {
                        UserData user = gson.fromJson(jsonInString, UserData.class);
                     //   CommonUtill.printClassData(user);
                        PrefrenceHandler.getPreferences(getApplicationContext()).putObjectValue(user);
                        PrefrenceHandler.getPreferences(VerifyOtp.this).setLogin();
                        Toast.makeText(VerifyOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerifyOtp.this, MainActivity.class));
                        //   UserData userData=UserData.getDatafromJson(jsonObject.getString("data"));
                    } else {
                        Toast.makeText(VerifyOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(VerifyOtp.this, error + "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile_no", MobileNo);
                params.put("otp_text", Otp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void reSendOtp(final String MobileNo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + sendOtp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //    Toast.makeText(ValidOtp.this, response + "", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(VerifyOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerifyOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(VerifyOtp.this, error + "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile_no", MobileNo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
