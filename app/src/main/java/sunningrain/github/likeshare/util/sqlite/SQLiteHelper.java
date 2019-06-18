package sunningrain.github.likeshare.util.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Created by 27837 on  2019/5/24.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_SHARE = "db_share";
    public static final int DB_VERSION = 1;
    public static final String TB_LIKE = "tb_like";
    private static final String CREATE_TB_LIKE = "create table " + TB_LIKE + "("
            + "id integer primary key autoincrement,"//id自增
            + "pb_id integer not null,"//作品id
            + "u_id integer not null,"//点赞人
            + "lk_time varchar(20) not null,"//点赞时间
            + "pb_pic varchar(50) not null,"
            + "pb_content varchar(100) not null"
            + ");";
    public SQLiteHelper(@Nullable Context context) {
        super(context,DB_SHARE,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TB_LIKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i){
            case 1:
                break;
                default:break;
        }
    }
}
