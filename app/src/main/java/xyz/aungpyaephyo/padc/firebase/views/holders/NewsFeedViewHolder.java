package xyz.aungpyaephyo.padc.firebase.views.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.components.loveanim.LoveAnimView;
import xyz.aungpyaephyo.padc.firebase.data.vo.NewsFeedVO;

/**
 * Created by aung on 8/18/17.
 */

public class NewsFeedViewHolder extends BaseViewHolder<NewsFeedVO> {

    private static final SimpleDateFormat sdfPostedDate = new SimpleDateFormat("MMM dd - hh:mma");

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_user_name)
    TextView tvUsername;

    @BindView(R.id.tv_posted_date)
    TextView tvPostedDate;

    @BindView(R.id.tv_feed_msg)
    TextView tvFeedMsg;

    @BindView(R.id.iv_feed_image)
    ImageView ivFeedImage;

    @BindView(R.id.tv_total_reactions)
    TextView tvTotalReactions;

    @BindView(R.id.iv_feed_love)
    LoveAnimView vFeedLove;

    public NewsFeedViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(NewsFeedVO data) {
        tvUsername.setText(data.getNewsAuthor().getUserName());

        long ms = data.getPosedDate() * 1000;
        Date postedDate = new Date(ms);
        tvPostedDate.setText(itemView.getContext().getString(R.string.tv_format_posted_date,
                sdfPostedDate.format(postedDate)));

        tvFeedMsg.setText(data.getContent());

        tvTotalReactions.setText(itemView.getContext().getString(R.string.format_total_reactions,
                data.getLikes().size(),
                data.getComments().size(),
                data.getSendToCount()));

        Glide.with(itemView.getContext())
                .load(data.getImage())
                .placeholder(R.drawable.placeholder_news_three)
                .error(R.drawable.placeholder_news_three)
                .into(ivFeedImage);

        Glide.with(itemView.getContext())
                .load(data.getNewsAuthor().getProfileImage())
                .centerCrop()
                .placeholder(R.mipmap.ic_news_from_people)
                .error(R.mipmap.ic_news_from_people)
                .into(ivProfile);
    }

    @Override
    public void onClick(View view) {

    }
}
