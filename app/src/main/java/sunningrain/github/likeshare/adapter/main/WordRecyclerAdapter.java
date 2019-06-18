package sunningrain.github.likeshare.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.WorksBean;

/**
 * Created by 27837 on  2019/5/3.
 */
public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private List<WorksBean> mWorksBeanList;
    public WordRecyclerAdapter(Context context,List<WorksBean> worksBeanList){
        this.mContext = context;
        this.mWorksBeanList = worksBeanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_works,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mWorksBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_work;
        TextView tv_work_description;
        CircleImageView tiv_user_pic;
        TextView tv_user_name;
        public ViewHolder(View view){
            super(view);
            iv_work = (ImageView)view.findViewById(R.id.iv_work);
            tv_work_description = (TextView)view.findViewById(R.id.tv_work_description);
            tiv_user_pic = (CircleImageView)view.findViewById(R.id.civ_user_pic);
            tv_user_name = (TextView)view.findViewById(R.id.tv_user_name);
        }
    }
}
