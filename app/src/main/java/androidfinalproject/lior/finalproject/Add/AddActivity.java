package androidfinalproject.lior.finalproject.Add;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Helper.BottomNavigationViewHelper;
import androidfinalproject.lior.finalproject.R;

public class AddActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setupNevigationBar();

        getProfileFragment();
    }

    private void getProfileFragment()
    {
        AddFragment addFragment = AddFragment.newInstance();
        FragmentTransaction transaction = AddActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,addFragment);
        transaction.addToBackStack("AddFragment");
        transaction.commit();
    }

    private void setupToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

    }

    private void setupNevigationBar()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_navigation_icon);
        BottomNavigationViewHelper.setupBottomNavigtion(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigationClick(AddActivity.this,bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }

    @Override
    public void onBackPressed() {
        ///do nothing
    }
}

