package com.example.khalid.bloodbank.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.ArticlesViewModel;
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

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView ;
    private List<ArticlesViewModel> listarticle;
    private ApiServices apiServices;
    private List<PostsData> data =new ArrayList<>();
    private ArticlesRecyclerViewAdapter receclerViewAdapter;


    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_articles, container, false);
        myRecyclerView =(RecyclerView) v.findViewById(R.id.articles_recycle_view);
        receclerViewAdapter=new ArticlesRecyclerViewAdapter(getContext(),data);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(receclerViewAdapter);
        setArticleData();

        return v;
    }

    private void setArticleData( ) {
        String api_token= SharedPreferencesManger.LoadStringData(getActivity(),"api_token");
        int page = 1;
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getPosts(api_token,page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.body().getStatus()==1){
                    data.addAll(response.body().getData().getData());
                    receclerViewAdapter.notifyDataSetChanged();


                }
                else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.toString() );

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
