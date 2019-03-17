package com.example.khalid.bloodbank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.posts.Posts;
import com.example.khalid.bloodbank.data.data.model.posts.PostsData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.adapter.EmptyAdapter;
import com.example.khalid.bloodbank.ui.fragment.splashCycle.SplashFragment;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.splash_Fragment_Container)
    FrameLayout splashFragmentContainer;
    @BindView(R.id.splash_Fragment_RecyclerView)
    RecyclerView splashFragmentRecyclerView;
    private int max=0;
    private ApiServices apiServices;
    private List<PostsData> postsList =new ArrayList<>();
    private EmptyAdapter emptyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
      //   apiServices= getClient().create(ApiServices.class);

        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replace(splashFragment ,getSupportFragmentManager(),R.id.splash_Fragment_Container,null,null);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        splashFragmentRecyclerView.setLayoutManager(linearLayoutManager);
//        OnEndless onEndless =new OnEndless(linearLayoutManager,1) {
//            @Override
//            public void onLoadMore(int current_page) {
//                if(current_page <= max){
//                    getPosts(current_page);
//                }
//
//            }
//        };
//        splashFragmentRecyclerView.addOnScrollListener(onEndless);
//
//        emptyAdapter = new EmptyAdapter(this,postsList);
//        splashFragmentRecyclerView.setAdapter(emptyAdapter);
//
//
//        getPosts(1);
    }

    private void getPosts(final int page) {
        apiServices.getPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27",page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                   Posts posts = response.body();
                    if (posts.getStatus()==1) {
                        postsList.addAll(posts.getData().getData());
                        emptyAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(SplashActivity.this, posts.getMsg(), Toast.LENGTH_LONG).show();

                    }
                }catch(Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
