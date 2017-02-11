package main.com.dvb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import main.com.dvb.R;

/**
 * Created by SREEVATSAVA on 30-07-2016.
 */
public class BioFragment extends Fragment{

//    GridView gridView;
    ImageView bottomImage;
    LinearLayout bottomLayout;

    ArrayList<String> resultList = new ArrayList<>();

    public static BioFragment bioFragment;
    public BioFragment() {
        // Required empty public constructor
        bioFragment = BioFragment.this;
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
//        bottomImage = (ImageView) view.findViewById(R.id.bottomImage);
//
//        bottomImage.getLayoutParams().width = (int) (Dashboard_main.width/3f);

        prepareList();
        return view;
    }

    public void prepareList(){
        for(int i =0;i<8;i++){
            resultList.add("Weekly "+i);
        }
    }

}
