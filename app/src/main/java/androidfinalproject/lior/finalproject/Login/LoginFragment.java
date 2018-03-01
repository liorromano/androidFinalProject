package androidfinalproject.lior.finalproject.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Lior on 01/03/2018.
 */

public class LoginFragment extends Fragment{


    ProgressBar progressBar;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText email = (EditText) view.findViewById(R.id.login_email_txt);
        final EditText password = (EditText) view.findViewById(R.id.login_password_txt);

        progressBar = view.findViewById(R.id.login_progressBar);
        progressBar.setVisibility(GONE);

        Button signUp = (Button) view.findViewById(R.id.login_signUp_Btn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button login = (Button) view.findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!email.getText().toString().equals("")) && (!password.getText().toString().equals("")))
                {
                    progressBar.setVisibility(VISIBLE);
                    UserRepository.instance.login(email.getText().toString(), password.getText().toString(), new UserRepository.LoginListener() {
                        @Override
                        public void answer(Boolean answer) {
                            if(answer == true)
                            {
                                progressBar.setVisibility(GONE);
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                progressBar.setVisibility(GONE);
                                alert("Username or password are incorrect");
                            }
                        }
                    });
                }
                else
                {
                    alert("please fill fields");
                }
            }
        });

        return view;
    }

    private void alert(String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
