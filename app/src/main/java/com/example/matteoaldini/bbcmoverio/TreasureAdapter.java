package com.example.matteoaldini.bbcmoverio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.matteoaldini.bbcmoverio.model.TreasureChest;

import java.util.List;
import java.util.Set;

/**
 * Created by matteo.aldini on 03/07/2015.
 */
public class TreasureAdapter extends BaseAdapter {
    private List<TreasureChest> treasureChestSet;
    private Context context;
    public TreasureAdapter(List<TreasureChest> treasureChestSet, Context context){
        this.treasureChestSet = treasureChestSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.treasureChestSet.size();
    }

    @Override
    public Object getItem(int position) {
        return this.treasureChestSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.treasure_layout, parent, false);
        TextView lat = (TextView)rowView.findViewById(R.id.lat);
        TextView longitude = (TextView)rowView.findViewById(R.id.longitude);
        lat.setText(""+this.treasureChestSet.get(position).getLatitude());
        longitude.setText(""+this.treasureChestSet.get(position).getLongitude());
        return rowView;
    }
}
