package com.example.mlm.UI.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.mlm.UI.Activity.VerifyOtp;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.Constants;
import com.example.mlm.Util.PrefrenceHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    private String SourcefilePath, fileName;

    public Profile() {
        // Required empty public constructor
    }

    View view = null;
    String logoutUrl = "logout";
    UserData userData;
    CircleImageView img_showProfile;
    TextView txt_userName, txt_userId;
    TextInputEditText et_firstname, et_lastname, et_fathername, et_MobileNo, et_gender, et_panno, et_email, et_accno, et_bankname, et_acctype, et_ifsccode;
    Spinner sp_gender;
    Button btn_save;
    Bitmap bitmap;

    String UrlProfileUpdate = "update-profile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
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
            headerTv.setText("Edit Profile");
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
                    builder.show();
                }
            });
        }


        img_showProfile = view.findViewById(R.id.img_showProfile);
        txt_userName = view.findViewById(R.id.txt_userName);
        txt_userId = view.findViewById(R.id.txt_userId);
        et_firstname = view.findViewById(R.id.et_firstname);
        et_lastname = view.findViewById(R.id.et_lastname);
        et_fathername = view.findViewById(R.id.et_fathername);
        et_MobileNo = view.findViewById(R.id.et_MobileNo);
        et_gender = view.findViewById(R.id.et_gender);
        et_panno = view.findViewById(R.id.et_panno);
        et_email = view.findViewById(R.id.et_email);
        et_accno = view.findViewById(R.id.et_accno);
        et_bankname = view.findViewById(R.id.et_bankname);
        et_acctype = view.findViewById(R.id.et_acctype);
        et_ifsccode = view.findViewById(R.id.et_ifsccode);
        sp_gender = view.findViewById(R.id.sp_gender);
        btn_save = view.findViewById(R.id.btn_save);
        et_MobileNo.setKeyListener(null);
        sp_gender.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Select Gender", "male", "female"}));

        txt_userName.setText(userData.getFirst_name() + " " + userData.getLast_name());
        txt_userId.setText(userData.getUsername());
        et_firstname.setText(userData.getFirst_name());
        et_lastname.setText(userData.getLast_name());
        et_fathername.setText(userData.getFather_name());
        et_MobileNo.setText(userData.getMobile());
        et_gender.setText(userData.getGender());
        et_panno.setText(userData.getPan());
        et_email.setText(userData.getEmail());
        et_accno.setText(userData.getAccount_number());
        et_bankname.setText(userData.getBank_name());
        et_acctype.setText(userData.getAccount_name());
        et_ifsccode.setText(userData.getBank_ifsc());

        if (userData.getGender() != null && userData.getGender().equalsIgnoreCase("Male")) {
            sp_gender.setSelection(1);
        } else if (userData.getGender() != null && userData.getGender().equalsIgnoreCase("Female")) {
            sp_gender.setSelection(2);
        } else {
            sp_gender.setSelection(0);
        }


        Picasso.with(getActivity())
                .load(userData.getPhoto())
                .placeholder(R.drawable.user_image)
                .error(R.drawable.user_image)
                .into(img_showProfile);


        img_showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp_gender.getSelectedItem().toString().equalsIgnoreCase("Select Gender")) {
                    Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                } else {

                    Uploaddata();
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        BitmapFactory.Options bitmapOptionsForCompression = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile("", bitmapOptions);
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        FileOutputStream fos = null;
                        ByteArrayOutputStream streamForCompression = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, streamForCompression);
                        byte[] bArrayForCompression = streamForCompression.toByteArray();
                        File CompressedDir = getActivity().getDir("Images", 0);
                        fileName = System.currentTimeMillis() + ".jpg";
                        File fileNew = new File(CompressedDir, fileName);
                        try {
                            fos = new FileOutputStream(fileNew);
                            fos.write(bArrayForCompression);
                            fos.flush();
                            fos.close();
                            SourcefilePath = fileNew.toString();
                            img_showProfile.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Log.e("Error : ", e.getMessage());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void Uploaddata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        try {
            Builders.Any.B request = Ion.with(this).
                    load(ApiUrl.BASE_URL + UrlProfileUpdate);
            request.setMultipartParameter("user_id", userData.getId())
                    .setMultipartParameter("first_name", et_firstname.getText().toString().trim())
                    .setMultipartParameter("last_name", et_lastname.getText().toString().trim())
                    .setMultipartParameter("father_name", et_fathername.getText().toString().trim())
                    .setMultipartParameter("gender", sp_gender.getSelectedItem().toString())
                    .setMultipartParameter("email", et_email.getText().toString().trim())
                    .setMultipartParameter("bank_name", et_bankname.getText().toString().trim())
                    .setMultipartParameter("bank_acc_name", et_acctype.getText().toString().trim())
                    .setMultipartParameter("bank_acc_number", et_accno.getText().toString().trim())
                    .setMultipartParameter("bank_ifsc", et_ifsccode.getText().toString().trim())
                    .setMultipartParameter("mobile_number", et_MobileNo.getText().toString().trim())
                    .setMultipartParameter("user_id", userData.getId());
            if (bitmap != null) {
                request.setMultipartFile("profile_picture",
                        new File(SourcefilePath.trim()));
            }
            request.asJsonObject().setCallback(
                    new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();

                            if (result.getAsJsonObject().get("status") != null && result.getAsJsonObject().get("status").toString().equalsIgnoreCase("true")) {
                                Gson gson = new Gson();
                                String jsonInString = String.valueOf(result.getAsJsonObject().get("data"));
                                // CommonUtill.printClassData(user);try {
                                UserData user = gson.fromJson(jsonInString, UserData.class);
                                //   CommonUtill.printClassData(user);
                                try {
                                    PrefrenceHandler.getPreferences(getActivity()).putObjectValue(user);
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
                                }
                                Toast.makeText(getActivity(), "Profile Update Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        } catch (Exception e) {
            // Do something about exceptions
            progressDialog.dismiss();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

}
