package com.chj.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chj.accountms.R;
import com.chj.accountms.dao.InaccountDAO;
import com.chj.accountms.model.Tb_inaccount;

import java.util.List;

/***
 * 类名：Inaccountinfo
 * 描述：显示收入信息
 * author:陈海俊
 */
public class Inaccountinfo extends Activity {

	TextView title;	//标题
	ListView lvinfo;    //创建ListView对象
	String strType = "";    //创建字符串，记录管理类型
	Button back ; //返回按钮
	public static final String FLAG = "id";        //定义变量，作为请求码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.inaccountinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏

		back = (Button)findViewById(R.id.back);
		lvinfo = (ListView) findViewById(R.id.inlistview);    //获取布局文件中的Listview组件
		title = (TextView)findViewById(R.id.title);	//标题
		title.setText("收入信息");

		showInfo(0);        //调用自定义方法显示收入信息

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Inaccountinfo.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});

		lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String strinfo = String.valueOf(((TextView) view).getText());    //获取所点击item的信息
				String strid = strinfo.substring(0, strinfo.indexOf(")"));        //从收入信息中截取收入的编号
				Intent intent = new Intent(Inaccountinfo.this, InfoManage.class);    //创建intent对象
				intent.putExtra(FLAG, new String[]{strid, strType});        //设置传递参数
                finish();
				startActivity(intent);        //执行intent操作
			}
		});
	}

	private void showInfo(int intType) {
		int m = 0;    //定义数组存储的坐标
		String[] strinfos = null;    //定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;    //创建ArrayAdapter对象
		strType = "btnininfo";    //为类型赋值
		InaccountDAO inaccountDAO = new InaccountDAO(Inaccountinfo.this);    //创建InaccountDAO对象
		//获取所有收入信息，并存储到List集合中
		List<Tb_inaccount> listinfos = inaccountDAO.getScrollData(0, (int) inaccountDAO.getCount());
		strinfos = new String[listinfos.size()];
		for (Tb_inaccount tb_inaccount : listinfos) {        //遍历list集合
			strinfos[m] = tb_inaccount.get_id() + ")" + tb_inaccount.getType() + "      " + String.valueOf(tb_inaccount.getMoney()) + "元        " + tb_inaccount.getTime();
			m++;    //标识加1
		}
		//使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strinfos);
		lvinfo.setAdapter(arrayAdapter);      //为listview设置数据源
	}


	@Override
	protected void onRestart() {
		super.onRestart(); //实现基类的方法
		showInfo(0);    //显示收入信息
	}

}
