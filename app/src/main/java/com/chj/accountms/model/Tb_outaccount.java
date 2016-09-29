package com.chj.accountms.model;
/*
 * 支出信息实体类
 */
public class Tb_outaccount {

    private int _id; //存储支出编号
    private double money;//存储支出金额
    private String time;//存储支出时间
    private String type;//存储支出类别
    private String address;//存储支出地点
    private String mark;//存储支出备注
    public Tb_outaccount() {//默认构造函数
        super();
        // TODO Auto-generated constructor stub
    }
    //定义有参构造函数，用来初始化支出信息实体类中的各个字段
    public Tb_outaccount(int _id, double money, String time, String type,
                         String address, String mark) {
        super();
        this._id = _id;//为支出编号赋值
        this.money = money;//为支出金额赋值
        this.time = time;//为支出时间赋值
        this.type = type;//为支出类别赋值
        this.address = address;//为支出地点赋值
        this.mark = mark;//为支出备注赋值
    }
    public int get_id() { //设置支出编号的可读属性
        return _id;
    }
    public void set_id(int _id) {//设置支出编号的可写属性
        this._id = _id;
    }
    public double getMoney() {//设置支出金额的可读属性
        return money;
    }
    public void setMoney(double money) {//设置支出金额的可写属性
        this.money = money;
    }
    public String getTime() {//设置支出时间的可读属性
        return time;
    }
    public void setTime(String time) {//设置支出时间的可写属性
        this.time = time;
    }
    public String getType() {//设置支出类型的可读属性
        return type;
    }
    public void setType(String type) {//设置支出类型的可写属性
        this.type = type;
    }
    public String getAddress() {//设置支出地点的可读属性
        return address;
    }
    public void setAddress(String address) {//设置支出地点的可写属性
        this.address = address;
    }
    public String getMark() {//设置支出备注的可读属性
        return mark;
    }
    public void setMark(String mark) {//设置支出备注的可写属性
        this.mark = mark;
    }
}
