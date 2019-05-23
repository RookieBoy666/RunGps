package com.bravo.rungps.activity;
/**
 * 主要是用户信息的管理操作
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataManager {             //用户数据管理类
    //一些宏定义和声明
    private static final String TAG = "UserDataManager";
    private static final String DB_NAME = "user_data";
    private static final String TABLE_NAME = "users";
    public static final String ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_SEX = "user_sex";
    public static final String USER_DESCRIPTION = "user_descripton";
//    public static final String SILENT = "silent";
//    public static final String VIBRATE = "vibrate";
    private static final int DB_VERSION = 2;
    private Context mContext = null;
    private static  String[] now=new String[5];
    //创建用户book表

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
    +ID + " VARCHAR PRIMARY KEY,"+ USER_NAME + " varchar," + USER_PWD + " varchar," +USER_SEX+" varchar,"+USER_DESCRIPTION+" varchar" +");";



    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    //DataBaseManagementHelper继承自SQLiteOpenHelper
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG,"db.getVersion()="+db.getVersion());
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME+ ";");
            db.execSQL(DB_CREATE);
            Log.i(TAG, "db.execSQL(DB_CREATE)");
            Log.e(TAG, DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            onCreate(db);
        }
    }

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");
    }
    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }
    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }
//    //添加新用户，即注册(用户名和密码）
//    public long insertUserData(UserData userData) {
//        String userName=userData.getUserName();
//        String userPwd=userData.getUserPwd();
//        ContentValues values = new ContentValues();
//        values.put(USER_NAME, userName);
//        values.put(USER_PWD, userPwd);
//
//
//        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
//    }
    ////////////2018.12.03
    //添加新用户，即注册(用户名和密码）
    public long insertUserData(UserData userData) {
        String userName=userData.getUserName();
        String userPwd=userData.getUserPwd();
        String userSex=userData.getUserSex();
        String userDescription=userData.getUserDescription();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        values.put(USER_SEX, userSex);
        values.put(USER_DESCRIPTION, userDescription);
        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
    }


    //根据用户名注销
    public boolean deleteUserDatabyName(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME, USER_NAME + "='" + name+"'", null) > 0;
    }
    //更新用户信息，如修改密码
    public boolean updateUserData(UserData userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
       values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        String where = USER_NAME +"=" +"="+ userName+" ";//添加
        return mSQLiteDatabase.update(TABLE_NAME, values,null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }

    public boolean updateUserInfo(UserData userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String sex = userData.getUserSex();
        String desc = userData.getUserDescription();

        ContentValues values = new ContentValues();
       values.put(USER_NAME, userName);
        values.put(USER_SEX, sex);
        values.put(USER_DESCRIPTION, desc);
//        String where = " ";//添加
        return mSQLiteDatabase.update(TABLE_NAME, values,null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }



    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName){
        Log.i(TAG,"findUserByName , userName="+userName);
        int result=0;
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName, null, null, null, null);
        String sql = "Select * from users where user_name =?";
        Cursor mCursor = mSQLiteDatabase.rawQuery(sql,new String[]{userName});
        //zhenguqe
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return result;
    }

    public String[] userinfo(String userName){

        Log.i(TAG,"userinfo , userName="+userName);

//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName, null, null, null, null);
        String sql = "Select * from users where user_name =?";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{userName});
        //zhenguqe
        while (cursor.moveToNext()){
            //真正的获取数据
            int id = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
            String user_sex=cursor.getString(3);
            String user_description=cursor.getString(4);
            Log.i("SQLITE","id:"+id+"sex:"+user_sex+"Description:"+user_description);
//           now[0] =id+"";
             now[0]=user_sex;      //性别    UserInfoActivity
            now[1]=user_description;     //签名
            Log.i("SQLITE","now[0]:"+now[0]+" now[1]:"+now[1]);
            Log.i("SQLITE","now:"+now);
        }
           return now;

    }



    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName, String pwd){
        Log.i(TAG,"findUserByNameAndPwd");
        int result=0;
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName+" and "+USER_PWD+"="+pwd,
//                null, null, null, null);

        String sql = "Select * from users where user_name=? and user_pwd=?";
        Cursor mCursor =mSQLiteDatabase.rawQuery(sql,new String[]{userName,pwd});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByNameAndPwd , result="+result);
        }
        return result;
    }





    //
    public Cursor fetchUserData(int id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //
    public Cursor fetchAllUserDatas() {
        return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
                null);
    }
    //根据id删除用户
    public boolean deleteUserData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null) > 0;
    }

    //删除所有用户
    public boolean deleteAllUserDatas() {
        return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
    }

    //
    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }
    //
    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }


}
