package com.svmc.footballMatching.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.ui.account.LoginFragment;
import com.svmc.footballMatching.ui.account.personalProfile.UserProfileFragment;
import com.svmc.footballMatching.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setBackgroundDrawableResource(R.drawable.login_background);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


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
                .add(R.id.container, new UserProfileFragment(), null)
                .commit();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, Bundle args, String tag) {
        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack, Bundle args, String tag) {
        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addFragmentWithoutView(Fragment fragment, Bundle args, String tag) {
        if (args != null) {
            fragment.setArguments(args);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(fragment, tag)
                .commit();
    }
}