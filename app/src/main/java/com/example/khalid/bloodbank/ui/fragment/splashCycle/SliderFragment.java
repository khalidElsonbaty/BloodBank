package com.example.khalid.bloodbank.ui.fragment.splashCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.ui.activity.LoginActivity;
import com.example.khalid.bloodbank.adapter.SlideShowAdapter;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {

    ViewPager viewPager;
    SlideShowAdapter adapter;
    CircleIndicator indicator;
    private View view;


    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_slider, container, false);
        indicator =(CircleIndicator)view.findViewById(R.id.slider_Fragment_Circleindicator_Id);
        viewPager=(ViewPager)view.findViewById(R.id.slider_Fragment_ViewPager);
        adapter=new SlideShowAdapter(getActivity());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        Button skipButton =(Button)view.findViewById(R.id.slider_Fragment_Skip_Btn);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
