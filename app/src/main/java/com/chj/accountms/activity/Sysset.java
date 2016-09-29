package com.chj.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.PwdDAO;
import com.chj.accountms.model.Tb_pwd;

/***
 * 类名：Sysset
 * 描述：设置密码信息
 * author:陈海俊
 */
public class Sysset extends Activity {

	Button back;	//返回按钮
	TextView title;	//标题
	EditText password;	//密码框
	Button btnset,btnclose;	//设置按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.sysset);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏
		back = (Button)findViewById(R.id.back);
		password = (EditText)findViewById(R.id.etpwd);	//密码框
		btnset = (Button)findViewById(R.id.btnset);	//设置密码按钮
		btnclose = (Button)findViewById(R.id.btnClose);	//设置取消按钮
		title = (TextView)findViewById(R.id.title);
		title.setText("系统设置");	//设置标题

		btnset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String pwd = password.getText().toString();	//获得密码框内容
				Tb_pwd tb_pwd = new Tb_pwd(pwd);	//创建Tb_pwd对象
				PwdDAO pwdDAO = new PwdDAO(Sysset.this);	//创建PwdDAO对象
				if(pwdDAO.getCount()==0){
					pwdDAO.add(tb_pwd);	//执行添加操作
				}else{
					pwdDAO.update(tb_pwd);	//执行更新操作
				}

				Toast.makeText(Sysset.this,"保存成功",Toast.LENGTH_SHORT).show();	//提示消息
				Intent intent = new Intent(Sysset.this,MainActivity.class);	//跳转
				finish();
				startActivity(intent);
			}
		});

		btnclose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Sysset.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Sysset.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accountflag, menu);
		return true;
	}

}
