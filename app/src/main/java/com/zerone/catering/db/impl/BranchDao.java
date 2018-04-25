package com.zerone.catering.db.impl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.zerone.catering.db.abs.AbstractDao;
import com.zerone.catering.domain.Branch;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class BranchDao extends AbstractDao {

    public BranchDao(Context context) {
        super(context);
    }

    /**
     * 插入数据
     * @param branch  分店实体类
     * @throws Exception
     */
    public void add(Branch branch) throws Exception {

//           Log.i("URL",branch.toString());
        try {
            db = baseDao.getWritableDatabase();
            String sql = "insert into branch (id, ztdname,thumb) values (?, ?, ?)";
            String[] param = new String[] { branch.getId(),branch.getZtdname(),branch.getThumb()};
            db.execSQL(sql, param);
//            Log.i("URL","插入成功");
        } catch (Exception e) {
            throw new Exception("插入数据失败", e);
        } finally {
            db.close();
        }
    }


    public Branch getBranch(String id) throws Exception {

        Cursor cur = null;
        try {
            db = baseDao.getReadableDatabase();
            String[] field = new String[] { id };
//            cur = db.query("branch", field, null, null, null,null,null);
            cur = db.rawQuery("select * from branch where id="+field[0], null);
            int count = cur.getCount();
            if(count == 0){
                return null;
            }
            cur.moveToFirst();
            Branch branch = new Branch ();
            branch.setId(cur.getString(cur.getColumnIndex("id")));
            branch.setZtdname(cur.getString(cur.getColumnIndex("ztdname")));
            branch.setThumb(cur.getString(cur.getColumnIndex("thumb")));
            return  branch;
        } catch (Exception e) {
            throw new Exception("获取失败", e);
        } finally {
            cur.close();
            db.close();
        }
    }

    /**
     * 清空表中数据
     * @throws Exception
     */
    public void del() throws Exception{
        try {
            db = baseDao.getWritableDatabase();
            String sql = "delete from branch";
            db.execSQL(sql);
        } catch (Exception e) {
            throw new Exception("清空失败", e);
        } finally {
            db.close();
        }
    }
}
