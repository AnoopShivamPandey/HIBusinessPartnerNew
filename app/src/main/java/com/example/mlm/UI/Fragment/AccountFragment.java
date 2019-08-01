package com.example.mlm.UI.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.PrefrenceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.mlm.Interface.MLMInterface.personalDataList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    View view;

    public AccountFragment() {
        // Required empty public constructor
    }

    Spinner sp_plan, sp_node;
    Button btn_save;
    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return view;
    }

    public void init() {
        if (getActivity() != null) {
            sp_node = view.findViewById(R.id.sp_node);
            sp_plan = view.findViewById(R.id.sp_plan);
            btn_save = view.findViewById(R.id.btn_save);
            sp_plan.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Select Your Plan", "12000", "65000"}));
            sp_node.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Select Node Placement", "left", "right"}));

            try {
                userData = (UserData) PrefrenceHandler.getPreferences(getActivity()).getObjectValue(new UserData());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sp_plan.getSelectedItem().toString().trim().equalsIgnoreCase("Select Your Plan")) {
                        Toast.makeText(getActivity(), "Please Select Your Plan", Toast.LENGTH_SHORT).show();
                    } else if (sp_node.getSelectedItem().toString().trim().equalsIgnoreCase("Select Node Placement")) {
                        Toast.makeText(getActivity(), "Please Select Node Placement", Toast.LENGTH_SHORT).show();
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + "sign-up", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("status")) {
                                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        AddUser.viewPager.setCurrentItem(0);
                                        personalDataList.clear();
                                    } else {
                                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                                Log.e("errmsg", error.getMessage());
                            }
                        }) {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("first_name", personalDataList.get(0).getFirstName());
                                params.put("last_name", personalDataList.get(0).getLastName());
                                params.put("father_name", personalDataList.get(0).getFatherName());
                                params.put("mobile_no", personalDataList.get(0).getMobileNo());
                                params.put("gender", personalDataList.get(0).getGender());
                                params.put("pan_no", personalDataList.get(0).getPanNo());
                                params.put("email", personalDataList.get(0).getEmail());
                                params.put("password", personalDataList.get(0).getPassword());
                                params.put("confirm_password", personalDataList.get(0).getConfirmPassword());
                                params.put("plan", sp_plan.getSelectedItem().toString().trim());
                                params.put("sponsor_id", userData.getUsername());
                                params.put("node", sp_node.getSelectedItem().toString().trim());
                                params.put("bank_name", personalDataList.get(0).getBankName());
                                params.put("bank_acc_name", personalDataList.get(0).getBranchName());
                                params.put("bank_acc_number", personalDataList.get(0).getAccountNo());
                                params.put("bank_ifsc", personalDataList.get(0).getIfseCode());
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    }

                }
            });


        }
    }

    void setcolor() {
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.black));
    }

}
