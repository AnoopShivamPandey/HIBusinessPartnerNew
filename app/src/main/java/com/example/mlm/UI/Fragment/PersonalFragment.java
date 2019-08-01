package com.example.mlm.UI.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.mlm.Interface.MLMInterface;
import com.example.mlm.Constants.ValidationUtill;
import com.example.mlm.Entity.PersonalData;
import com.example.mlm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements MLMInterface {


    View view;
    Spinner sp_gender;
    TextInputEditText et_firstname,
            et_lastname,
            et_fathername,
            et_mobileno,
            et_panno,
            et_email,
            et_password,
            et_confirmpassword;
    Button btn_next;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        init();
        return view;
    }

    public void init() {
        if (getActivity() != null) {
            sp_gender = view.findViewById(R.id.sp_gender);
            et_firstname = view.findViewById(R.id.et_firstname);
            et_lastname = view.findViewById(R.id.et_lastname);
            et_fathername = view.findViewById(R.id.et_fathername);
            et_mobileno = view.findViewById(R.id.et_mobileno);
            et_panno = view.findViewById(R.id.et_panno);
            et_email = view.findViewById(R.id.et_email);
            et_password = view.findViewById(R.id.et_password);
            et_confirmpassword = view.findViewById(R.id.et_confirmpassword);
            btn_next = view.findViewById(R.id.btn_next);

            sp_gender.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Select Gender", "male", "female"}));
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser.viewPager.setCurrentItem(1);

            }
        });


        AddUser.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    if (et_firstname.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (et_lastname.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (et_fathername.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please Enter Father/Husband Name", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (et_mobileno.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please Enter Phone no", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (sp_gender.getSelectedItem().toString().equalsIgnoreCase("Select Gender")) {
                        Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (!ValidationUtill.isValidPhoneNumber(et_mobileno.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Valid Phone no", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (et_panno.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please Enter Pan no", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (!ValidationUtill.isValidEmail(et_email.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Correct Email", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (et_password.getText().toString().length() < 6) {
                        Toast.makeText(getActivity(), "Password must at Least 6 Character ", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_confirmpassword.getText().toString().trim()) || !et_confirmpassword.getText().toString().trim().equals(et_password.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Same Password", Toast.LENGTH_SHORT).show();
                        setViewpager();

                    } else {
                        PersonalData personalData = new PersonalData();
                        personalDataList.add(personalData);
                        personalDataList.get(0).setFirstName(et_firstname.getText().toString().trim());
                        personalDataList.get(0).setLastName(et_lastname.getText().toString().trim());
                        personalDataList.get(0).setFatherName(et_fathername.getText().toString().trim());
                        personalDataList.get(0).setGender(sp_gender.getSelectedItem().toString().trim());
                        personalDataList.get(0).setMobileNo(et_mobileno.getText().toString().trim());
                        personalDataList.get(0).setEmail(et_email.getText().toString().trim());
                        personalDataList.get(0).setPanNo(et_panno.getText().toString().trim());
                        personalDataList.get(0).setPassword(et_password.getText().toString().trim());
                        personalDataList.get(0).setConfirmPassword(et_confirmpassword.getText().toString().trim());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    void setViewpager() {
        AddUser.viewPager.setCurrentItem(0);
    }

    void setcolor() {
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.black));
    }

}
