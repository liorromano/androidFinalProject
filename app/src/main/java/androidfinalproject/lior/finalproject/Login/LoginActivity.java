package androidfinalproject.lior.finalproject.Login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 22/02/2018.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getMainFragment();
    }

    private void getMainFragment()
    {
        LoginFragment listFragment = LoginFragment.newInstance();
        FragmentTransaction transaction = LoginActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,listFragment);
        transaction.addToBackStack("LoginFragment");
        transaction.commit();
    }
}
