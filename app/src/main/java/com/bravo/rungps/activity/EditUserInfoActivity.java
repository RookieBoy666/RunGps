package com.bravo.rungps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bravo.rungps.R;

public class EditUserInfoActivity extends AppCompatActivity {
  private EditText name=null;
  private  EditText sex=null;
  private  EditText desc=null;
  private Button  sureedit=null;

    private UserDataManager mUserDataManager;         //用户数据管理类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserinfo);

        name = (EditText) super.findViewById(R.id.edit_name);
        sex = (EditText) super.findViewById(R.id.edit_sex);
        desc = (EditText) super.findViewById(R.id.desc);
        sureedit = (Button) super.findViewById(R.id.resetpwd_btn_sure);


        sureedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String usersex = sex.getText().toString().trim();
                String userdesc = desc.getText().toString().trim();
                UserData mUser = new UserData(userName, usersex, userdesc);
                int count=mUserDataManager.findUserByName(userName);
                mUserDataManager.openDataBase();
//用户不存在时返回，给出提示文字
                if(count<=0){
                    Toast.makeText(getApplicationContext(), "账户不存在！", Toast.LENGTH_SHORT);

                }
                if (name.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入用户名！", Toast.LENGTH_SHORT);

                } else if (sex.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入性别！", Toast.LENGTH_SHORT);

                } else if (desc.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入签名！", Toast.LENGTH_SHORT);

                }


                boolean flag = mUserDataManager.updateUserInfo(mUser);
                     if (flag == false) {
//                    Toast.makeText(this, getString(R.string.editinfomation_fail),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "编辑信息失败！", Toast.LENGTH_SHORT);
                } else {
//                    Toast.makeText(this, getString(R.string.editinfomation_success),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "修改信息成功！", Toast.LENGTH_SHORT);
                    mUser.pwdresetFlag = 1;
                    Intent editsucess = new Intent(EditUserInfoActivity.this, UserInfoActivity.class);    //切换User Activity至Login Activity

//                    sucess="1";
//                    editsucess.putExtra("sucess",sucess);     //信使确定信息改成功
                    startActivity(editsucess);
                    finish();
                }
            }

        });
    }
    public  void cancel()
    {}


}
