package sunningrain.github.likeshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.bean.response.PublishResponse;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.ui.detail.DetailActivity;
import sunningrain.github.likeshare.widget.SquareImageView;

/**
 * Created by 27837 on  2019/5/4.
 */
public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.ViewHolder> {

    private Context mContext;
    private List<PublishResponse.DataBean> mResponseList;
    private String userPhoto;
    private String userName;
    private int userId = 0;

    public PublishAdapter(Context context, List<PublishResponse.DataBean> responseList, String userPhoto, String userName) {
        this.mContext = context;
        this.mResponseList = responseList;
        this.userName = userName;
        this.userPhoto = userPhoto;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_publish_user;
        CircleImageView cciv_user_pic;
        TextView tv_publish_user;
        TextView tv_publish_time;
        TextView tv_publish_text;
        SquareImageView iv_publish_pic;
        LinearLayout ll_publish_content;
        LinearLayout ll_work;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_publish_user = (LinearLayout) itemView.findViewById(R.id.ll_publish_user);
            cciv_user_pic = (CircleImageView) itemView.findViewById(R.id.cciv_user_pic);
            tv_publish_user = (TextView) itemView.findViewById(R.id.tv_publish_user);
            tv_publish_time = (TextView) itemView.findViewById(R.id.tv_publish_time);
            tv_publish_text = (TextView) itemView.findViewById(R.id.tv_publish_text);
            iv_publish_pic = (SquareImageView) itemView.findViewById(R.id.iv_publish_pic);
            ll_publish_content = (LinearLayout) itemView.findViewById(R.id.ll_publish_content);
            ll_work = (LinearLayout) itemView.findViewById(R.id.ll_work);
        }
    }

    @NonNull
    @Override
    public PublishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublishAdapter.ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(Const.BASE_PHOTO_URL + userPhoto)
                .placeholder(LikeShare.getContext().getResources().getDrawable(R.drawable.default_picture))
                .into(viewHolder.cciv_user_pic);
        viewHolder.tv_publish_user.setText(userName);
        viewHolder.tv_publish_time.setText(mResponseList.get(i).getPublishTime());
        viewHolder.tv_publish_text.setText(mResponseList.get(i).getPublishText());
        Glide.with(mContext).load(Const.BASE_PICTURE_URL + mResponseList.get(i).getPublishPic())
                .into(viewHolder.iv_publish_pic);
        viewHolder.ll_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FollowResponse.DataBean work = new FollowResponse.DataBean();
                work.setPublishId(mResponseList.get(i).getPublishId());
                work.setPublishPic(mResponseList.get(i).getPublishPic());
                work.setPublishTime(mResponseList.get(i).getPublishTime());
                work.setPublishText(mResponseList.get(i).getPublishText());
                work.setUId(userId);
                work.setUName(userName);
                work.setUPhoto(userPhoto);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.FROM,DetailActivity.FROM_PUBLISH_ADAPTER);
                intent.putExtra(DetailActivity.DATA,work);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }

    public void refreshData(int userId, String userName, String userPhoto, List<PublishResponse.DataBean> list) {
        mResponseList.clear();
        mResponseList.addAll(list);
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        notifyDataSetChanged();
    }
}
