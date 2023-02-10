package com.sahin.flowapp.doctor.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.sahin.flowapp.doctor.Fragments.HomeFragment;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChangeFragments changeFragments=new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }
}
