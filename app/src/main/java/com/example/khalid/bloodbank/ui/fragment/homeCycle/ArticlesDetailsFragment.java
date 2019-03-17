package com.example.khalid.bloodbank.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.posts.Posts;
import com.example.khalid.bloodbank.data.data.model.postsDetails.PostDetailsData;
import com.example.khalid.bloodbank.data.data.model.postsDetails.PostsDetails;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesDetailsFragment extends Fragment {

    ApiServices apiServices;


    @BindView(R.id.articles_Details_Tv)
    TextView articlesDetailsTv;
    @BindView(R.id.articles_Details_view_TBtn)
    ToggleButton articlesDetailsViewTBtn;
    Unbinder unbinder;
    @BindView(R.id.articles_Details_ImageView)
    ImageView articlesDetailsImageView;


    public ArticlesDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_articles_details, container, false);
        setDetailsData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void setDetailsData() {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(), "api_token");
        final int post_id = getArguments().getInt("post_id");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getPostsDetails(api_token, post_id).enqueue(new Callback<PostsDetails>() {
            @Override
            public void onResponse(Call<PostsDetails> call, Response<PostsDetails> response) {
                if (response.body().getStatus() == 1) {
                    PostDetailsData data = response.body().getData();
                    Glide.with(getActivity()).load(data.getThumbnailFullPath()).into(articlesDetailsImageView);
                    articlesDetailsTv.setText(data.getTitle());
                    Boolean check = Boolean.valueOf(data.getIsFavourite());
                    if (check) {
                        articlesDetailsViewTBtn.setButtonDrawable(R.drawable.ic_drawer_favorite);
                    }else {
                        articlesDetailsViewTBtn.setButtonDrawable(R.drawable.ic_selected_favorite);
                    }
                    articlesDetailsViewTBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (isChecked) {
                                compoundButton.setButtonDrawable(R.drawable.ic_drawer_favorite);
                                sendNetworkFavorite();
                            }

                            else {
                                compoundButton.setButtonDrawable(R.drawable.ic_selected_favorite);
                                sendNetworkFavorite();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostsDetails> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.toString());
            }
        });


    }

    private void sendNetworkFavorite() {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(), "api_token");
        final int post_id = getArguments().getInt("post_id");
        ApiServices apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.addPostToggleFavourite(String.valueOf(post_id),api_token).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "fav", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.d(Constraints.TAG, "onFailure: " + t.toString());

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
