package com.example.khalid.bloodbank.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.ArticlesViewModel;
import com.example.khalid.bloodbank.data.data.model.donationRequest.DonationRequest;
import com.example.khalid.bloodbank.data.data.model.donationRequest.DonationRequestData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.OnEndless;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.adapter.OrderRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<ArticlesViewModel> listarticle;
    private ApiServices apiServices;
    private ArrayList<DonationRequestData> orderData = new ArrayList<>();
    private OrderRecyclerViewAdapter receclerViewAdapter;
    private int max = 0;
    private LinearLayoutManager linearLayoutManager;


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.Order_Recycle_View);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setArticleData(current_page);
                }

            }
        };
        myRecyclerView.addOnScrollListener(onEndless);
        receclerViewAdapter = new OrderRecyclerViewAdapter(getContext(), orderData);
        myRecyclerView.setAdapter(receclerViewAdapter);

        setArticleData(1);
        return v;
    }

    private void setArticleData(int page) {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(), "api_token");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getDonationRequests(api_token).enqueue(new Callback<DonationRequest>() {
            @Override
            public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                if (response.body().getStatus() == 1) {
                    orderData.addAll(response.body().getData().getData());
                    max = response.body().getData().getLastPage();
                    receclerViewAdapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<DonationRequest> call, Throwable t) {

            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        listarticle =new ArrayList<>();
//        listarticle.add(new ArticlesViewModel("مشروع  حمايه الطفل",R.drawable.ic_drawer_favorite,R.drawable.articles_pic1));
//        listarticle.add(new ArticlesViewModel("مشروع  حمايه الطفل",R.drawable.ic_drawer_favorite,R.drawable.articles_pic1));
//        listarticle.add(new ArticlesViewModel("مشروع  حمايه الطفل",R.drawable.ic_drawer_favorite,R.drawable.articles_pic1));
//        listarticle.add(new ArticlesViewModel("مشروع  حمايه الطفل",R.drawable.ic_drawer_favorite,R.drawable.articles_pic1));
//        listarticle.add(new ArticlesViewModel("مشروع  حمايه الطفل",R.drawable.ic_drawer_favorite,R.drawable.articles_pic1));

    }
}
