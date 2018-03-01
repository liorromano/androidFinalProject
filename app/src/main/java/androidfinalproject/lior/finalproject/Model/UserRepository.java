package androidfinalproject.lior.finalproject.Model;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import androidfinalproject.lior.finalproject.MyApplication;

/**
 * Created by Lior on 21/02/2018.
 */

public class UserRepository {


    public static final UserRepository instance = new UserRepository();

    public void logout() {
        UserFirebase.logout();
    }

    public interface whoLoggedInListener{
        void answer(String answer);
    }

    public interface LoginListener{
        void answer(Boolean answer);
    }

    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void addUserAuth(String password, String email, final whoLoggedInListener listner)
    {
        UserFirebase.addUserAuth(password, email, new UserFirebase.Callback<String>() {
            @Override
            public void onComplete(String data) {
                listner.answer(data);
            }
        });
    }

    public void addUser(User user,String uid) {
       UserFirebase.addUser(user,uid);
    }

    public void saveImage(final Bitmap imageBmp, final String name, final UserRepository.SaveImageListener listener) {
        UserFirebase.saveImage(imageBmp, name, new UserRepository.SaveImageListener() {
            @Override
            public void complete(String url) {
                String fileName = URLUtil.guessFileName(url, null, null);
                saveImageToFile(imageBmp,fileName);
                listener.complete(url);
            }

            @Override
            public void fail() {
                listener.fail();
            }
        });


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

    public void login(String email, String password, final UserRepository.LoginListener listener)
    {
        UserFirebase.login(email, password, new UserFirebase.Callback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                Boolean answer= data;
                listener.answer(answer);
            }
        });
    }

    public void checkLoggedIn(final UserRepository.LoginListener listener) {
        UserFirebase.checkIfloggedIn(new UserFirebase.Callback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                Boolean answer= data;
                listener.answer(answer);
            }
        });
    }

    public void whoLoggedIn(final whoLoggedInListener listener)
    {
        UserFirebase.whoLoggedIn(new UserFirebase.Callback<String>() {
            @Override
            public void onComplete(String uid) {
                if(uid != null)
                {
                    listener.answer(uid);
                }
                else
                {
                    listener.answer(null);
                }
            }
        });
    }

    public interface GetUserCallback {
        void onComplete(User user);

        void onCancel();
    }

    public void getUser(String uId, final GetUserCallback callback) {
        //return StudentSql.getStudent(modelSql.getReadableDatabase(),stId);
        UserFirebase.getUser(uId, new UserFirebase.GetUserCallback() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });

    }

    public void updateUserPosts(String uid)
    {
        UserFirebase.updateUserPosts(uid);
    }

    public void getImage(final String url, final PostRepository.GetImageListener listener) {
        //check if image exsist localy
        String fileName = URLUtil.guessFileName(url, null, null);
        Bitmap image = loadImageFromFile(fileName);

        if (image != null){
            Log.d("TAG","getImage from local success " + fileName);
            listener.onSuccess(image);
        }else {
            UserFirebase.getImage(url, new PostRepository.GetImageListener() {
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

    public void updateUserImageUrl(String url, String uid)
    {
        UserFirebase.updateUserImageUrl(url,uid);
    }
}
