package xyz.aungpyaephyo.padc.firebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.data.models.NewsFeedModel;

/**
 * Created by aung on 8/18/17.
 */

public class AddNewsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_news)
    EditText etNewsContent;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNewsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        ButterKnife.bind(this, this);

        toolbar.setTitle(getString(R.string.screen_title_add_news));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_add_news)
    public void onTapAddNews(View view) {
        String newsContent = etNewsContent.getText().toString();
        if (TextUtils.isEmpty(newsContent)) {
            etNewsContent.setError("Need news content to publish.");
        } else {
            NewsFeedModel.getInstance().addNews(newsContent);
            etNewsContent.setText("");
            onBackPressed();
        }
    }
}
