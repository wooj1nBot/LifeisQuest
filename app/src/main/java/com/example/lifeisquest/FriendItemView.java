package com.example.lifeisquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendItemView extends RelativeLayout {

    TextView tv_name;

    public FriendItemView(Context context) {
        super(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.friend_add_list_item,this);


    }




}
