package androidfinalproject.lior.finalproject.Profile;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 01/03/2018.
 */

public class PostDetailActivity extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        bundle = getIntent().getExtras();

        getProfileFragment();
    }


    private void getProfileFragment()
    {
        PostDetailFragment postDetailFragment = PostDetailFragment.newInstance();
        postDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = PostDetailActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,postDetailFragment);
        transaction.addToBackStack("PostDetailFragment");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        ///do nothing
    }


}
