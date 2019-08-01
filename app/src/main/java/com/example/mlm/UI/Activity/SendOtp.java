package com.example.mlm.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.mlm.R;
import com.example.mlm.Util.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendOtp extends AppCompatActivity {
    String url = "send-otp";
    EditText edt_sendotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        edt_sendotp = findViewById(R.id.edt_sendotp);
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                    sendOtp(edt_sendotp.getText().toString().trim());
                else
                    Toast.makeText(SendOtp.this, "Please Check InterNet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendOtp(final String MobileNo) {
        final ProgressDialog progressDialog = new ProgressDialog(SendOtp.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(SendOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        Intent intent = new Intent(SendOtp.this, VerifyOtp.class);
                        intent.putExtra("mobile", jsonObject1.getString("mobile"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(SendOtp.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SendOtp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile_no", MobileNo);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SendOtp.this);
        requestQueue.add(stringRequest);

    }

    private boolean isValid() {
        if (ConnectionDetector.isConnected(SendOtp.this)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        finishAffinity();
    }
}
