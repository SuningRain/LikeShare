package sunningrain.github.likeshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.response.WorldAndPopularResponse;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.ui.detail.DetailActivity;
import sunningrain.github.likeshare.ui.userinfo.PersonalCenterActivity;

/**
 * Created by 27837 on  2019/5/7.
 */
public class WordAndPopularAdapter extends RecyclerView.Adapter<WordAndPopularAdapter.ViewHolder>{
    private Context mContext;
    private List<WorldAndPopularResponse.DataBean> mWorldRespons;
    private boolean likeFlag = false;

    public WordAndPopularAdapter(Context context, List<WorldAndPopularResponse.DataBean> worldRespons) {
        this.mContext = context;
        this.mWorldRespons = worldRespons;
    }

    @NonNull
    @Override
    public WordAndPopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_world_popular, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordAndPopularAdapter.ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(Const.BASE_PICTURE_URL + mWorldRespons.get(i).getPublishPic())
                .into(viewHolder.iv_publish_pic);
        viewHolder.tv_publish_text.setText(mWorldRespons.get(i).getPublishText());
        Glide.with(mContext).load(Const.BASE_PHOTO_URL + mWorldRespons.get(i).getUPhoto())
                .placeholder(LikeShare.getContext().getResources().getDrawable(R.drawable.default_picture))
                .into(viewHolder.civ_user_pic);
        viewHolder.tv_user_name.setText(mWorldRespons.get(i).getUName());
        setListener(viewHolder, i);
    }

    private void setListener(@NonNull WordAndPopularAdapter.ViewHolder viewHolder, int i) {
        viewHolder.ll_publish_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.FROM,DetailActivity.WORD_AND_POPULAR_ADAPTER);
                intent.putExtra(DetailActivity.DATA,mWorldRespons.get(i));
                mContext.startActivity(intent);
            }
        });
        viewHolder.ll_user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,PersonalCenterActivity.class);
                intent.putExtra(PersonalCenterActivity.FROM,PersonalCenterActivity.WORD_AND_POPULAR_ADAPTER);
                intent.putExtra(PersonalCenterActivity.DATA,mWorldRespons.get(i).getUId());
                mContext.startActivity(intent);
            }
        });
        viewHolder.ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeFlag = !likeFlag;
                if (likeFlag){
                    viewHolder.iv_like.setBackgroundResource(R.drawable.ic_liked);
                }else {
                    viewHolder.iv_like.setBackgroundResource(R.drawable.ic_like);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorldRespons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_publish_pic;
        TextView tv_publish_text;
        CircleImageView civ_user_pic;
        TextView tv_user_name;
        LinearLayout ll_publish_content;
        LinearLayout ll_user_center;
        LinearLayout ll_like;
        ImageView iv_like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_publish_pic = itemView.findViewById(R.id.iv_publish_pic);
            tv_publish_text = itemView.findViewById(R.id.tv_publish_text);
            civ_user_pic = itemView.findViewById(R.id.civ_user_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            ll_publish_content = itemView.findViewById(R.id.ll_publish_content);
            ll_user_center = itemView.findViewById(R.id.ll_user_center);
            ll_like = itemView.findViewById(R.id.ll_like);
            iv_like = itemView.findViewById(R.id.iv_like);
        }
    }
}
