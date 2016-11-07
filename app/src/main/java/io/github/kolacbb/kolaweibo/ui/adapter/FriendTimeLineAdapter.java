package io.github.kolacbb.kolaweibo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.User;
import io.github.kolacbb.kolaweibo.util.CircleTransform;
import io.github.kolacbb.kolaweibo.widget.WBImagesView;
import io.github.kolacbb.kolaweibo.widget.WBTextView;

/**
 * Created by zhangli on 2016/10/3.
 */

public class FriendTimeLineAdapter extends BaseSwipeLoadingAdapter<FriendTimeLine>
        implements View.OnClickListener{
    private Context mCtx;

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
        mCtx = parent.getContext();
        View v = LayoutInflater.from(mCtx)
                .inflate(R.layout.time_line_view, parent, false);

        // init string
        ATTITUDES_TEXT = " " + mCtx.getResources().getString(R.string.attitudes_text) + " ";
        COMMENTS_TEXT = " " + mCtx.getResources().getString(R.string.comments_text) + " ";
        REPOSTS_TEXT = " " + mCtx.getResources().getString(R.string.reposts_text);
        return new TimeLineVH(v);
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder h, int position) {
        TimeLineVH holder = (TimeLineVH) h;
        FriendTimeLine t = get(position);

        // set user bar
        User user = t.getUser();
        Picasso.with(mCtx)
                .load(user.getAvatarLarge())
                .resize(48, 48)
                .transform(new CircleTransform())
                .into(holder.userImg);


        holder.userName.setText(t.getUser().getName());
        //holder.wbCreateTime.setText(t.getCreatedAt());
        //holder.wbSource.setText(t.getSource());

        // set content
        holder.wbContent.setText(t.getText());

        // set images
        if (t.getPicUrls() != null && t.getPicUrls().size() != 0) {
            String[] urls = null;
            urls = new String[t.getPicUrls().size()];
            for (int i = 0; i < t.getPicUrls().size(); i++) {
                urls[i] = t.getPicUrls().get(i).getThumbnail_pic();
            }
            holder.wbImagesView.setImages(mCtx, urls);

        } else {
            holder.wbImagesView.setVisibility(View.INVISIBLE);
        }


        // set time line status bar
        setTimeLineStatus(holder.commentsCount, t.getCommentsCount(), COMMENTS_TEXT);
        setTimeLineStatus(holder.attitudesCount, t.getAttitudesCount(), ATTITUDES_TEXT);
        setTimeLineStatus(holder.repostsCount, t.getRepostsCount(), REPOSTS_TEXT);

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

    private void setTimeLineStatus(TextView tv, long count, String text) {
        tv.setVisibility(View.VISIBLE);
        if (count == 0) {
            tv.setVisibility(View.GONE);
        } else if (count < 1000) {
            String str = count + text;
            tv.setText(str);
        } else {
            String str = count / 1000 + "k" + text;
            tv.setText(str);
        }
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
        // 微博图片
        //GridLayout wbImagesView;
        WBImagesView wbImagesView;
//        private ImageView[] mImages;

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
            wbImagesView = (WBImagesView) itemView.findViewById(R.id.wb_images);

//            mImages = new ImageView[9];
//            mImages[0] = (ImageView) itemView.findViewById(R.id.image0);
//            mImages[1] = (ImageView) itemView.findViewById(R.id.image1);
//            mImages[2] = (ImageView) itemView.findViewById(R.id.image2);
//            mImages[3] = (ImageView) itemView.findViewById(R.id.image3);
//            mImages[4] = (ImageView) itemView.findViewById(R.id.image4);
//            mImages[5] = (ImageView) itemView.findViewById(R.id.image5);
//            mImages[6] = (ImageView) itemView.findViewById(R.id.image6);
//            mImages[7] = (ImageView) itemView.findViewById(R.id.image7);
//            mImages[8] = (ImageView) itemView.findViewById(R.id.image8);
//


            attitudesCount = (TextView) itemView.findViewById(R.id.attitudes_count);
            commentsCount = (TextView) itemView.findViewById(R.id.comments_count);
            repostsCount = (TextView) itemView.findViewById(R.id.reposts_count);
        }
    }
}
