package com.shadliq.palaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shadliq.palaces.R;
import com.shadliq.palaces.enums.EventTypeEnum;

import androidx.annotation.NonNull;

public class EventTypeArrayAdapter extends ArrayAdapter<EventTypeEnum> {

    private Context context;
    //    private ListFilter listFilter = new ListFilter();
    private LayoutInflater inflater;

    public EventTypeArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_event_type, parent, false);
        }
        EventTypeEnum eventTypeEnum = getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.name);
        nameTextView.setText(eventTypeEnum != null ? context.getResources().getString(eventTypeEnum.getStrId()) : null);

        return convertView;
    }

    @Override
    public int getCount() {
        return EventTypeEnum.values().length;
    }

    @Override
    public EventTypeEnum getItem(int position) {
        return EventTypeEnum.values()[position];
    }

}