package main.com.dvb.homePageFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.com.dvb.R;

/**
 * Created by AIA on 11/11/16.
 */

public class InstagramFragment extends Fragment{

    public static InstagramFragment facebookFragment;
    public InstagramFragment() {
        // Required empty public constructor
        facebookFragment = InstagramFragment.this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bio_fragment, container, false);

        return view;
    }

}
