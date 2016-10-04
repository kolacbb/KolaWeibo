package io.github.kolacbb.kolaweibo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.User;
import io.github.kolacbb.kolaweibo.widget.WBTextView;

/**
 * Created by zhangli on 2016/10/3.
 */

public class FriendTimeLineAdapter extends BaseSwipeLoadingAdapter<FriendTimeLine>
        implements View.OnClickListener{
    private static String ATTITUDES_TEXT;
    private static String COMMENTS_TEXT;
    private static String REPOSTS_TEXT;

    private OnItemClickListener mItemClickListener;
    private OnUserClickListener mUserClickListener;
    private OnLikeClickListener mLikeClickListener;
    private OnCommentClickListener mCommentClickListener;
    private OnRepostClickListener mRepostClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_line_view, parent, false);

        // init string
        ATTITUDES_TEXT = parent.getContext().getResources().getString(R.string.attitudes_text) + " ";
        COMMENTS_TEXT = parent.getContext().getResources().getString(R.string.comments_text) + " ";
        REPOSTS_TEXT = parent.getContext().getResources().getString(R.string.reposts_text);

        return new TimeLineVH(v);
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder h, int position) {
        TimeLineVH holder = (TimeLineVH) h;
        FriendTimeLine t = get(position);
        // set user bar
        User user = t.getUser();
        holder.userName.setText(t.getUser().getName());
        //holder.wbCreateTime.setText(t.getCreatedAt());
        //holder.wbSource.setText(t.getSource());

        // set content
        holder.wbContent.setText(t.getText());

        // set time line status bar
        String commentText = t.getCommentsCount() + COMMENTS_TEXT;
        holder.commentsCount.setText(commentText);
        String attitudeText = t.getAttitudesCount() + ATTITUDES_TEXT;
        holder.attitudesCount.setText(attitudeText);
        String repostsText = t.getRepostsCount() + REPOSTS_TEXT;
        holder.repostsCount.setText(repostsText);

        // init listener

        holder.attitudesCount.setTag(position);
        holder.commentsCount.setTag(position);
        holder.repostsCount.setTag(position);
        holder.userImg.setTag(position);
        holder.userName.setTag(position);

        holder.attitudesCount.setOnClickListener(this);
        holder.commentsCount.setOnClickListener(this);
        holder.repostsCount.setOnClickListener(this);
        holder.userImg.setOnClickListener(this);
        holder.userName.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.attitudes_count:
                if (mLikeClickListener != null) {
                    mLikeClickListener.onLikeClick(v, position);
                }
                break;
            case R.id.comments_count:
                if (mCommentClickListener != null) {
                    mCommentClickListener.onCommentClick(v, position);
                }
                break;
            case R.id.reposts_count:
                if (mRepostClickListener != null) {
                    mRepostClickListener.onRepostClick(v, position);
                }
                break;
            case R.id.user_img:
            case R.id.user_name:
                if (mUserClickListener != null) {
                    mUserClickListener.onUserClick(v, position);
                }
                break;
        }
    }

    public long getFirstWBId() {
        if (getData().size() > 0) {
            return get(0).getId();
        }
        return 0;
    }

    public long getLastWBId() {
        if (getData().size() > 0) {
            return get(getData().size() - 1).getId();
        }
        return 0;
    }



    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setUserClickListener(OnUserClickListener mUserClickListener) {
        this.mUserClickListener = mUserClickListener;
    }

    public void setLikeClickListener(OnLikeClickListener mLikeClickListener) {
        this.mLikeClickListener = mLikeClickListener;
    }

    public void setCommentClickListener(OnCommentClickListener mCommentClickListener) {
        this.mCommentClickListener = mCommentClickListener;
    }

    public void setRepostClickListener(OnRepostClickListener mRepostClickListener) {
        this.mRepostClickListener = mRepostClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnUserClickListener {
        void onUserClick(View view, int position);
    }

    public interface OnCommentClickListener {
        void onCommentClick(View view, int position);
    }

    public interface OnLikeClickListener {
        void onLikeClick(View view, int position);
    }

    public interface OnRepostClickListener {
        void onRepostClick(View view, int position);
    }

    private static class TimeLineVH extends RecyclerView.ViewHolder {
        // user bar ui component
        ImageView userImg;
        TextView userName;
        TextView wbCreateTime;
//        TextView wbSource;

        // wb content
        WBTextView wbContent;

        // time line status bar ui component
        TextView attitudesCount;
        TextView commentsCount;
        TextView repostsCount;

        public TimeLineVH(View itemView) {
            super(itemView);
            userImg = (ImageView) itemView.findViewById(R.id.user_img);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            wbCreateTime = (TextView) itemView.findViewById(R.id.wb_create_time);
//            wbSource = (TextView) itemView.findViewById(R.id.wb_source);

            wbContent = (WBTextView) itemView.findViewById(R.id.wb_content);

            attitudesCount = (TextView) itemView.findViewById(R.id.attitudes_count);
            commentsCount = (TextView) itemView.findViewById(R.id.comments_count);
            repostsCount = (TextView) itemView.findViewById(R.id.reposts_count);
        }
    }
}
