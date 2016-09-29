package com.chj.accountms.activity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.InaccountDAO;
import com.chj.accountms.dao.OutaccountDAO;
import com.chj.accountms.model.Tb_inaccount;
import com.chj.accountms.model.Tb_outaccount;

import java.util.Calendar;

public class InfoManage extends Activity {

	protected static final int DATE_DIALOG_ID = 0;	//创建日期对话框常量
	EditText etmoney,ettime,etpay,etmark;	//创建4个EditTEXT对象
	TextView tvpay,title ;	//创建付款方/地点,标题对象
	Spinner ettype ;		//创建Spinner对象
	Button btnsave , btncancel ,back;		//创建Button对象
	String strinfos[];		//定义字符串数组
	String strid,strType ;	//定义两个字符串变量，分别用来记录信息编号和管理类型
	private int year , month ,day;	//定义年，月，日
	OutaccountDAO outaccountDAO ;
	InaccountDAO inaccountDAO ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.infomanage);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏

		etmoney = (EditText)findViewById(R.id.etmoney);		//获取金额
		ettime = (EditText) findViewById(R.id.ettime);		//获取日期
		etpay = (EditText) findViewById(R.id.etpay);	//获取付款方/地点
		etmark = (EditText)findViewById(R.id.etmark);		//获取备注
		ettype = (Spinner)findViewById(R.id.ettype);		//获取类型
		tvpay = (TextView)findViewById(R.id.tvpay);		//获取地点
		btnsave = (Button)findViewById(R.id.btnSave);			//获取保存按钮
		btncancel = (Button)findViewById(R.id.btnCancel);	//获取取消按钮
		back = (Button)findViewById(R.id.back);
		title = (TextView)findViewById(R.id.title);	//标题

		outaccountDAO = new OutaccountDAO(InfoManage.this);
		inaccountDAO = new InaccountDAO(InfoManage.this);

		Intent intent = getIntent();	//创建intent对象
		Bundle bundle = intent.getExtras();	//获取传入的数据
		strinfos = bundle.getStringArray(Showinfo.FLAG);	//获取bundle中的数据
		strid = strinfos[0];	//获取id
		strType = strinfos[1];	//获取类型
		if(strType.equals("btnoutinfo")){

			title.setText("支出管理");		//设置标题为“支出管理”
			tvpay.setText("地  点:");
			//根据编号查找支出信息，并存储到Tb_outaccount对象中
			Tb_outaccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
			ettype.setPrompt(tb_outaccount.getType());	//显示类型
			etpay.setText(tb_outaccount.getAddress());		//显示地点
			etmoney.setText(String.valueOf(tb_outaccount.getMoney()));	//显示金额
			etmark.setText(tb_outaccount.getMark());	//显示备注
			ettime.setText(tb_outaccount.getTime());	//显示日期

		}else if(strType.equals("btnininfo")){

			title.setText("收入管理");		//设置标题为“收入管理”
			tvpay.setText("付款方：");
			//根据编号查找收入信息，并存储到Tb_inaccount对象中
			Tb_inaccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
			ettype.setPrompt(tb_inaccount.getType());	//显示类型
			etpay.setText(tb_inaccount.getHandler());		//显示付款方
			etmoney.setText(String.valueOf(tb_inaccount.getMoney()));	//显示金额
			etmark.setText(tb_inaccount.getMark());	//显示备注
			ettime.setText(tb_inaccount.getTime());	//显示日期
		}
		ettime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);	//弹出日期对话框
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (strType.equals("btnininfo")){
					Intent intent = new Intent(InfoManage.this,Inaccountinfo.class);
					finish();
					startActivity(intent);
				}else{
					Intent intent = new Intent(InfoManage.this,Outaccountinfo.class);
					finish();
					startActivity(intent);
				}
			}
		});
		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(strType.equals("btnininfo")) { //如果是收入类型
					String type = ettype.getSelectedItem().toString();    //获得类型
					String mark = etmark.getText().toString();    //获得备注
					String time = ettime.getText().toString();    //获得日期
					String handle = etpay.getText().toString();    //获得付款方
					Double money = Double.parseDouble(etmoney.getText().toString());    //获得金额
					Tb_inaccount tb_inaccount = new Tb_inaccount(
							Integer.parseInt(strid), money, time, type, handle, mark);  //为各属性赋值
					inaccountDAO.update(tb_inaccount);    //执行修改操作

					Toast.makeText(InfoManage.this,"保存成功",Toast.LENGTH_SHORT).show();//弹出消息提示
					finish();
					Intent intent = new Intent(InfoManage.this,Inaccountinfo.class);
					startActivity(intent);
				}else if(strType.equals("btnoutinfo")){//如果是支出类型
					String type = ettype.getSelectedItem().toString();    //获得类型
					String mark = etmark.getText().toString();    //获得备注
					String time = ettime.getText().toString();    //获得日期
					String handle = etpay.getText().toString();    //获得地点
					Double money = Double.parseDouble(etmoney.getText().toString());    //获得金额
					Tb_outaccount tb_outaccount = new Tb_outaccount(
							Integer.parseInt(strid), money, time, type, handle, mark);  //为各属性赋值
					outaccountDAO.update(tb_outaccount);   //执行修改操作

					Toast.makeText(InfoManage.this,"保存成功",Toast.LENGTH_SHORT).show();//弹出消息提示
					finish();
					Intent intent = new Intent(InfoManage.this,Outaccountinfo.class);
					startActivity(intent);
				}
			}
		});
		btncancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (strType.equals("btnininfo")) {    //如果是收入类型
					inaccountDAO.delete(Integer.parseInt(strid));    //删除指定编号的收入信息
					Toast.makeText(InfoManage.this, "删除成功", Toast.LENGTH_SHORT).show();//弹出消息提示
					finish();
					Intent intent = new Intent(InfoManage.this,Inaccountinfo.class);
					startActivity(intent);
				} else if (strType.equals("btnoutinfo")) {//如果是支出类型
					outaccountDAO.delete(Integer.parseInt(strid));    //删除指定编号的收入信息
					Toast.makeText(InfoManage.this, "删除成功", Toast.LENGTH_SHORT).show();//弹出消息提示
					finish();
					Intent intent = new Intent(InfoManage.this,Outaccountinfo.class);
					startActivity(intent);
				}

			}
		});
		Calendar calendar = Calendar.getInstance();  //获取当前系统日期
		year = calendar.get(Calendar.YEAR);		//获取年份
		month = calendar.get(Calendar.MONTH);	//获取月份
		day = calendar.get(Calendar.DAY_OF_MONTH);	//获取天数
		updateDisplay();	//显示默认日期
	}
	private void updateDisplay(){
		//显示获取的时间
		ettime.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day));
	}

	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,listener ,year, month,day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int theyear, int monthOfYear, int dayOfMonth) {
			year = theyear;	//为年赋值
			month = monthOfYear ;	//为月赋值
			day = dayOfMonth;	//为天赋值
			updateDisplay();	//显示设置的日期
		}
	};

}
