package androidfinalproject.lior.finalproject.Add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;
import androidfinalproject.lior.finalproject.Model.User;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by Lior on 07/02/2018.
 */

public class AddFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    //final static int RESAULT_SUCCESS = 0;
    final static int RESAULT_FAIL = 1;

    ImageView imageView;
    Bitmap imageBitmap;
    ProgressBar progressBar;


    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.addProgressBar);
        progressBar.setVisibility(GONE);

        final EditText description = (EditText) view.findViewById(R.id.add_editText);

        Button saveBtn = (Button) view.findViewById(R.id.add_saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(VISIBLE);

                Log.d("TAG","Btn Save click");
                final Post post = new Post();
                UserRepository.instance.whoLoggedIn(new UserRepository.whoLoggedInListener() {
                    @Override
                    public void answer(final String uid) {
                        UserRepository.instance.getUser(uid, new UserRepository.GetUserCallback() {
                            @Override
                            public void onComplete(User user) {

                                int numberOfPosts= user.getNumberOfPosts();
                                post.id = uid+numberOfPosts;
                                post.uId = uid;
                                post.name = user.getUserName();
                                post.description = description.getText().toString();

                                post.imageUrl = "";
                                if (imageBitmap != null) {
                                    PostRepository.instance.saveImage(imageBitmap, post.id + ".jpeg", new PostRepository.SaveImageListener() {
                                        @Override
                                        public void complete(String url) {
                                            post.imageUrl = url;
                                            PostRepository.instance.addPost(post);
                                            getActivity().setResult(RESAULT_SUCCESS);
                                            UserRepository.instance.updateUserPosts(uid);
                                            progressBar.setVisibility(GONE);
                                            getActivity().finish();
                                        }

                                        @Override
                                        public void fail() {
                                            //notify operation fail,...
                                            getActivity().setResult(RESAULT_SUCCESS);
                                            progressBar.setVisibility(GONE);
                                            getActivity().finish();
                                        }
                                    });
                                }else {
                                    progressBar.setVisibility(GONE);
                                    alert("Please choose picture");
                                }
                            }
                            @Override
                            public void onCancel() {

                            }
                        });

                    }
                });

            }
        });
        imageView = (ImageView) view.findViewById(R.id.editProfile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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
