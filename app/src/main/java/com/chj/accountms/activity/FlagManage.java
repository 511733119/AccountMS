package com.chj.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.FlagDAO;
import com.chj.accountms.model.Tb_flag;

public class FlagManage extends Activity {

	private TextView title;	//标题
	private EditText ettitle,etflag;	//获得便签标题和内容
	private Button btnsave,btncancel,back;	//获得保存,取消,返回按钮
	FlagDAO flagDAO = null;
	Tb_flag tb_flag = null;
	String flagtitle = "";
	String flag = "";
	String strinfos = "";	//用于存放传过来的数据
	String strid = "";	//用于存储获得的便签id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.flagmanage);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏
		ettitle = (EditText)findViewById(R.id.ettitle);	//获得titile
		etflag = (EditText)findViewById(R.id.etflag);	//获得flag
		btnsave = (Button)findViewById(R.id.btnAdd);	//获得保存按钮
		btncancel = (Button)findViewById(R.id.btnCancel);	//获得取消按钮
		back = (Button)findViewById(R.id.back);	//获得返回按钮
		title = (TextView)findViewById(R.id.title);	//标题

		title.setText("便签管理");

		Intent intent = getIntent();	//创建intent对象
		Bundle bundle = intent.getExtras();	//获取传入的数据
		strinfos = bundle.getString(Showinfo.FLAG);	//获取bundle中的数据
		strid = strinfos;	//获取id

		flagDAO = new FlagDAO(FlagManage.this);
		tb_flag = flagDAO.find(Integer.parseInt(strid));	//根据编号获得便签信息
		ettitle.setText(tb_flag.getTitle());	//显示标题
		etflag.setText(tb_flag.getFlag());		//显示内容

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FlagManage.this,Showinfo.class);
				finish();
				startActivity(intent);
			}
		});

		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flagtitle = ettitle.getText().toString();	//获得title内容
				flag = etflag.getText().toString();	//获得flag内容
				if(flagtitle.isEmpty() || flag.isEmpty()){
					Toast.makeText(FlagManage.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
					ettitle.setText("");
				}else{
					flagDAO = new FlagDAO(FlagManage.this);	//创建flagDAO对象
					tb_flag = new Tb_flag(Integer.parseInt(strid),flagtitle,flag);	//创建对象并赋值
					flagDAO.update(tb_flag);	//执行添加操作
					Toast.makeText(FlagManage.this,"保存成功",Toast.LENGTH_SHORT).show();
					finish();
					Intent intent = new Intent(FlagManage.this,MainActivity.class);	//跳转至主界面
					startActivity(intent);
				}
			}
		});
		btncancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
