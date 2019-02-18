package com.ww7h.ww.common.apis.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GreenDaoManager {

    private static volatile GreenDaoManager instance = null;
    private AbstractDaoMaster daoMaster;
    private AbstractDaoSession daoSession;
    private DatabaseOpenHelper helper;
    private int openCount = 0;

    public static GreenDaoManager getInstance() {
        if(instance==null){
            synchronized(GreenDaoManager.class){
                if(instance==null){
                    instance=new GreenDaoManager ();
                }
            }
        }
        return instance;
    }

    private GreenDaoManager() {

    }


    public void initGreenDao (DatabaseOpenHelper helper,Class daoMasterClass) {
        this.helper = helper;
        try {
            this.daoMaster = (AbstractDaoMaster) daoMasterClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库操作对象
     * @param needWrite 是否需要写入操作
     * @return 数据库操作对象
     */
    private SQLiteDatabase getDB(boolean needWrite){
        openCount++;
        if(needWrite){
            return  helper.getWritableDatabase();
        }else{
            return helper.getReadableDatabase();
        }
    }

    /**
     * 关闭数据库
     * @param db 数据库对象
     */
    private void closeDB(SQLiteDatabase db){
        openCount--;
        if(openCount==0){
            db.close();
        }
    }

    /**
     * 关闭数据库
     */
    private void closeDB(){
        openCount--;
        if(openCount==0&&daoSession!=null){
            daoSession.getDatabase().close();
        }
    }

    private AbstractDaoSession getDaoSession() {
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = daoMaster.newSession();
        return daoSession;
    }

    public String getDbPath() {
        return helper.getWritableDatabase().getPath();
    }


    public <T> void insertOrReplace(T entity) {
        writeDB(0,entity,null,null,null);
    }

    public <T> void insertOrReplaceList(List<T> entityList){
        for (T t:entityList) {
            insertOrReplace(t);
        }
    }

    public void executeSql(String sql) {
        writeDB(1,null,null,null,sql);
    }

    public void executeSqls(List<String> sqlList, final String[]sqls) {
        writeDB(2,null,sqlList,sqls,null);
    }

    private synchronized  <T> void writeDB(int type, T entity, List<String> sqlList, final String[]sqls, String sql){
        openCount++;
        if(type==0){
            getDaoSession().insertOrReplace(entity);
        }else if(type==1){
            getDaoSession().getDatabase().execSQL(sql);
        }else if(type==2){
            executeSqlList(sqlList,sqls);
        }
        closeDB();
    }

    public <T> void queryOne(Class<T> clazz, String sql, GreenDaoCallBack.QueryCallBack<T> callBack) {
        final SQLiteDatabase db = getDB(false);
        List<T> tList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            T t;
            try {
                t = clazz.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
                continue;
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                continue;
            }
            String[] names = cursor.getColumnNames();
            for (String name : names) {
                int index = cursor.getColumnIndex(name);
                Field field = null;
                try {
                    String fieldName = name.equals("_id") ? "id" : name;
                    try {
                        field = clazz.getDeclaredField(fieldName);
                    } catch (Exception e) {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field f : fields) {
                            if (f.getName().toLowerCase().replace("_", "").equals(fieldName.toLowerCase().replace("_", ""))) {
                                field = f;
                                break;
                            }
                        }
                        if (field == null) {
                            continue;
                        }
                    }
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    switch (type.getName()) {
                        case "long":
                            field.set(t, cursor.getLong(index));
                            break;
                        case "java.lang.String":
                            field.set(t, cursor.getString(index));
                            break;
                        case "java.lang.Long":
                            field.set(t, cursor.getLong(index));
                            break;
                        case "int":
                            field.set(t, cursor.getInt(index));
                            break;
                        case "java.lang.Integer":
                            field.set(t, cursor.getInt(index));
                            break;
                        case "double":
                            field.set(t, cursor.getDouble(index));
                            break;
                        case "java.lang.Double":
                            field.set(t, cursor.getDouble(index));
                            break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            tList.add(t);
        }
        cursor.close();
        // 关闭当前数据库
        closeDB(db);
        if (tList.size() > 0) {
            callBack.querySuccess(tList.get(0));
        } else {
            callBack.queryFail("没有查询到数据");
        }
    }

    public  <T> void queryList(Class<T> clazz, String sql, GreenDaoCallBack.QueryCallBack<List<T>> callBack) {
        final SQLiteDatabase db =  getDB(false);
        List<T> tList = new ArrayList<>();
        try{
            db.rawQuery(sql, null);
        }catch (Exception e){
            Log.e("ww",e.getMessage());
        }
        Cursor cursor = db.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            T t;
            try {
                t = clazz.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
                continue;
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                continue;
            }
            String[] names = cursor.getColumnNames();
            for (String name : names) {
                int index = cursor.getColumnIndex(name);
                Field field = null;
                try {
                    String fieldName = name.equals("_id") ? "id" : name;
                    try {
                        field = clazz.getDeclaredField(fieldName);
                    } catch (Exception e) {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field f : fields) {
                            if (f.getName().toLowerCase().equals(fieldName.toLowerCase())) {
                                field = f;
                                break;
                            }
                        }
                        if (field == null) {
                            continue;
                        }
                    }
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    switch (type.getName()) {
                        case "long":
                            field.set(t, cursor.getLong(index));
                            break;
                        case "java.lang.String":
                            field.set(t, cursor.getString(index));
                            break;
                        case "java.lang.Long":
                            field.set(t, cursor.getLong(index));
                            break;
                        case "int":
                            field.set(t, cursor.getInt(index));
                            break;
                        case "java.lang.Integer":
                            field.set(t, cursor.getInt(index));
                            break;
                        case "double":
                            field.set(t, cursor.getDouble(index));
                            break;
                        case "java.lang.Double":
                            field.set(t, cursor.getDouble(index));
                            break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            tList.add(t);
        }
        cursor.close();
        // 关闭当前数据库
        closeDB(db);
        callBack.querySuccess(tList);
    }



    private void  executeSqlList(final List<String> sqlList, final String[]sqls){
        if ((sqlList == null || sqlList.isEmpty())&&(sqls==null||sqls.length==0)) {
            return;
        }
        getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                if(sqlList!=null){
                    for (int i = 0; i < sqlList.size(); i++) {
                        getDaoSession().getDatabase().execSQL(sqlList.get(i));
                    }
                }
                if(sqls!=null){
                    for (String sql : sqls) {
                        getDaoSession().getDatabase().execSQL(sql);
                    }
                }
            }

        });
    }

    /**
     * 判断数据库中某张表是否存在
     */
    public boolean sqlTableIsExist(String tableName) {
        long count = queryCount("select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName+"'");
        return count>0;
    }

    private long queryCount(String sql) {
        final SQLiteDatabase db = getDB(false);
        Cursor cursor = db.rawQuery(sql, null);
        long count = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String[] names = cursor.getColumnNames();
            count = cursor.getLong(cursor.getColumnIndex(names[0]));
        }
        cursor.close();
        // 关闭当前数据库
        closeDB(db);
        return count;
    }

}
