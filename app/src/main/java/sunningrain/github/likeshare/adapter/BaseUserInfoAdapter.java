package sunningrain.github.likeshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/5/8.
 */
public class BaseUserInfoAdapter extends RecyclerView.Adapter<BaseUserInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<BaseUserInfo> mBaseUserInfoList;

    public BaseUserInfoAdapter(Context context,List<BaseUserInfo> baseUserInfos){
        this.mContext = context;
        this.mBaseUserInfoList = baseUserInfos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_base_follow,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(Const.User.USER_PIC+mBaseUserInfoList.get(i).getUserPhoto()).into(viewHolder.civ_user_pic);
        viewHolder.tv_user_name.setText(mBaseUserInfoList.get(i).getUserName());
        viewHolder.tv_user_signature.setText(mBaseUserInfoList.get(i).getUserSignat());
    }

    @Override
    public int getItemCount() {
        return mBaseUserInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_user_pic;
        TextView tv_user_name;
        TextView tv_user_signature;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_user_pic = (CircleImageView) itemView.findViewById(R.id.civ_user_pic);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_signature = (TextView) itemView.findViewById(R.id.tv_user_signature);
        }
    }
}
