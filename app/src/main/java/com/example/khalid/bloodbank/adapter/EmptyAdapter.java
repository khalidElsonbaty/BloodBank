package com.example.khalid.bloodbank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.posts.PostsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.ViewHolder> {

   private Context context;
   private List<PostsData> postsDataList= new ArrayList<>();

    public EmptyAdapter(Context context, List<PostsData> postsDataList) {
        this.context = context;
        this.postsDataList = postsDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_splash,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder,position);
        setAction(holder,position);

    }

    private void setData(ViewHolder holder, int position) {
    }
    private void setAction(ViewHolder holder, int position) {
    }



    @Override
    public int getItemCount() {
        return postsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view =itemView;
            ButterKnife.bind(this,view);
        }
    }
}
