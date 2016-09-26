package io.github.kolacbb.kolaweibo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.kolacbb.kolaweibo.R;
import io.github.kolacbb.kolaweibo.api.models.FriendTimeLine;
import io.github.kolacbb.kolaweibo.api.models.User;
import io.github.kolacbb.kolaweibo.widget.WBTextView;

/**
 * Created by Kola on 2016/9/22.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {
    private List<FriendTimeLine> mTimeLines;

    private static String ATTITUDES_TEXT;
    private static String COMMENTS_TEXT;
    private static String REPOSTS_TEXT;

    public TimeLineAdapter(List<FriendTimeLine> timeLines) {
        mTimeLines = timeLines;
    }

    public void setData(List<FriendTimeLine> timeLines) {
        mTimeLines = timeLines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_line_view, parent, false);

        // init string
        ATTITUDES_TEXT = parent.getContext().getResources().getString(R.string.attitudes_text) + " ";
        COMMENTS_TEXT = parent.getContext().getResources().getString(R.string.comments_text) + " ";
        REPOSTS_TEXT = parent.getContext().getResources().getString(R.string.reposts_text);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FriendTimeLine t = mTimeLines.get(position);
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
    }

    @Override
    public int getItemCount() {
        return mTimeLines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View itemView) {
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
