package com.bravo.rungps.activity;

public class UserData {
    private String userName;                  //用户名
    private String userPwd;                   //用户密码
    private int userId;                       //用户ID号
    private String userSex;
    private String userDescription;
    public int pwdresetFlag=0;


    //获取用户名
    public String getUserName()
    {             //获取用户名
        return userName;
    }
    //设置用户名
    public void setUserName(String userName)
    {  //输入用户名
        this.userName = userName;
    }

    //获取用户密码
    public String getUserPwd()
    {                //获取用户密码
        return userPwd;
    }
    //设置用户密码
    public void setUserPwd(String userPwd)
    {     //输入用户密码
        this.userPwd = userPwd;
    }
    //获取性别
    public String getUserSex()
    {             //获取用户名
        return userSex;
    }
    //设置用户名
    public void setUserSex(String userSex)
    {  //输入性别
        this.userSex = userSex;
    }


    //获取描述
    public String getUserDescription()
    {             //获取用户名
        return userDescription;
    }
    //设置用户名
    public void settUserDescription(String userDescription)
    {  //输入miaoshu
        this.userDescription = userDescription;
    }

    //获取用户id
//    public int getUserId() {                   //获取用户ID号
//        return userId;
//    }
//    //设置用户id
//    public void setUserId(int userId) {       //设置用户ID号
//        this.userId = userId;
//    }

//   public UserData(String userName, String userPwd, int userId) {    //用户信息
//        super();
//        this.userName = userName;
//        this.userPwd = userPwd;
//        this.userId = userId;
//    }

    public UserData(String userName, String userPwd)
    {  //这里只采用用户名和密码
        super();
        this.userName = userName;
        this.userPwd = userPwd;
    }
    public UserData(String userName, String userPwd,String userSex,String userDescription )
    {  //这里采用用户名和密码 性别 描述
        super();
        this.userName = userName;
        this.userPwd = userPwd;
        this.userSex = userSex;
        this.userDescription = userDescription;
    }

    public UserData(String userName,String userSex,String userDescription )
    {  //这里采用用户名和密码 性别 描述
        super();
        this.userName = userName;
        this.userSex = userSex;
        this.userDescription = userDescription;
    }




}
