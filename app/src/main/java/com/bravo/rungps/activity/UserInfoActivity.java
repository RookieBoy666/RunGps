package com.bravo.rungps.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bravo.rungps.R;

public class UserInfoActivity extends AppCompatActivity {
    private TextView username=null;
    private Button edituserinfo;
    private TextView sex=null;
    private TextView desc=null;
    private UserDataManager mUserDataManager;         //用户数据管理类
    private SQLiteDatabase mSQLiteDatabase = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        username=(TextView)super.findViewById(R.id.userinfoname);
        sex=(TextView)super.findViewById(R.id.textView_sex);
        desc=(TextView)super.findViewById(R.id.desc1);
//        edituserinfo=(Button)super.findViewById(R.id.edituserinfo);

        Intent user= getIntent();
        String userName= user.getStringExtra("userName");
        username.setText(userName);


        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }

        String name = username.getText().toString().trim();    //获取当前输入的用户名
              String[]  a= mUserDataManager.userinfo(name);
               sex.setText(a[0]);
               desc.setText(a[1]);


//        edituserinfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserInfoActivity.this,EditUserInfoActivity.class) ;    //切换到editactivity
//                intent.putExtra("userName",userName); //账号名
//                startActivity(intent);
//                        finish();
//            }
//        });

    }

 public void showdialog(View view)
{
//Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
alertdialogbuilder.setMessage("您确认要注销账号");
alertdialogbuilder.setPositiveButton("取消", click1);
alertdialogbuilder.setNegativeButton("确定", click2);
 AlertDialog alertdialog1=alertdialogbuilder.create();
alertdialog1.show();
 }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
{
@Override
public void onClick(DialogInterface arg0,int arg1)
{
    arg0.cancel();
}
};
private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
{
@Override
public void onClick(DialogInterface arg0, int arg1)
{
    String userName = username.getText().toString().trim();    //获取当前输入的用户名
    mUserDataManager.deleteUserDatabyName(userName);
    Toast.makeText(UserInfoActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(UserInfoActivity.this,Login.class) ;    //切换到Loginactivity
    startActivity(intent);
    finish();
}
};

}
