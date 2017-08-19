package xyz.aungpyaephyo.padc.firebase.data.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.firebase.FirebaseApp;
import xyz.aungpyaephyo.padc.firebase.data.vo.NewsFeedVO;
import xyz.aungpyaephyo.padc.firebase.events.FirebaseEvents;

/**
 * Created by aung on 8/18/17.
 */

public class NewsFeedModel {

    private static final String PATH_SAMPLE_DATA = "sample_data";
    private static final String OFFLINE_NEWSFEED = "news_feed.json";

    private static final String MM_NEWS_FEED = "mmNewsFeed";

    private static NewsFeedModel objInstance;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference mNewsFeedDR;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private NewsFeedModel() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    public static NewsFeedModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsFeedModel();
        }
        return objInstance;
    }

    public List<NewsFeedVO> getSampleNewsFeed(Context context) {
        try {
            String newsFeed = loadDummyData(context, OFFLINE_NEWSFEED);
            Type listType = new TypeToken<List<NewsFeedVO>>() {
            }.getType();
            List<NewsFeedVO> newsFeedList = new Gson().fromJson(newsFeed, listType);
            return newsFeedList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void loadNewsFeed() {
        mNewsFeedDR = mDatabaseReference.child(MM_NEWS_FEED);
        mNewsFeedDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    List<NewsFeedVO> newsFeedList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        NewsFeedVO newsFeed = snapshot.getValue(NewsFeedVO.class);
                        newsFeedList.add(newsFeed);
                    }
                    FirebaseEvents.NewsFeedLoadedEvent event = new FirebaseEvents.NewsFeedLoadedEvent(newsFeedList);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean isUserSignIn() {
        return mFirebaseUser != null;
    }

    public void authenticateUserWithGoogleAccount(final GoogleSignInAccount signInAccount, final SignInWithGoogleAccountDelegate delegate) {
        Log.d(FirebaseApp.TAG, "signInAccount Id :" + signInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(FirebaseApp.TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(FirebaseApp.TAG, "signInWithCredential", task.getException());
                            delegate.onFailureSignIn(task.getException().getMessage());
                        } else {
                            Log.d(FirebaseApp.TAG, "signInWithCredential - successful");
                            delegate.onSuccessSignIn(signInAccount);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FirebaseApp.TAG, "OnFailureListener : " + e.getMessage());
                        delegate.onFailureSignIn(e.getMessage());
                    }
                });
    }

    public void addNews(String newsContent) {
        NewsFeedVO news = NewsFeedVO.initNews(newsContent, mFirebaseUser);
        mNewsFeedDR.child(String.valueOf(news.getPosedDate())).setValue(news);
    }

    /**
     * Read text from assets folder.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private byte[] readJsonFile(Context context, String fileName) throws IOException {
        InputStream inStream = context.getAssets().open(fileName);
        int size = inStream.available();
        byte[] buffer = new byte[size];
        inStream.read(buffer);
        inStream.close();
        return buffer;
    }

    /**
     * @param fileName - name of Json File.
     * @return JSONObject from loaded file.
     * @throws IOException
     * @throws JSONException
     */
    private String loadDummyData(Context context, String fileName) throws IOException, JSONException {
        byte[] buffer = readJsonFile(context, PATH_SAMPLE_DATA + "/" + fileName);
        return new String(buffer, "UTF-8").toString();
    }

    public interface SignInWithGoogleAccountDelegate {
        void onSuccessSignIn(GoogleSignInAccount signInAccount);

        void onFailureSignIn(String msg);
    }
}
