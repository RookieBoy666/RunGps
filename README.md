# RunGps
1.百度地图KEY申请
SHA1不同需要申请百度地图Key
 
2.点击enter在user里
DylanStepCount-master计步器
APP停止运行 可能：1.jinLibs没拷贝 2.libs没应用  3.布局文件格式出错

3.轨迹定位
LocationClient找不到类  百度地图下载不全

4.按钮在user.Xml逻辑在user.java
    public void main(View view)
    {
        startActivity(new Intent(this,MainActivity.class));
    }

 
5.处理百度地图开源项目的定位偏差
option.setCoorType("bd09ll");



6.侧边栏点击事件：在user.Java  视图在menu_layout
 
//获取登录信息
7.Login（发送）
Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
                intent.putExtra("userName",userName); //账号名
                startActivity(intent);
User.java(获取)
        Intent user= getIntent();
        String userName= user.getStringExtra("userName");
         username.setText(userName);

8.user发送
UserInfoActivity接收

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
尚需解决的细节，安卓4.4后的通知权限适配，轨迹定位的速度、时间展示，个人信息完善用户数据在本地。


表 6 1函数原型及功能描述
编号	函数原型	函数功能描述
1	Function onSensorChanged(SensorEvent event)	Sensor变化
2	Function detectorNewStep(float values)	检测步子，并开始计步，1.传入传感器中的数据2.假设检测到了波峰，而且符合时间差以及阈值的条件，那么判定为一步
3.符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
3	Function detectorPeak(floatnewValue,float oldValue)	监测波峰，以下四个条件判断为波峰：
1.目前点为降落的趋向：isDirectionUp为假2.之前的点为上升的趋向：lastStatus为真3.到波峰为止，lianxu 

停止服务并取消定时唤醒，stopService 会调用 Service.onDestroy()，而 WorkService 做了保活处理，会把 Service 再拉起来；上升>=2次 4.波峰值>20
4	Function  peakValleyThread(float value)	阈值的计算：通过波峰波谷的差值计算阈值 2.记载 4个值，保存在tempValue[]数组中 3.再将数组传入函数averageValue中计算阈值
5	Function  averageValue(float value[], int n)	梯度化阈值：1.算当前数组的平均值 2.均值把阈值梯度化在一个范围内
6	Function  countStep()	连续走十步才会开始计步连续走了9步以下,停留超过3秒,则计数清空
7	setSteps(int initValue)	初始化步数。
8	Function initTodayData()	初始化当天的步数
9	Function initBroadcastReceiver()	注册广播
10	Function isNewDay()	监听晚上0点变化初始化数据
11	Function isCall()	监听时间变化提醒用户锻炼
12
13
14
15
16
17	Function updateNotification()
Function startStepDetector()
Function  addCountStepListener()
Function addBasePedometerListener()
Function save
Function onDestroy()	更新步数通知
获取传感器实例
添加传感器监听
通过加速度传感器来记步
保存记步数据
取消前台进程

乐动APP轨迹展示包含了以下主要函数，这些函数方法的原型及描述，如表6-2所示。


表 6 2函数原型及功能描述
编号	函数原型	函数功能描述
1	Function initMap(Bundle savedInstanceState)	初始化百度地图，设置缩放级别、开启定位图层
2	Function getCurrentLocation（）	定位权限检测
3	Function update(Observable o, Object arg)	接收service的信息
4	Function updateLocation(Information info)	位置变化更新
5	Function runGetFirstLocation(Location location) 	获取第一次的定位数据
6	Function moveMap(LinkedList<PositionBean> list)	地址获取不成功则将地图移动到list的最后一个点。
7	updateListenerView()	地图上画线与起始点，当前位置标蓝点
8	Function onClick(View v)	下方开始暂停按钮的点击事件
9	Function gpsToBaidu(LatLng data) ()	规范的GPS经纬度坐标假设直接在地图上绘制，地点会有偏移，这是测绘局和地图商添加的加密，要转换成百度地图对应的坐标[6]
10	Function onRespondError(String message)	网络请求错误，返回message时，如果想直接弹提示，调用这里
11	Function getFormatTime(long time)	得到一个格式化的时间
12
13
14
15
16
17
18	Function onReceive(Context context, Intent intent)
Function setOutDoorRunListener()


Function startService()
Function stopService
Function onGpsStatusChanged(int event)
Function onLocationChanged(final Location mlocation) 
Function  getLatLng(LocationManager locationManager)	广播的监听类，监听 SDK key 验证以及网络异常广播获取传感器实例
设置室外跑的监听。从startCommand调用。保存记步数据，期望间隔两秒或者两米更新一次
开始服务
停止服务并取消定时唤醒，stopService 会调用 Service.onDestroy()，而 WorkService 做了保活处置，会将 Service 再一次拉起来；
卫星状态改变监测
位置改变监测
获取经纬度，定位方式选择：网络定位和GPS定位




完结2019.5.18

