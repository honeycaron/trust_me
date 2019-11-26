package com.example.alarmproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listItems = new ArrayList<>();
    Context context;


    public AlarmListAdapter(Context context, ArrayList<ListViewItem> lists){
        this.context = context;
        this.listItems = lists;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }
        TextView tvAmPm = (TextView)convertView.findViewById(R.id.textampm);
        TextView tvTime = (TextView)convertView.findViewById(R.id.texttime);
        TextView tvName = (TextView)convertView.findViewById(R.id.textname);
        Switch lswitch = (Switch)convertView.findViewById(R.id.listswitch);
        TextView tvDays = (TextView)convertView.findViewById(R.id.textdays);

        ListViewItem alarmData = listItems.get(position);
        tvAmPm.setText(alarmData.ampm);
        tvName.setText(alarmData.name);
        String h, m;

        h = String.valueOf(alarmData.hour);
        m = String.valueOf(alarmData.min);
        if(alarmData.hour == 0) //오전 12시
            h = "12";
        if(alarmData.hour <10)
            h = "0"+String.valueOf(alarmData.hour);
        if(alarmData.min < 10)
            m = "0"+String.valueOf(alarmData.min);

        tvTime.setText(h+":"+m);
        lswitch.setChecked(alarmData.onoff);
        tvDays.setText(alarmData.days);

        return convertView;
    }
    public void addItem(String ap, int h, int m, String n, boolean s){
        ListViewItem item = new ListViewItem();
        item.ampm = ap;
        item.hour = h;
        item.min = m;
        item.name = n;
        item.onoff = s;

        listItems.add(item);
    }
}
