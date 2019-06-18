package sunningrain.github.likeshare.adapter.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import sunningrain.github.likeshare.R;

/**
 * Created by 27837 on  2019/4/30.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<File> mFileList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public AlbumAdapter(Context context, List<File> fileList){
        mContext = context;
        mFileList = fileList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_album,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        File file = mFileList.get(i);
        Glide.with(mContext).load(file)
                .placeholder(mContext.getResources().getDrawable(R.drawable.default_picture))
                .into(viewHolder.mImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(i,mFileList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public ViewHolder(View view){
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.iv_album_picture);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int postion ,File file);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}
