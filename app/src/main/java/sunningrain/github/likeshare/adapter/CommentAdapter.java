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

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.CommentBean;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.ui.userinfo.PersonalCenterActivity;

/**
 * Created by 27837 on  2019/5/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private List<CommentBean> mCommentBeanList;

    public CommentAdapter(Context context,List<CommentBean> commentBeans){
        mContext = context;
        mCommentBeanList = commentBeans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext)
                .load(Const.BASE_PHOTO_URL+ mCommentBeanList.get(i).getUserPic())
                .placeholder(LikeShare.getContext().getResources().getDrawable(R.drawable.default_picture))
                .into(viewHolder.civ_user_pic);
        viewHolder.tv_user_name.setText(mCommentBeanList.get(i).getUserName());
        viewHolder.tv_publish_time.setText(mCommentBeanList.get(i).getCommentTime());
        viewHolder.tv_comment_content.setText(mCommentBeanList.get(i).getCommentContent());
        viewHolder.ll_user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PersonalCenterActivity.class);
                intent.putExtra(PersonalCenterActivity.FROM,PersonalCenterActivity.FROM_COMMENT_ADAPTER);
                intent.putExtra(PersonalCenterActivity.DATA,mCommentBeanList.get(i).getuId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommentBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_user_pic;
        TextView tv_user_name;
        TextView tv_publish_time;
        TextView tv_comment_content;
        LinearLayout ll_user_center;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            civ_user_pic =(CircleImageView) itemView.findViewById(R.id.civ_user_pic);
            tv_user_name =(TextView) itemView.findViewById(R.id.tv_user_name);
            tv_publish_time =(TextView) itemView.findViewById(R.id.tv_publish_time);
            tv_comment_content =(TextView) itemView.findViewById(R.id.tv_comment_content);
            ll_user_center = (LinearLayout) itemView.findViewById(R.id.ll_user_center);
        }
    }
}
