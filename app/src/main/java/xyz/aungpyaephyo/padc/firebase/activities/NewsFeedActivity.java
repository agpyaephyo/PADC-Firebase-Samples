package xyz.aungpyaephyo.padc.firebase.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.adapters.NewsFeedsAdapter;
import xyz.aungpyaephyo.padc.firebase.components.rvset.SmartRecyclerView;
import xyz.aungpyaephyo.padc.firebase.views.pods.EmptyViewPod;

public class NewsFeedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_news_feed)
    SmartRecyclerView rvNewsFeed;

    @BindView(R.id.vp_empty_news_feed)
    EmptyViewPod vpEmptyNewsFeed;

    private NewsFeedsAdapter mNewsFeedsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this, this);

        toolbar.setTitle(getString(R.string.launcher_name));
        setSupportActionBar(toolbar);

        mNewsFeedsAdapter = new NewsFeedsAdapter(getApplicationContext());
        rvNewsFeed.setAdapter(mNewsFeedsAdapter);

        rvNewsFeed.setEmptyView(vpEmptyNewsFeed);
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

    @OnClick(R.id.fab)
    public void onTapFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
