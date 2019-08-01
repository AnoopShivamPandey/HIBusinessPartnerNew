package com.example.mlm.UI.Fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.mlm.Entity.LeftNodeList;
import com.example.mlm.Entity.RightNodeList;
import com.example.mlm.MainActivity;
import com.example.mlm.R;
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
public class RightNode extends Fragment {


    public RightNode() {
        // Required empty public constructor
    }

    View view = null;
    String url = "right-nodes";
    UserData userData;
    List<RightNodeList> rightNodes = new ArrayList<>();
    List<RightNodeList> rightNodes1 = new ArrayList<>();
    RecyclerView right_recycler;
    MyAdapter myAdapter;
    int pageNo = 1;
    boolean isLoad, isLast;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_right_node, container, false);
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
            right_recycler = view.findViewById(R.id.right_recycler);
            gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            right_recycler.setHasFixedSize(true);
            right_recycler.setLayoutManager(gridLayoutManager);
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
                    if (jsonObject.getBoolean("status")) ;
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String jsonInString = jsonObject1.getString("listing");
                    rightNodes = RightNodeList.createListFromObject(jsonInString);
                    if (jsonInString != null) {
                        rightNodes = RightNodeList.createListFromObject(jsonInString);
                        if (rightNodes != null && rightNodes.size() > 0) {
                            isLoad = false;
                            for (int i = 0; i < rightNodes.size(); i++) {
                                rightNodes1.add(rightNodes.get(i));
                            }
                            if (pageNo == 1) {
                                right_recycler.setVisibility(View.VISIBLE);
                                myAdapter = new MyAdapter(getActivity(), rightNodes1);
                                right_recycler.setAdapter(myAdapter);
                            } else {
                                myAdapter.notifyDataSetChanged();
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
                params.put("page_no", String.valueOf(pageNo));
                params.put("limit", "20");
                params.put("user_id", userData.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void implementPagination(GridLayoutManager linearLayoutManager) {
        right_recycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLast = false;
                isLoad = true;
                pageNo++;
                getData();
            }

            @Override
            public int getTotalPageCount() {
                return rightNodes1.size();
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
        List<RightNodeList> rightNodeLists = new ArrayList<>();

        public MyAdapter(FragmentActivity activity, List<RightNodeList> rightNodeLists) {
            this.context = activity;
            this.rightNodeLists = rightNodeLists;

        }

        @NonNull
        @Override
        public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.node_list, viewGroup, false);
            MyAdapter.VH vh = new MyAdapter.VH(v);
            return vh;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final MyAdapter.VH vh, int i) {
            vh.txt_name.setText(rightNodeLists.get(i).getFirst_name() + rightNodeLists.get(i).getLast_name());
            vh.txt_mobilenumber.setText(rightNodeLists.get(i).getMobile());
            vh.txt_date.setText(rightNodeLists.get(i).getJoining_date_format());
            String status = rightNodeLists.get(i).getPayment_status();
            if (status.equalsIgnoreCase("true")) {
                vh.txt_payment.setText("Payament:Yes");
                vh.txt_payment.setTextColor(context.getResources().getColor(R.color.yello));
            }


            Picasso.with(context).load(rightNodeLists.get(i).getPhoto()).placeholder(R.drawable.user_image).into(new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    vh.profile_image.setImageBitmap(bitmap);
                    vh.profile_image.setScaleType(ImageView.ScaleType.FIT_XY);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }
            });

        }

        @Override
        public int getItemCount() {
            return rightNodeLists.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            CircleImageView profile_image;
            TextView txt_payment, txt_name, txt_mobilenumber, txt_date;

            public VH(@NonNull View itemView) {
                super(itemView);
                profile_image = itemView.findViewById(R.id.profile_image);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_mobilenumber = itemView.findViewById(R.id.txt_mobilenumber);
                txt_payment = itemView.findViewById(R.id.txt_payment);
            }
        }
    }
}
