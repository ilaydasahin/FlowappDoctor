package com.sahin.flowapp.doctor.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.sahin.flowapp.doctor.Fragments.KullaniciPetlerFragment;
import com.sahin.flowapp.doctor.R;

public class ChangeFragments {


    private final Context context;

    public ChangeFragments(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment) {
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    //hasId
    public void changeWithParameter(KullaniciPetlerFragment fragment, String petid) {
        Bundle bundle=new Bundle();
        //hasid,hasId
        bundle.putString("userid",petid);
        fragment.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
