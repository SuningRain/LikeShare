package sunningrain.github.likeshare.ui.like;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.DraftsAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.bean.DraftsBean;
import sunningrain.github.likeshare.util.sqlite.SQLiteHelper;

public class LikeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected int getContentView() {
        return R.layout.activity_like;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle("我的点赞");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        List<DraftsBean> list = new ArrayList<>();
        Cursor cursor = db.query(SQLiteHelper.TB_LIKE,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            DraftsBean bean = new DraftsBean();
            String pbPic = cursor.getString(cursor.getColumnIndex("pb_pic"));
            String pbContent = cursor.getString(cursor.getColumnIndex("pb_content"));
            Log.i("zy",pbPic);
            bean.setPublishPic(pbPic);
            bean.setPublishText(pbContent);
            list.add(bean);
        }
        cursor.close();
        DraftsAdapter adapter = new DraftsAdapter(this,list);
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
