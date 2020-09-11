package com.svmc.footballMatching.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.ui.account.LoginFragment;
import com.svmc.footballMatching.ui.personalProfile.PlayerPersonalProfileFragment;
import com.svmc.footballMatching.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setBackgroundDrawableResource(R.drawable.login_background);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

//        UserDataSource.getInstance().register("vanhieunguyen221@gmail.com", "123456", "Nguyen Van Hieu", "Player", new RegisterCallBack() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });
//
//        //TODO: splash fragment
        if (viewModel.isLoggedIn() && Session.getInstance().getUserLiveData().getValue() != null) {
            // Go to home fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new HomeFragment(), null)
                    .commit();
        } else {
            // Go to login fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment(), null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                addProfileFragment();
                break;
            case R.id.logout:
                viewModel.logout();
                break;
        }
        return true;
    }

    private void addProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PlayerPersonalProfileFragment(), null)
                .commit();
    }
}