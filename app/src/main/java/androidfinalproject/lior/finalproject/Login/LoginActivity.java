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


import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Lior on 22/02/2018.
 */

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = (EditText) findViewById(R.id.login_email_txt);
        final EditText password = (EditText) findViewById(R.id.login_password_txt);

        progressBar = findViewById(R.id.login_progressBar);
        progressBar.setVisibility(GONE);

        Button signUp = (Button) findViewById(R.id.login_signUp_Btn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(VISIBLE);
                if(email.getText()!= null && password.getText()!=null)
                {
                    UserRepository.instance.login(email.getText().toString(), password.getText().toString(), new UserRepository.LoginListener() {
                        @Override
                        public void answer(Boolean answer) {
                            if(answer == true)
                            {
                                progressBar.setVisibility(GONE);
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                progressBar.setVisibility(GONE);

                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Username or password are incorrect");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        ///do nothing
    }

}
