package com.dacasa.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.dacasa.events.databinding.ActivityHomBinding;
import com.dacasa.events.databinding.ActivityMainBinding;

public class HomActivity extends AppCompatActivity {

    ActivityHomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // changed from HomFragment to Nyumbanifragment
        replaceFragment(new NyumbaniFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:
                    // changed from HomFragment to Nyumbanifragment
                    replaceFragment(new NyumbaniFragment());
                    break;
                case R.id.mymedicine:
                    replaceFragment(new HomFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}