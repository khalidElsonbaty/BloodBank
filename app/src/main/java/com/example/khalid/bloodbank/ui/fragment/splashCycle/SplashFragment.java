package com.example.khalid.bloodbank.ui.fragment.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.helper.HelperMethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private int splash_Display_Lenght = 2000;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SliderFragment sliderFragment=new SliderFragment();
                HelperMethod.replace(sliderFragment,getActivity().getSupportFragmentManager(),R.id.splash_Fragment_Container,null,null);
            }
        }, splash_Display_Lenght);
        return view;
    }
    }

