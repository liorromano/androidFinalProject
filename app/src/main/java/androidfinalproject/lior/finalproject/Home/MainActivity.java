package androidfinalproject.lior.finalproject.Home;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Helper.BottomNavigationViewHelper;
import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Profile.ProfileActivity;
import androidfinalproject.lior.finalproject.R;
import androidfinalproject.lior.finalproject.SectionPageAdapter;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNevigationBar();

        getMainFragment();


    }

    private void getMainFragment()
    {
        MainFragment listFragment = MainFragment.newInstance();
        FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,listFragment);
        transaction.addToBackStack("MainFragment");
        transaction.commit();
    }


    private void setupNevigationBar()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_navigation_icon);
        BottomNavigationViewHelper.setupBottomNavigtion(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigationClick(MainActivity.this,bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

    }

    public void setUpViewPage()
    {
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.addFragment(new MainFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPageAdapter);

    }

    @Override
    public void onItemSelected(Post post) {

    }
}

