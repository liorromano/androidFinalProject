package androidfinalproject.lior.finalproject.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import androidfinalproject.lior.finalproject.Add.AddActivity;
import androidfinalproject.lior.finalproject.Add.AddFragment;
import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Lior on 22/02/2018.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getProfileFragment();
    }


    private void getProfileFragment()
    {
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction transaction = LoginActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,loginFragment);
        transaction.addToBackStack("LoginFragment");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        ///do nothing
    }

}
