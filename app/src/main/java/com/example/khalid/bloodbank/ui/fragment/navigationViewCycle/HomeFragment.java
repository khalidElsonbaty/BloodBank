package com.example.khalid.bloodbank.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.adapter.ViewPagerAdapter;
import com.example.khalid.bloodbank.ui.fragment.homeCycle.ArticlesFragment;
import com.example.khalid.bloodbank.ui.fragment.homeCycle.DonationOrderFragment;
import com.example.khalid.bloodbank.ui.fragment.homeCycle.OrdersFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        tabLayout = (TabLayout)view.findViewById(R.id.home_Tablayout);


        viewPager=(ViewPager)view.findViewById(R.id.home_viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                ArticlesFragment foodListFragment = (ArticlesFragment) fragment;


                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                OrdersFragment foodListFragment = (OrdersFragment) fragment;


                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {

            }
        }
        adapter.addFragment(new ArticlesFragment(),"مقلات");
        adapter.addFragment(new OrdersFragment(),"طلبات التبرع");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton floatingActionButton=(FloatingActionButton)view.findViewById(R.id.home_fragment_floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonationOrderFragment donationOrderFragment=new DonationOrderFragment();
                HelperMethod.replace(donationOrderFragment,getActivity().getSupportFragmentManager(),R.id.home_fragment_container,null,null);
            }
        });


        return view;
    }

}
