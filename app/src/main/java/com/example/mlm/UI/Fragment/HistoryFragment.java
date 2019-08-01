package com.example.mlm.UI.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.mlm.CustomHelper.BaseBackPressedListener;
import com.example.mlm.CustomHelper.PaginationScrollListener;
import com.example.mlm.CustomHelper.UserData;
import com.example.mlm.Entity.HistoryList;
import com.example.mlm.Entity.LeftNodeList;
import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.UI.Activity.SendOtp;
import com.example.mlm.Util.ApiUrl;
import com.example.mlm.Util.PrefrenceHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }

    String logoutUrl = "logout";
    View view;
    UserData userData;
    String url = "earnings-history";
    List<HistoryList> historyListArrayList = new ArrayList<>();
    List<HistoryList> historyListArrayList1 = new ArrayList<>();
    RecyclerView history_recycler;
    MyAdapter myAdapter;
    int pageNo = 1;
    boolean isLoad, isLast;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
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
            history_recycler = view.findViewById(R.id.history_recycler);
            gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            history_recycler.setHasFixedSize(true);
            history_recycler.setLayoutManager(gridLayoutManager);
            getData();
            implementPagination(gridLayoutManager);


        }
    }

    public void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String jsonInString = jsonObject1.getString("paginate");
                        if (jsonInString != null) {
                            historyListArrayList = HistoryList.createListFromObject(jsonInString);
                            if (historyListArrayList != null && historyListArrayList.size() > 0) {
                                isLoad = false;
                                for (int i = 0; i < historyListArrayList.size(); i++) {
                                    historyListArrayList1.add(historyListArrayList.get(i));
                                }
                                if (pageNo == 1) {
                                    history_recycler.setVisibility(View.VISIBLE);
                                    myAdapter = new MyAdapter(getActivity(), historyListArrayList1);
                                    history_recycler.setAdapter(myAdapter);
                                } else {
                                    myAdapter.notifyDataSetChanged();
                                }

                            }
                        }
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
                params.put("page", String.valueOf(pageNo));
                params.put("user_id", userData.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        history_recycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                pageNo++;
                getData();
            }

            @Override
            public int getTotalPageCount() {
                return historyListArrayList1.size() ;
            }

            @Override
            public boolean isLastPage() {
                return isLast;
            }

            @Override
            public boolean isLoading() {
                return isLoad;
            }
        });

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
        Context context;
        List<HistoryList> historyLists = new ArrayList<>();

        public MyAdapter(FragmentActivity activity, List<HistoryList> historyLists) {
            this.context = activity;
            this.historyLists = historyLists;

        }

        @NonNull
        @Override
        public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list, viewGroup, false);
            MyAdapter.VH vh = new MyAdapter.VH(v);
            return vh;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final MyAdapter.VH vh, int i) {
            vh.txt_details.setText(historyLists.get(i).getMessage());
            vh.txt_datetime.setText(historyLists.get(i).getCreated_at());
            vh.txt_rs.setText(historyLists.get(i).getAmount());

        }

        @Override
        public int getItemCount() {
            return historyLists.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView
                    txt_details,
                    txt_datetime,
                    txt_rs;

            public VH(@NonNull View itemView) {
                super(itemView);
                txt_rs = itemView.findViewById(R.id.txt_rs);
                txt_details = itemView.findViewById(R.id.txt_details);
                txt_datetime = itemView.findViewById(R.id.txt_datetime);
            }
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


}
