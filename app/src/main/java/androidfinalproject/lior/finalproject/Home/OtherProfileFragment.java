package androidfinalproject.lior.finalproject.Home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import androidfinalproject.lior.finalproject.Login.LoginActivity;
import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;
import androidfinalproject.lior.finalproject.Model.User;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.Profile.EditProfileActivity;
import androidfinalproject.lior.finalproject.Profile.PostDetailActivity;
import androidfinalproject.lior.finalproject.Profile.PostDetailFragment;
import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 06/02/2018.
 */

public class OtherProfileFragment extends Fragment {

    PostsListAdapter adapter;
    ProgressBar progressBar;
    String myValue;

    private OnFragmentInteractionListener mListener;
    private MainViewModel postsListViewModel;
    List<Post> postsList = new LinkedList<>();
    List<Post> profilePostsList = new LinkedList<>();

    public static OtherProfileFragment newInstance() {
        OtherProfileFragment fragment = new OtherProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        myValue = bundle.getString("key");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView list = view.findViewById(R.id.profile_list_list);
        adapter = new PostsListAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),PostDetailActivity.class);
                Bundle bundle = new Bundle();
                Post item = profilePostsList.get(position);
                bundle.putString("key",item.getId());
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });



        progressBar = view.findViewById(R.id.profile_list_progressbar);
        progressBar.setVisibility(View.GONE);

        final TextView name = (TextView)view.findViewById(R.id.tvDisplay_name);
        final ImageView profileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
               UserRepository.instance.getUser(myValue, new UserRepository.GetUserCallback() {
                   @Override
                   public void onComplete(User user) {
                       name.setText(user.getUserName());
                       if (user.imageUrl != null && !user.imageUrl.isEmpty() && !user.imageUrl.equals(""))
                       {
                           UserRepository.instance.getImage(user.imageUrl, new PostRepository.GetImageListener() {
                               @Override
                               public void onSuccess(Bitmap image) {
                                   profileImage.setImageBitmap(image);
                               }

                               @Override
                               public void onFail() {

                               }
                           });
                       }
                   }

                   @Override
                   public void onCancel() {

                   }
               });

        final Button logout = (Button) view.findViewById(R.id.logout_Btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.instance.logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        logout.setVisibility(View.GONE);

        final Button editProfile = (Button) view.findViewById(R.id.editProfile_btn);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
            }
        });
        editProfile.setVisibility(View.GONE);

         UserRepository.instance.whoLoggedIn(new UserRepository.whoLoggedInListener() {
             @Override
             public void answer(String answer) {
                 if(answer.compareTo(myValue) == 0)
                 {
                     logout.setVisibility(View.VISIBLE);
                     editProfile.setVisibility(View.VISIBLE);

                 }
             }
         });



        return view;
    }


    private void setText()
    {
        TextView tv = (TextView)getView().findViewById(R.id.textView_posts);
        int size = profilePostsList.size();
        String textSize = Integer.toString(size);
        tv.setText(textSize);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        postsListViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        postsListViewModel.getPostsList().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                postsList = posts;


                        Post post;
                        profilePostsList.clear();
                       for(int i=0;i<postsList.size();i++)
                       {
                           post=postsList.get(i);
                           if(post.uId.compareTo(myValue) == 0)
                           {
                               profilePostsList.add(post);

                           }
                       }
                        setText();


                if (adapter != null) adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemSelected(Post post);
    }

    class PostsListAdapter extends BaseAdapter {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
            return profilePostsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class CBListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                Post post = profilePostsList.get(pos);
            }
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = inflater.inflate(R.layout.profile_list_row,null);
            }

            TextView description = (TextView) convertView.findViewById(R.id.profile_list_row_description_text);
            final ImageView postImage = (ImageView) convertView.findViewById(R.id.profile_list_row_image);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.profile_list_row_progressBar);
            final Post post = profilePostsList.get(position);


            final Button delete = convertView.findViewById(R.id.profile_list_row_deleteBtn);
            delete.setVisibility(View.GONE);


            description.setText(post.description);
            postImage.setTag(post.imageUrl);
            postImage.setImageDrawable(getContext().getDrawable(R.drawable.avatar));

            if (post.imageUrl != null && !post.imageUrl.isEmpty() && !post.imageUrl.equals("")){
                progressBar.setVisibility(View.VISIBLE);
                PostRepository.instance.getImage(post.imageUrl, new PostRepository.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                        String tagUrl = postImage.getTag().toString();
                        if (tagUrl.equals(post.imageUrl)) {
                            postImage.setImageBitmap(image);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            return convertView;
        }
    }
}





