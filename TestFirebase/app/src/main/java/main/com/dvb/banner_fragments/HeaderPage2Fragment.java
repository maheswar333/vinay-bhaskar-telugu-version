package main.com.dvb.banner_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.com.dvb.R;

/**
 * Created by SREEVATSAVA on 30-07-2016.
 */
public class HeaderPage2Fragment extends Fragment{


    public static HeaderPage2Fragment weeklyFragment;
    public HeaderPage2Fragment() {
        // Required empty public constructor
        weeklyFragment = HeaderPage2Fragment.this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.header_page1_frag, container, false);

        return view;
    }

}
