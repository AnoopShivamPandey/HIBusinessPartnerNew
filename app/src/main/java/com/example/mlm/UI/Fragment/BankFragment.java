package com.example.mlm.UI.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mlm.Constants.ValidationUtill;
import com.example.mlm.CustomHelper.BaseBackPressedListener;
import com.example.mlm.Interface.MLMInterface;
import com.example.mlm.Entity.PersonalData;
import com.example.mlm.MainActivity;
import com.example.mlm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankFragment extends Fragment implements MLMInterface {


    public BankFragment() {
        // Required empty public constructor
    }

    View view;

    TextInputEditText
            et_accno,
            et_bankname,
            et_bankbranchname,
            et_ifsccode;
    Button btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bank, container, false);
        init();
        return view;
    }

    public void init() {
        if (getActivity() != null) {
            et_accno = view.findViewById(R.id.et_accno);
            et_bankname = view.findViewById(R.id.et_bankname);
            et_bankbranchname = view.findViewById(R.id.et_bankbranchname);
            et_ifsccode = view.findViewById(R.id.et_ifsccode);
            btn_next = view.findViewById(R.id.btn_next);
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser.viewPager.setCurrentItem(2);
            }
        });


        AddUser.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 2) {
                    if (TextUtils.isEmpty(et_accno.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Account No", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (!ValidationUtill.isValidAccountNo(et_accno.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter correct Account No", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_bankname.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Bank Name", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_bankbranchname.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter Branch Name", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else if (TextUtils.isEmpty(et_ifsccode.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();
                        setViewpager();
                    } else {
                        PersonalData personalData = new PersonalData();
                        personalDataList.add(personalData);
                        personalDataList.get(0).setAccountNo(et_accno.getText().toString().trim());
                        personalDataList.get(0).setBankName(et_bankname.getText().toString().trim());
                        personalDataList.get(0).setBranchName(et_bankbranchname.getText().toString().trim());
                        personalDataList.get(0).setIfseCode(et_ifsccode.getText().toString().trim());

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    void setViewpager() {
        AddUser.viewPager.setCurrentItem(1);
    }

    void setcolor() {
        AddUser.tv_bank.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        AddUser.tv_account.setTextColor(getResources().getColor(R.color.black));
        AddUser.tv_personal.setTextColor(getResources().getColor(R.color.black));
    }
}
