package com.honganjk.ynybzbizfood.mode.javabean.store.home.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

import com.honganjk.ynybzbizfood.mode.javabean.store.home.db.SearchHistorySQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by anynew on 2017/4/10.
 */

public class Mydao {
    private static final String TAG = "Mydao";
    private SearchHistorySQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private SimpleCursorAdapter adapter;

    private Mydao(){}

    public static Mydao mydao;

    public static synchronized   Mydao  getDao (){
        if (mydao==null){
            mydao=new Mydao();
        }
      return mydao;
    }

    public void setcontext(Context context){
        this.context=context;
        helper=new SearchHistorySQLiteOpenHelper(context);
    }


    /*插入数据*/
    public void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert OR IGNORE into history(name) values('" + tempName + "')");
        db.close();
    }


    /*模糊查询数据 并显示在ListView列表上*/
    public Cursor queryData(String tempName, ListView listView) {
        //模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from history where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象,装入模糊搜索的结果
//        adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
//                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

//        // 设置适配器
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        return cursor;
    }


    /*检查数据库中是否已经有该条记录*/
    public boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from history where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }


    /*清空数据*/
    public void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from history");
        db.close();
    }


    /**
     * 查询最新历史记录  最多5条
     */
    public ArrayList queryDatafromhistory(int maxItem){                                       //id
        Cursor cursor = helper.getReadableDatabase().rawQuery("select * from history  order by id desc limit 0,"+maxItem,null);
        ArrayList<String> strings=new ArrayList<>();

            while (cursor!=null&&cursor.moveToNext()){
                String id = cursor.getString(0);
                String columnName = cursor.getString(1);

                strings.add(columnName);
                    Log.e(TAG, "lishiid: "+id.toString());
                    Log.e(TAG, "queryDatafromhistory: "+columnName.toString());
        }
        Log.e(TAG, "queryDatafromhistory: 历史记录"+strings.toString() );
        return strings;
    }

    /**
     * 查询是否有数据
     */
    public ArrayList queryhaveData(){
        boolean isTableExist=true;
        ArrayList<String> strings=new ArrayList<>();
        int number=0;
        Cursor cursor = helper.getReadableDatabase().rawQuery("select count(*)  from sqlite_master where type='table' and name = 'history'",null);
        if (cursor.getCount()<=0) {
            isTableExist=false;
        }else {
            Cursor c = helper.getReadableDatabase().rawQuery("select * from history", null);
            number=c.getCount();

            //数据不为空
            if (number!=0){
                while (c!=null&&c.moveToNext()){
                    String id = c.getString(0);
                    String columnName = c.getString(1);

                    strings.add(columnName);
                    Log.e(TAG, "lishiid: "+id.toString());
                    Log.e(TAG, "queryDatafromhistory: "+columnName.toString());
                }
                Log.e(TAG, "queryDatafromhistory: 历史记录"+strings.toString() );
            }
            c.close();
        }

        cursor.close();

        if (isTableExist==false){
            return new ArrayList<>();
        }

        return strings;
    }


}



