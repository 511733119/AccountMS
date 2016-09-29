package com.chj.accountms.model;
/*
 * 密码数据表实体类
 */
public class Tb_pwd {

    private String password;//定义字符串，表示用户密码
    public Tb_pwd(){//默认构造函数
        super();
    }
    //定义有参构造函数
    public Tb_pwd(String password){
        super();
        this.password=password;
    }

    public String getPassword() {//定义密码的可读属性
        return password;
    }

    public void setPassword(String password) {//为密码赋值
        this.password = password;
    }

}
