package sunningrain.github.likeshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.DraftsBean;

/**
 * Created by 27837 on  2019/5/5.
 */
public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.ViewHolder> {
    private Context mContext;
    private List<DraftsBean> mDraftsBeanList;
    private OnItemClickListener mItemClickListener;
    public DraftsAdapter(Context context,List<DraftsBean> draftsBeans){
        this.mContext = context;
        this.mDraftsBeanList = draftsBeans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_drafts,viewGroup,false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mDraftsBeanList.get(i).getPublishPic()).into(viewHolder.iv_publish_pic);
        viewHolder.tv_publish_text.setText(mDraftsBeanList.get(i).getPublishText());
        viewHolder.tv_publish_time.setText(mDraftsBeanList.get(i).getPublishTime());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDraftsBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_publish_pic;
        TextView tv_publish_text;
        TextView tv_publish_time;
        TextView tv_draft;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_publish_pic = (ImageView)itemView.findViewById(R.id.iv_publish_pic);
            tv_publish_text = (TextView)itemView.findViewById(R.id.tv_publish_text);
            tv_publish_time = (TextView)itemView.findViewById(R.id.tv_publish_time);
            tv_draft = (TextView)itemView.findViewById(R.id.tv_draft);
        }
    }

    public void refresh(List<DraftsBean> draftsBeanList){
        mDraftsBeanList.clear();
        mDraftsBeanList.addAll(draftsBeanList);
        notifyDataSetChanged();
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mItemClickListener = onItemClickListener;
    }
}
