package com.shadliq.palaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.shadliq.palaces.adapter.EventTypeArrayAdapter;
import com.shadliq.palaces.bean.FilterBean;
import com.shadliq.palaces.enums.EventTypeEnum;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class DetailedSearch extends AppCompatActivity {
    ImageView imageView;
    Spinner eventTypeSpinner;
    EditText menuText, guestCountText;
    Byte eventType;
    Integer menuPrice;
    Integer guestCount;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_search);
        activity = this;

        eventTypeSpinner = findViewById(R.id.event_type_spinner);
        menuText = findViewById(R.id.menu_text);
        guestCountText = findViewById(R.id.guest_count_text);



        EventTypeArrayAdapter arrayAdapter = new EventTypeArrayAdapter(this, R.layout.item_event_type);

        eventTypeSpinner.setAdapter(arrayAdapter);

        eventTypeSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, EventTypeEnum.values()));


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }



    public void onClick(View view) {

        if (eventTypeSpinner.getSelectedItem() != null) {
            byte selectedValue = EventTypeEnum.values()[eventTypeSpinner.getSelectedItemPosition()].getValue();
            System.out.println("POS " + selectedValue);
            if (selectedValue == 0)
                eventType = null;

            else
                eventType = selectedValue;
        }

        if (menuText.getText() != null && !menuText.getText().toString().equals(""))
            menuPrice = Integer.valueOf(menuText.getText().toString());

        if (guestCountText.getText() != null && !guestCountText.getText().toString().equals(""))
            guestCount = Integer.valueOf(guestCountText.getText().toString());

        Log.i("eventType", eventType + "");
        Log.i("menuPrice", menuPrice + "");
        Log.i("guestCount", guestCount + "");

        FilterBean filterBean = new FilterBean();
        filterBean.setEventType(eventType);
        filterBean.setGuestCount(guestCount);
        filterBean.setMenuPrice(menuPrice);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Paper.book().write("filter", filterBean);
        startActivity(intent);

    }

}
