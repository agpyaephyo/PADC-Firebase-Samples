package xyz.aungpyaephyo.padc.firebase.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.data.vo.NewsFeedVO;
import xyz.aungpyaephyo.padc.firebase.views.holders.NewsFeedViewHolder;

/**
 * Created by aung on 8/18/17.
 */

public class NewsFeedsAdapter extends BaseRecyclerAdapter<NewsFeedViewHolder, NewsFeedVO> {

    public NewsFeedsAdapter(Context context) {
        super(context);
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_holder_news_feed, parent, false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }
}
