package main.com.dvb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import main.com.dvb.R;
import main.com.dvb.pojos.MyReportsBean;
import main.com.dvb.pojos.NotificationsBean;

/**
 * Created by AIA on 11/6/16.
 */

public class NotificationsAdapter extends BaseAdapter {

    private ArrayList<NotificationsBean> list;
    LayoutInflater inflater;
    public NotificationsAdapter(ArrayList<NotificationsBean> myReportslist, Context context){
        this.list = myReportslist;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.comment_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.subject_notification = (TextView) vi.findViewById(R.id.comment_name);
            holder.date_notification=(TextView)vi.findViewById(R.id.comment_date);
            holder.details_notification=(TextView)vi.findViewById(R.id.comment_message);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        NotificationsBean notificationsBean = list.get(position);
        holder.subject_notification.setText(notificationsBean.getSubject_notification());
        holder.date_notification.setText(notificationsBean.getDate_notification());
        holder.details_notification.setText(notificationsBean.getMessage_notification());
        return vi;
    }

    public static class ViewHolder{

        public TextView subject_notification;
        public TextView details_notification;
        public TextView date_notification;

    }
}
