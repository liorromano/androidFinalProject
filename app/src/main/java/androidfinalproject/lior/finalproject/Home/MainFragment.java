package androidfinalproject.lior.finalproject.Home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.LinkedList;
import java.util.List;

import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;
import androidfinalproject.lior.finalproject.Model.User;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.Profile.PostDetailFragment;
import androidfinalproject.lior.finalproject.R;


public class MainFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    List<Post> postsList = new LinkedList<>();
    PostsListAdapter adapter;
    ProgressBar progressBar;

    private MainViewModel postsListViewModel;

    public MainFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmployeeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
       MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        ListView list = view.findViewById(R.id.mainlist_list);
        adapter = new PostsListAdapter();
        list.setAdapter(adapter);
        progressBar = view.findViewById(R.id.mainlist_progressbar);
        //MyTask task = new MyTask();
        progressBar.setVisibility(View.GONE);
        //task.execute("");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Post item = postsList.get(position);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment otherProfileFragment = new OtherProfileFragment();//the fragment you want to show
                Bundle bundle = new Bundle();
                bundle.putString("key",item.getuId());
                otherProfileFragment.setArguments(bundle);

                fragmentTransaction
                        .replace(R.id.container, otherProfileFragment);//R.id.content_frame is the layout you want to replace
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
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
                if (adapter != null) adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onItemSelected(Post post);
    }

    class PostsListAdapter extends BaseAdapter {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
            return postsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = inflater.inflate(R.layout.posts_list_row,null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.namerow_text);
            TextView description = (TextView) convertView.findViewById(R.id.descriptionrow_text);
            final ImageView postImage = (ImageView) convertView.findViewById(R.id.postrow_image);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.strow_progressBar);
            final ImageView profileImage = (ImageView) convertView.findViewById(R.id.list_row_profile_image);
            final Post post = postsList.get(position);
            name.setText(post.name);
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

            UserRepository.instance.getUser(post.uId, new UserRepository.GetUserCallback() {
                @Override
                public void onComplete(final User user) {

                    profileImage.setTag(user.imageUrl);
                    profileImage.setImageDrawable(getContext().getDrawable(R.drawable.avatar));
                    if (user.imageUrl != null && !user.imageUrl.isEmpty() && !user.imageUrl.equals("")){
                        UserRepository.instance.getImage(user.imageUrl, new PostRepository.GetImageListener() {
                            @Override
                            public void onSuccess(Bitmap image) {
                                String tagUrl =  profileImage.getTag().toString();
                                if (tagUrl.equals(user.imageUrl)) {
                                    profileImage.setImageBitmap(image);

                                }
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

            return convertView;
        }
    }


    class MyTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... strings) {
            // do all the asynch jobs ...
            for (int i=0;i<10;i++) {
                Log.d("TAG", "performing task offline...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return 10.0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("TAG","progress update execute on main thread");
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Log.d("TAG","execute on main thread");
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(aDouble);
        }
    }

}
