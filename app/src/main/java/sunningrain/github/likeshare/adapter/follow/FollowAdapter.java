package sunningrain.github.likeshare.adapter.follow;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import redis.clients.jedis.Jedis;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.ui.detail.DetailActivity;
import sunningrain.github.likeshare.ui.userinfo.PersonalCenterActivity;
import sunningrain.github.likeshare.util.SharedUtil;
import sunningrain.github.likeshare.util.sqlite.SQLiteHelper;
import sunningrain.github.likeshare.widget.SquareImageView;

/**
 * Created by 27837 on  2019/5/4.
 * 主界面我的关注页面
 */
public class FollowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FollowResponse.DataBean> mResponseList;
    public static final String TAG = "FollowAdapter";
    private boolean liked = false;

    public FollowAdapter(Context context, List<FollowResponse.DataBean> responseList) {
        this.mContext = context;
        this.mResponseList = responseList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_publish_user;
        CircleImageView cciv_user_pic;
        TextView tv_publish_user;
        TextView tv_publish_time;
        TextView tv_publish_text;
        SquareImageView iv_publish_pic;
        LinearLayout ll_publish_content;
        ImageView iv_like;
        ImageView iv_report;
        ImageView iv_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_publish_user = (LinearLayout) itemView.findViewById(R.id.ll_publish_user);
            cciv_user_pic = (CircleImageView) itemView.findViewById(R.id.cciv_user_pic);
            tv_publish_user = (TextView) itemView.findViewById(R.id.tv_publish_user);
            tv_publish_time = (TextView) itemView.findViewById(R.id.tv_publish_time);
            tv_publish_text = (TextView) itemView.findViewById(R.id.tv_publish_text);
            iv_publish_pic = (SquareImageView) itemView.findViewById(R.id.iv_publish_pic);
            ll_publish_content = (LinearLayout) itemView.findViewById(R.id.ll_publish_content);
            iv_like = (ImageView) itemView.findViewById(R.id.iv_like);
            iv_report = (ImageView) itemView.findViewById(R.id.iv_repost);
            iv_comment = (ImageView) itemView.findViewById(R.id.iv_comment);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.ll_publish_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PersonalCenterActivity.class);
                intent.putExtra(PersonalCenterActivity.FROM, TAG);
                intent.putExtra(PersonalCenterActivity.DATA, mResponseList.get(i).getUId());
                mContext.startActivity(intent);
            }
        });
        holder.ll_publish_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDetailActivity(i);
            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                liked = !liked;
                if (liked) {
                    holder.iv_like.setBackgroundResource(R.drawable.ic_liked);
                    addLike(i);
                } else {
                    holder.iv_like.setBackgroundResource(R.drawable.ic_like);
                }
            }
        });
        holder.iv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                Toast.makeText(mContext, "转发", Toast.LENGTH_SHORT).show();
            }
        });
        holder.iv_comment.setOnClickListener(view -> startDetailActivity(i));
        Glide.with(mContext).load(Const.BASE_PHOTO_URL + mResponseList.get(i).getUPhoto())
                .into(holder.cciv_user_pic);
        holder.tv_publish_user.setText(mResponseList.get(i).getUName());
        holder.tv_publish_time.setText(mResponseList.get(i).getPublishTime());
        holder.tv_publish_text.setText(mResponseList.get(i).getPublishText());
        Glide.with(mContext).load(Const.BASE_PICTURE_URL + mResponseList.get(i).getPublishPic())
                .into(holder.iv_publish_pic);
    }

    private void addLike(int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Jedis jedis = new Jedis("182.254.180.91",6666);
                    jedis.auth("zyfrom1996");
                    jedis.set("hello","world");
                    Log.e("zy","连接成功"+jedis.get("hello"));
                }catch (Exception e) {
                    Log.e("zy", "连接失败");
                    e.printStackTrace();
                }
            }
        }).start();
        SQLiteHelper sqLiteHelper = new SQLiteHelper(mContext);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("u_id", SharedUtil.read(Const.Auth.USER_ID, 0));
        contentValues.put("pb_id", mResponseList.get(i).getPublishId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        contentValues.put("lk_time", date);
        contentValues.put("pb_pic", mResponseList.get(i).getPublishPic());
        contentValues.put("pb_content", mResponseList.get(i).getPublishText());
        try {
            db.insert(SQLiteHelper.TB_LIKE, null, contentValues);
            Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "点赞失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void startDetailActivity(int i) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.FROM, DetailActivity.FollowAdapter);
        intent.putExtra(DetailActivity.DATA, mResponseList.get(i));
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }
}
