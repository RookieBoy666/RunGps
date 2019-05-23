package com.bravo.rungps.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bravo.rungps.R;
import com.bravo.rungps.run.SettingsActivity;
import com.bravo.rungps.step.UpdateUiCallBack;
import com.bravo.rungps.step.service.StepService;
import com.bravo.rungps.step.utils.SharedPreferencesUtils;
import com.bravo.rungps.view.StepArcView;


public class User extends Activity implements View.OnClickListener {
    //mainactivity private

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;   //判断是否第一次登录
    SharedPreferences user_name;      //存储用户名
    SharedPreferences.Editor findname;


    private TextView tv_data;
    private StepArcView cc;
    private TextView tv_set;
    private TextView tv_isSupport;
    private SharedPreferencesUtils sp;

    private void assignViews() {
        tv_data = (TextView) findViewById(R.id.tv_data);
        cc = (StepArcView) findViewById(R.id.cc);
        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_isSupport = (TextView) findViewById(R.id.tv_isSupport);

    }


    /////////////////////////////////////////////////////////////
    private Button mReturnButton;
    private Button enter;
    private TextView un=null;
    private SlidingMenu mMenu;
    public static RelativeLayout shadow_layout;
    private ImageView back=null;
    private TextView backlogin=null;
    private  ImageView userinfophoto; //头像
    private  TextView username=null;  //昵称
    private View view;
    private TextView step;
    private TextView selflocation;
    private TextView searchlocation;
    private TextView orbitlocation;
    private  TextView about;
private  TextView run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_success_login);
//        setContentView(R.layout.user);
        view =this.getLayoutInflater().inflate(R.layout.user,null);
        setContentView(view);


        /////////////////////////////mainactivity fangfa
        assignViews();
        initData();
        addListener();

        userinfophoto=(ImageView)super.findViewById(R.id.userinfophoto);
        username=(TextView)super.findViewById(R.id.user_name);//头像旁姓名昵称

        back=(ImageView)super.findViewById(R.id.menu_setting_icon);
        backlogin=(TextView)super.findViewById(R.id.exit);    //退出登录
//        step=(TextView)super.findViewById(R.id.step) ;
        selflocation=(TextView)super.findViewById(R.id.knowledge_library);
        searchlocation=(TextView)super.findViewById(R.id.custom_service);
        orbitlocation=(TextView)super.findViewById(R.id.orbittextview);
        about=(TextView) super.findViewById(R.id.About);

        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
run=(TextView)findViewById(R.id.runtextview);
      //获取登录信息
        Intent user= getIntent();
        String userName= user.getStringExtra("userName");
         username.setText(userName);
///////////////////////////////////////////////////////////////////////////////


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setBackgroundDrawableResource(R.color.contentTitleColor);
        }
        shadow_layout=(RelativeLayout)findViewById(R.id.shadow_layout);
   back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent3 = new Intent(User.this,Login.class) ;
        startActivity(intent3);
        finish();
    }

});
      userinfophoto.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent1 = new Intent(User.this,UserInfoActivity.class) ;
              intent1.putExtra("userName",userName); //账号名
              startActivity(intent1);
              startActivity(intent1);

          }
      });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(User.this,UserInfoActivity.class) ;

                intent1.putExtra("userName",userName); //账号名
                startActivity(intent1);


            }
        });
        //计步按钮
//        step.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(User.this,MainActivity.class) ;
//                startActivity(intent1);
//
//            }
//        });
        //定位按钮
        selflocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(User.this,SelfLocationActivity.class) ;
                startActivity(intent1);
            }
        });
        //查找按钮
        searchlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(User.this,SearchLocationActivity.class) ;
                startActivity(intent1);
            }
        });
        orbitlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(User.this,OrbitLocationActivity.class) ;
                startActivity(intent1);
            }
        });
         about.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent= new Intent(User.this,AboutActivity.class) ;
                      startActivity(intent);
                  }
              });
run.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

//
//        Intent intent = new Intent();
//        intent.setClass(User.this, StepCounterActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("run", false);
//        intent.putExtras(bundle);
//        startActivity(intent);

        Intent intent = new Intent();
        intent.setClass(User.this, SettingsActivity.class);
        startActivity(intent);


    }
});
        backlogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Intent intent3 = new Intent(User.this,Login.class) ;
//        startActivity(intent3);
//        finish();
        //点击注销按键后调用LoginActivity提供的resetSprfMain()方法执行editorMain.putBoolean("main",false);，即将"main"对应的值修改为false
        resetSprfMain();


//       //创建SharedPreferences 存储用户名
//        SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE); //此处name相当于数据库
//        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//        editor.putString("name",userName);  //此处name传递
//        editor.commit();



        Intent intent=new Intent(User.this,Login.class);
        startActivity(intent);
        User.this.finish();

    }

});
    }
    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.commit();
    }

//        Intent intent = getIntent();
//        String userName= intent.getStringExtra("userName");
//        un.setText(userName);
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(User.this ,MainActivity.class);
//                startActivity(i);
//            }
//        });


//    public void back_to_login(View view) {
//        //setContentView(R.layout.login);
//        Intent intent3 = new Intent(User.this,Login.class) ;
//        startActivity(intent3);
//        finish();
//
//    }

    //////////////////////////////////////////////
    private void addListener() {
        tv_set.setOnClickListener(this);
        tv_data.setOnClickListener(this);
    }

    private void initData() {
        sp = new SharedPreferencesUtils(this);
        //获取用户设置的计划锻炼步数，没有设置过的话默认7000
        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
        //设置当前步数为0
        cc.setCurrentCount(Integer.parseInt(planWalk_QTY), 0);
        tv_isSupport.setText("计步中...");
        setupService();
    }


    private boolean isBind = false;

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
            cc.setCurrentCount(Integer.parseInt(planWalk_QTY), stepService.getStepCount());

            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
                    cc.setCurrentCount(Integer.parseInt(planWalk_QTY), stepCount);
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set:
                startActivity(new Intent(this, SetPlanActivity.class));
                break;
            case R.id.tv_data:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击返回键
        if(keyCode==KeyEvent.KEYCODE_BACK){
            //声明弹出对象并初始化
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("是否退出?");
            //设置确定按钮
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            //设置取消按钮
            builder.setPositiveButton("取消",null);
            //显示弹窗
            builder.show();
        }
        return super.onKeyDown(keyCode,event);
    }
}
