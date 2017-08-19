package xyz.aungpyaephyo.padc.firebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.firebase.FirebaseApp;
import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.adapters.NewsFeedsAdapter;
import xyz.aungpyaephyo.padc.firebase.components.rvset.SmartRecyclerView;
import xyz.aungpyaephyo.padc.firebase.data.models.NewsFeedModel;
import xyz.aungpyaephyo.padc.firebase.events.FirebaseEvents;
import xyz.aungpyaephyo.padc.firebase.utils.FirebaseAppConstants;
import xyz.aungpyaephyo.padc.firebase.views.pods.EmptyViewPod;

public class NewsFeedActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    protected static final int RC_GOOGLE_SIGN_IN = 1236;

    private static final String IE_NOTIFICATION_ID = "IE_NOTIFICATION_ID";
    private static final String IE_LAUNCH_ACTION = "IE_LAUNCH_ACTION";

    private static final int LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION = 2222;
    private static final int LAUNCH_ACTION_TAP_NOTI_BODY = 2223;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_screen_title)
    TextView tvScreenTitle;

    @BindView(R.id.rv_news_feed)
    SmartRecyclerView rvNewsFeed;

    @BindView(R.id.vp_empty_news_feed)
    EmptyViewPod vpEmptyNewsFeed;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsFeedsAdapter mNewsFeedsAdapter;

    protected GoogleApiClient mGoogleApiClient;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NewsFeedActivity.class);
        return intent;
    }

    public static Intent newIntentSaveNews(Context context, int notificationId) {
        Intent intent = new Intent(context, NewsFeedActivity.class);
        intent.putExtra(IE_NOTIFICATION_ID, notificationId);
        intent.putExtra(IE_LAUNCH_ACTION, LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION);
        return intent;
    }

    public static Intent newIntentNotiBody(Context context) {
        Intent intent = new Intent(context, NewsFeedActivity.class);
        intent.putExtra(IE_LAUNCH_ACTION, LAUNCH_ACTION_TAP_NOTI_BODY);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this, this);

        initRemoteConfig();
        fetchRemoteConfig();

        tvScreenTitle.setText(getString(R.string.screen_title_newsfeed));
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mNewsFeedsAdapter = new NewsFeedsAdapter(getApplicationContext());
        rvNewsFeed.setAdapter(mNewsFeedsAdapter);

        rvNewsFeed.setEmptyView(vpEmptyNewsFeed);

        /*
        List<NewsFeedVO> newsFeed = NewsFeedModel.getInstance().getSampleNewsFeed(getApplicationContext());
        mNewsFeedsAdapter.setNewData(newsFeed);
        */

        swipeRefreshLayout.setRefreshing(true);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("155065313733-1lgn7qgb6teg7q7imb7butvd78md99oh.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int notificationId = bundle.getInt(IE_NOTIFICATION_ID);
            if (notificationId != 0) {
                dismissNotification(notificationId);
            }

            int launchAction = bundle.getInt(IE_LAUNCH_ACTION);
            switch (launchAction) {
                case LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION:
                    Snackbar.make(rvNewsFeed, "\"Save News\" from notification action!", Snackbar.LENGTH_SHORT).show();
                    break;
                case LAUNCH_ACTION_TAP_NOTI_BODY:
                    Snackbar.make(rvNewsFeed, "Tapped notification body", Snackbar.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processGoogleSignInResult(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        NewsFeedModel.getInstance().loadNewsFeed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @OnClick(R.id.fab)
    public void onTapFab(View view) {
        if (NewsFeedModel.getInstance().isUserSignIn()) {
            //startActivity(AddNewsActivity.newIntent(getApplicationContext()));
            startAddNewsActivity();
        } else {
            // Not signed in, launch the Sign In activity
            Snackbar.make(rvNewsFeed, "You need to sign with Google to publish in MM-News.", Snackbar.LENGTH_INDEFINITE).setAction("Sign-In", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInWithGoogle();
                }
            }).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsFeedLoaded(FirebaseEvents.NewsFeedLoadedEvent event) {
        Log.d(FirebaseApp.TAG, "onNewsFeedLoaded - " + event.getNewsFeed().size());
        mNewsFeedsAdapter.setNewData(event.getNewsFeed());
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void processGoogleSignInResult(GoogleSignInResult signInResult) {
        if (signInResult.isSuccess()) {
            // Google Sign-In was successful, authenticate with Firebase
            GoogleSignInAccount account = signInResult.getSignInAccount();
            NewsFeedModel.getInstance().authenticateUserWithGoogleAccount(account, new NewsFeedModel.SignInWithGoogleAccountDelegate() {
                @Override
                public void onSuccessSignIn(GoogleSignInAccount signInAccount) {
                    startAddNewsActivity();
                }

                @Override
                public void onFailureSignIn(String msg) {

                }
            });
        } else {
            // Google Sign-In failed
            Log.e(FirebaseApp.TAG, "Google Sign-In failed.");
            Snackbar.make(rvNewsFeed, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show();
        }
    }

    private void startAddNewsActivity() {
        startActivity(AddNewsActivity.newIntent(getApplicationContext()));
    }

    private void dismissNotification(int notificationID) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.cancel(notificationID);
    }

    private void initRemoteConfig() {
        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(FirebaseAppConstants.RC_NEWS_FEED_LAYOUT, 1);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
    }

    private void fetchRemoteConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via
                        // FirebaseRemoteConfig get<type> calls.
                        mFirebaseRemoteConfig.activateFetched();
                        applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // There has been an error fetching the config
                        Log.d(FirebaseApp.TAG, "Error fetching config: " +
                                e.getMessage());
                        applyRetrievedLengthLimit();
                    }
                });
    }

    private void applyRetrievedLengthLimit() {
        Long newsFeedLayout = mFirebaseRemoteConfig.getLong(FirebaseAppConstants.RC_NEWS_FEED_LAYOUT);
        mNewsFeedsAdapter.setNewsFeedLayout(newsFeedLayout.intValue());
        Log.d(FirebaseApp.TAG, "newsFeedLayout : " + newsFeedLayout);
    }
}
