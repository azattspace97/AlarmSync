package com.graduation.alarmsync;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityAddalarmBinding;

public class AddalarmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityAddalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_addalarm);


        binding.groupLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_On));
                    binding.groupLayoutFriendsImage.setVisibility(View.VISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.VISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.VISIBLE);

                } else {
                    binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_Off));
                    binding.groupLayoutFriendsImage.setVisibility(View.INVISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.INVISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
