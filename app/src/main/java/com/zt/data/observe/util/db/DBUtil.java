package com.zt.data.observe.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zt.data.observe.bean.db.TabProject;

import org.litepal.parser.LitePalAttr;
import org.litepal.parser.LitePalParser;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.util.error.ErrorUtil;

public class DBUtil {
    private static DBUtil batchDBUtil;

    public static DBUtil init(Context context) {
        if (batchDBUtil == null) {
            synchronized (DBUtil.class) {
                if (batchDBUtil == null) {
                    LitePalParser.parseLitePalConfiguration();
                    LitePalAttr mLitePalAttr = LitePalAttr.getInstance();
                    batchDBUtil = new DBUtil(context, mLitePalAttr.getDbName(), null, mLitePalAttr.getVersion());
                }
            }
        }
        return batchDBUtil;
    }

    private Context mContext;
    private String dbName;

    public DBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name+ ".db", factory, version);
        this.mContext = context;
        this.dbName = name;
    }

    public static List<TabProject> getProjectList() {
        List<TabProject> projects = new ArrayList<>();
        SQLiteDatabase db = Connector.getDatabase();
        String sql = "select  p.* ,count(m.id) as itemno from tabproject p left join tabinstrument m on m.projectid= p.id  group by p.id,m.projectid order by coalesce(p.updatedate,p.CREATEDATE) desc";
        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                TabProject project = new TabProject();
                project.setId(cursor.getLong(cursor.getColumnIndex("id")));
                project.setName(cursor.getString(cursor.getColumnIndex("name")));
                project.setCreateAddress(cursor.getString(cursor.getColumnIndex("createaddress")));
                project.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                project.count = cursor.getLong(cursor.getColumnIndex("itemno"));
                project.setCreateDate(cursor.getString(cursor.getColumnIndex("createdate")));
                project.setUpdateDate(cursor.getString(cursor.getColumnIndex("updatedate")));
                projects.add(project);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ErrorUtil.showError(e);
        } finally {
            db.endTransaction();
            db.close();
        }
        return projects;
    }

    public static void install(List<String> sqls) {
        SQLiteDatabase db = Connector.getDatabase();
        try {
            db.beginTransaction();
            for (String sql : sqls) {
                db.execSQL(sql);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ErrorUtil.showError(e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
