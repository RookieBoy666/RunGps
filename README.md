# RunGps

目录
轨迹定位3
LocationClient找不到类  百度地图下载不全3
按钮在user。Xml逻辑在user.java3
处理百度地图开源项目的定位偏差4
侧边栏点击事件：在user.Java  视图在menu_layout4
//获取登录信息4
尚未完成：
添加天气信息4
二维码功能4
指纹识别5
定位分享5
闹钟功能5

UserData6
账号登录后下次自动登录的问题（获取用户名）6


 
点击enter在user里
DylanStepCount-master计步器
APP停止运行 可能：1.jinLibs没拷贝 2.libs没应用  3.布局文件格式出错
Activity_main下com.bravo.rungps.view.StepArcView 包引用错误

RunGps调试没结束
侧边栏实现进行中

轨迹定位
 

 

 

LocationClient找不到类  百度地图下载不全

按钮在user。Xml逻辑在user.java
    public void main(View view){
        startActivity(new Intent(this,MainActivity.class));
    }

 
处理百度地图开源项目的定位偏差
option.setCoorType("bd09ll");



侧边栏点击事件：在user.Java  视图在menu_layout
 
//获取登录信息
1.Login（发送）
Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
                intent.putExtra("userName",userName); //账号名

                startActivity(intent);
User.java(获取)
        Intent user= getIntent();
        String userName= user.getStringExtra("userName");
         username.setText(userName);

2.user发送
UserInfoActivity接收

添加天气信息

注销时弹出框
在UserInfoActivity中
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

//    android.os.Process.killProcess(android.os.Process.myPid());
    

}

UserData
Get set
Userdatamanage

账号登录后下次自动登录的问题（获取用户名）
在login中
存储
//创建SharedPreferences 存储用户名
SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE); //此处name
SharedPreferences.Editor ed = sharedPreferences.edit();//获取编辑器
ed.putString("name",userName);  //此处name传递    类比intent信使
ed.commit();

读取

SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
String nameValue = sharedPreferences.getString("name", "");

intent.putExtra("userName",nameValue);    //获取的数据可以传递（类比intent）




此总结是解决安卓账号登录后下次自动登录的问题（获取用户名）

