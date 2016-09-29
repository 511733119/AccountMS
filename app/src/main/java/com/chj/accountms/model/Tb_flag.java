package com.chj.accountms.model;
/*
 * 便签信息实体类
 */
public class Tb_flag {

    private int _id;//存储便签编号
    private String title;//存储便签title信息
    private String flag;//存储便签信息
    public Tb_flag()  //默认构造函数
    {
        super();
        // TODO Auto-generated constructor stub
    }
    //定义有参构造函数，用来初始化便签信息实体类
    public Tb_flag(int _id, String title,String flag) {
        super();
        this._id = _id;//为便签号赋值
        this.title = title;//为便签title赋值
        this.flag = flag;//为便签信息赋值
    }
    public int get_id() {//设置便签编号的可读属性
        return _id;
    }
    public void set_id(int _id) {//设置便签编号的可写属性
        this._id = _id;
    }
    public String getTitle() { //设置便签编号的可读属性
        return title;
    }
    public void setTitle(String title) {//设置便签编号的可写属性
        this.title = title;
    }
    public String getFlag() { //设置便签编号的可读属性
        return flag;
    }
    public void setFlag(String flag) {//设置便签编号的可写属性
        this.flag = flag;
    }

}
