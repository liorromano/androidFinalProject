package androidfinalproject.lior.finalproject.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import androidfinalproject.lior.finalproject.MyApplication;

import static android.content.Context.MODE_PRIVATE;



public class PostRepository {
    public static final PostRepository instance = new PostRepository();
    MutableLiveData<List<Post>> postsListliveData;



    PostRepository(){
        for(int i=0;i<5;i++) {
            Post emp = new Post();
            emp.name = "KUKU" + i;
            emp.id = ""+i;
            PostFirebase.addPost(emp);
        }
    }

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }


    public LiveData<List<Post>> getPostsList() {
        synchronized (this) {
            if (postsListliveData == null) {
                postsListliveData = new MutableLiveData<List<Post>>();
                PostFirebase.getAllPostsAndObserve(new PostFirebase.Callback<List<Post>>() {

                    @Override
                    public void onComplete(List<Post> data) {
                        if (data != null) postsListliveData.setValue(data);
                        Log.d("TAG", "got post data");
                    }
                });
            }
        }
        return postsListliveData;
    }

    public LiveData<List<Post>> getAllPosts() {
        synchronized (this) {
            if (postsListliveData == null) {
                postsListliveData = new MutableLiveData<List<Post>>();

                //1. get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = MyApplication.getMyContext()
                            .getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
                }catch (Exception e){

                }

                //2. get all students records that where updated since last update date
                PostFirebase.getAllPostsAndObserve(lastUpdateDate, new PostFirebase.Callback<List<Post>>() {
                    @Override
                    public void onComplete(List<Post> data) {
                        updatePostDataInLocalStorage(data);
                    }
                });
            }
        }
        return postsListliveData;
    }

    private void updatePostDataInLocalStorage(List<Post> data) {
        Log.d("TAG", "got items from firebase: " + data.size());
        MyTask task = new MyTask();
        task.execute(data);
    }




    public void getImage(final String url, final GetImageListener listener) {
        //check if image exsist localy
        String fileName = URLUtil.guessFileName(url, null, null);
        Bitmap image = loadImageFromFile(fileName);

        if (image != null){
            Log.d("TAG","getImage from local success " + fileName);
            listener.onSuccess(image);
        }else {
            PostFirebase.getImage(url, new GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    String fileName = URLUtil.guessFileName(url, null, null);
                    Log.d("TAG","getImage from FB success " + fileName);
                    saveImageToFile(image,fileName);
                    listener.onSuccess(image);
                }

                @Override
                public void onFail() {
                    Log.d("TAG","getImage from FB fail ");
                    listener.onFail();
                }
            });

        }
    }

    private Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPicureToGallery(File imageFile){
        //add the picture to the gallery so we dont need to manage the cache size
        Intent mediaScanIntent = new
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        MyApplication.getMyContext().sendBroadcast(mediaScanIntent);
    }





    class MyTask extends AsyncTask<List<Post>,String,List<Post>> {
        @Override
        protected List<Post> doInBackground(List<Post>[] lists) {
            Log.d("TAG", "starting updateEmployeeDataInLocalStorage in thread");
            if (lists.length > 0) {
                List<Post> data = lists[0];
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = MyApplication.getMyContext()
                            .getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
                } catch (Exception e) {

                }
                if (data != null && data.size() > 0) {
                    //3. update the local DB
                    long reacentUpdate = lastUpdateDate;
                    for (Post post : data) {
                        AppLocalStore.db.postDao().insertAll(post);
                        if (post.lastUpdated > reacentUpdate) {
                            reacentUpdate = post.lastUpdated;
                        }
                        Log.d("TAG", "updating: " + post.toString());
                    }
                    SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                    editor.putLong("lastUpdateDate", reacentUpdate);
                    editor.commit();
                }
                //return the complete student list to the caller
                List<Post> postList = AppLocalStore.db.postDao().getAll();
                Log.d("TAG", "finish updateEmployeeDataInLocalStorage in thread");

                return postList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            postsListliveData.setValue(posts);
            Log.d("TAG","update updateEmployeeDataInLocalStorage in main thread");
            Log.d("TAG", "got items from local db: " + posts.size());

        }
    }
}
