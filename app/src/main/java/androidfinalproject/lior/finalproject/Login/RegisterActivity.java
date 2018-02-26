package androidfinalproject.lior.finalproject.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidfinalproject.lior.finalproject.Model.User;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by Lior on 22/02/2018.
 */

public class RegisterActivity extends AppCompatActivity{


    ProgressBar progressBar;
    ImageView imageView;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.register_progressBar);
        progressBar.setVisibility(GONE);

        final EditText username = (EditText) findViewById(R.id.register_username_txt);
        final EditText email = (EditText) findViewById(R.id.register_email_txt);
        final EditText password = (EditText) findViewById(R.id.register_password_txt);

        Button cancel = (Button) findViewById(R.id.register_cancel_Btn);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageView = (ImageView) findViewById(R.id.register_profile_picture);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button signUp = (Button) findViewById(R.id.register_signUp_Btn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if((!username.getText().toString().equals("")) && (!email.getText().toString().equals("")) && (!password.getText().toString().equals(""))){
                if(password.getText().toString().length() < 6)
                {
                    alert("password must contains 6 digits");
                }
                else {
                    progressBar.setVisibility(VISIBLE);
                    Log.d("TAG", "Btn SignUp click");

                    final User user = new User();
                    user.setUserName(username.getText().toString());
                    user.imageUrl = "";

                    UserRepository.instance.addUserAuth(password.getText().toString(), email.getText().toString(), new UserRepository.whoLoggedInListener() {
                        @Override
                        public void answer(final String answer) {
                            if(answer == null)
                            {
                                progressBar.setVisibility(GONE);
                                alert("email is already exists");
                            }
                            else {
                                if (imageBitmap != null) {
                                    UserRepository.instance.saveImage(imageBitmap, answer + ".jpeg", new UserRepository.SaveImageListener() {
                                        @Override
                                        public void complete(String url) {
                                            user.imageUrl = url;
                                            UserRepository.instance.addUser(user, answer);
                                            setResult(RESAULT_SUCCESS);
                                            progressBar.setVisibility(GONE);
                                            finish();
                                        }

                                        @Override
                                        public void fail() {
                                            //notify operation fail,...
                                            setResult(RESAULT_SUCCESS);
                                            progressBar.setVisibility(GONE);
                                            finish();
                                        }
                                    });
                                } else {
                                    UserRepository.instance.addUser(user, answer);
                                    setResult(RESAULT_SUCCESS);
                                    progressBar.setVisibility(GONE);
                                    finish();
                                }
                            }
                        }
                    });

                }
            }
            else
            {
                alert("Please fill fields");
            }
        }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void alert(String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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
