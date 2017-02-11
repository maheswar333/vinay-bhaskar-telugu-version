package main.com.dvb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.com.dvb.R;
import main.com.dvb.pojos.FB.FBCommentBean;
import main.com.dvb.pojos.JourneyBean;

/**
 * Created by AIA on 11/29/16.
 */

public class JourneyListAdapter extends BaseAdapter {

    private ArrayList<JourneyBean> beanArrayList;
    private static LayoutInflater inflater=null;

    public JourneyListAdapter(ArrayList<JourneyBean> list, Context context){
        this.beanArrayList = list;

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return beanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder viewHolder;

        JourneyBean journeyBean = beanArrayList.get(position);
        if(convertView == null){
            view = inflater.inflate(R.layout.journey_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.journeyDate = (TextView) view.findViewById(R.id.journeyDate);
            viewHolder.journeyValue=(TextView)view.findViewById(R.id.journeyValue);

            view.setTag( viewHolder );
        }else
            viewHolder=(ViewHolder)view.getTag();

        viewHolder.journeyDate.setText(journeyBean.getJourneyDate());
        viewHolder.journeyValue.setText(journeyBean.getJourneyValue());

        return view;
    }

    public static class ViewHolder{

        public TextView journeyDate;
        public TextView journeyValue;

    }

}
