package com.example.matteoaldini.bbcmoverio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        TextView state = (TextView)rowView.findViewById(R.id.state);
        TextView number = (TextView)rowView.findViewById(R.id.number);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.colorImg);
        if(this.treasureChestSet.get(position).getNumber()!=0){
            number.setText(""+this.treasureChestSet.get(position).getNumber());
        }else {
            number.setText("");
        }
        lat.setText(""+this.treasureChestSet.get(position).getLatitude());
        longitude.setText(""+this.treasureChestSet.get(position).getLongitude());
        state.setText(this.treasureChestSet.get(position).getState().name());
        switch (this.treasureChestSet.get(position).getState()){
            case UNVISITED:
                imageView.setBackgroundColor(rowView.getResources().getColor(R.color.white));
                break;
            case LOCKED_KEY:
                imageView.setBackgroundColor(rowView.getResources().getColor(R.color.red));
                break;
            case LOCKED_COOPERATION:
                imageView.setBackgroundColor(rowView.getResources().getColor(R.color.blue));
                break;
            case FINAL:
                imageView.setBackgroundColor(rowView.getResources().getColor(R.color.yellow));
                break;
            case OPEN:
                imageView.setBackgroundColor(rowView.getResources().getColor(R.color.green));
                break;
        }
        return rowView;
    }
}
