package com.example.khalid.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.example.khalid.bloodbank.data.data.model.posts.PostsData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.ui.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<ArticlesRecyclerViewAdapter.MyViewHolder> {


    Context context;
    List<PostsData> dataList =new ArrayList<>();

    public ArticlesRecyclerViewAdapter(Context context, List<PostsData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_articles_view, parent, false);
        MyViewHolder vHolder=new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.tv_article.setText((dataList.get(position).getTitle()));
      //  myViewHolder.civ_fav.setImageResource((mlist.get(position).getFav()));
      //  myViewHolder.iv_photo.setImageResource((mlist.get(position).getThumbnailFullPath()));
        Glide.with(context).load(dataList.get(position).getThumbnailFullPath()).into(myViewHolder.iv_photo);
        Boolean check = Boolean.valueOf(dataList.get(position).getIsFavourite());
        if (check) {
            myViewHolder.toggleButton_fav.setButtonDrawable(R.drawable.ic_drawer_favorite);
        }else {
            myViewHolder.toggleButton_fav.setButtonDrawable(R.drawable.ic_selected_favorite);
        }

        myViewHolder.toggleButton_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    compoundButton.setButtonDrawable(R.drawable.ic_drawer_favorite);
                    myViewHolder.sendNetworkFavorite();
                }

                else {
                    compoundButton.setButtonDrawable(R.drawable.ic_selected_favorite);
                    myViewHolder.sendNetworkFavorite();
                }
            }
        });
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int post_id = dataList.get(position).getId();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("post_id",post_id);
                intent.putExtra("Key","ArticleKey");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_article;
        private ToggleButton toggleButton_fav;
        private ImageView iv_photo;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_article=(TextView)itemView.findViewById(R.id.articles_view_tv);
            iv_photo = (ImageView)itemView.findViewById(R.id.articles_view_iv);
            toggleButton_fav = (ToggleButton) itemView.findViewById(R.id.articles_view_TBtn);
            cardView = (CardView)itemView.findViewById(R.id.articles_Details_CardView);


           // toggleButton_fav.setBackgroundDrawable(ContextCompat.getDrawable(toggleButton_fav.getContext(),R.drawable.ic_drawer_favorite));
        }
        public void sendNetworkFavorite(){
            String post_id = String.valueOf(dataList.get(getAdapterPosition()).getId());
            String api_token = SharedPreferencesManger.LoadStringData((Activity)context,"api_token");
            ApiServices apiServices = RetrofitClient.getClient().create(ApiServices.class);
            apiServices.addPostToggleFavourite(post_id,api_token).enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(context, "fav", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });

        }
    }


}
