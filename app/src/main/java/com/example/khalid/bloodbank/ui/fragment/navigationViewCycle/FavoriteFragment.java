package com.example.khalid.bloodbank.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.posts.Posts;
import com.example.khalid.bloodbank.data.data.model.posts.PostsData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.adapter.ArticlesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private ArticlesRecyclerViewAdapter receclerViewAdapter;
    private List<PostsData> data =new ArrayList<>();
    private ApiServices apiServices;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        myRecyclerView =(RecyclerView) view.findViewById(R.id.favorite_List_Recycle_View);
        receclerViewAdapter=new ArticlesRecyclerViewAdapter(getContext(),data);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(receclerViewAdapter);
        setFavoriteData();
        return view;
    }

    private void setFavoriteData() {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(),"api_token");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getFavouritesList(api_token).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.body().getStatus()==1){
                    data.addAll(response.body().getData().getData());
                    receclerViewAdapter.notifyDataSetChanged();


                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());

            }
        });
    }


}
