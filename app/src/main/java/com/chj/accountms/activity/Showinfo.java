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
import com.chj.accountms.dao.FlagDAO;
import com.chj.accountms.dao.InaccountDAO;
import com.chj.accountms.dao.OutaccountDAO;
import com.chj.accountms.model.Tb_flag;
import com.chj.accountms.model.Tb_inaccount;
import com.chj.accountms.model.Tb_outaccount;

import java.util.List;

/**
 * 类名：Showinfo
 * 描述：实现收入，支出，便签信息浏览
 * author：陈海俊
 */
public class Showinfo extends Activity {

	public static final String FLAG = "id";	//定义一个字符串，用来作为请求码
	Button btnoutinfo,btnininfo,btnflaginfo,back;	//定义四个按钮
	ListView lvinfo;	//创建ListView对象
	TextView title ;	//标题
	String strType = "";	//创建字符串，记录管理类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.showinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏
		btnoutinfo = (Button) findViewById(R.id.btnoutinfo);	//获取支出信息按钮
		btnininfo = (Button) findViewById(R.id.btnininfo);		//获取收入信息按钮
		btnflaginfo = (Button) findViewById(R.id.btnflaginfo);		//获取便签按钮
		back = (Button) findViewById(R.id.back);		//获取返回按钮
		lvinfo = (ListView)findViewById(R.id.lvinfo);	//获取ListView组件
		title = (TextView)findViewById(R.id.title);
		title.setText("数据管理");	//设置标题

		btnoutinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowInfo(R.id.btnoutinfo);	//显示支出信息
			}
		});

		btnininfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowInfo(R.id.btnininfo);	//显示收入信息
			}
		});

		btnflaginfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowInfo(R.id.btnflaginfo);	//显示便签信息
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Showinfo.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});

		lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String strinfo = String.valueOf(((TextView) view).getText());    //获取所点击项的信息
				String strid = strinfo.substring(0,strinfo.indexOf(")"));	//获取id
				Intent intent = null;	//创建intent对象
				if(strType == "btnoutinfo" | strType == "btnininfo"){	//如果是支出或收入类型
					intent = new Intent(Showinfo.this,InfoManage.class);	//跳到InfoManage页面
					intent.putExtra(FLAG,new String[]{strid,strType});	//传数据
				}else if(strType == "btnflaginfo"){
					intent = new Intent(Showinfo.this,FlagManage.class);	//跳到便签管理页面
					intent.putExtra(FLAG,strid);	//传数据
				}
				startActivity(intent);	//执行intent,打开相应的Activity
			}
		});
	}

	private void ShowInfo(int intType){
		String[] strinfos = null;	//定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;	//创建ArrayAdapter对象
		switch(intType) {
            case R.id.btnoutinfo:    //如果是查看支出按钮
                strType = "btnoutinfo";
                OutaccountDAO outaccountDAO = new OutaccountDAO(Showinfo.this);
                //获取所有支出信息，并存储到List集合中
                List<Tb_outaccount> listoutinfos = outaccountDAO.getScrollData(0, (int) outaccountDAO.getCount());
                strinfos = new String[listoutinfos.size()]; //设置字符数组长度
                int i = 0;    //定义一个开始标识
                for (Tb_outaccount tb_outaccount : listoutinfos) {    //遍历
                    //将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strinfos[i] = tb_outaccount.get_id() + ") " +
                            tb_outaccount.getType() + "      " +
                            String.valueOf(tb_outaccount.getMoney()) + "元      " +
                            tb_outaccount.getTime();
                    i++; //标识+1
                }
                break;
            case R.id.btnininfo:    //如果是查看收入按钮
                strType = "btnininfo";
                InaccountDAO inaccountDAO = new InaccountDAO(Showinfo.this);
                //获取所有支出信息，并存储到List集合中
                List<Tb_inaccount> listininfos = inaccountDAO.getScrollData(0, (int) inaccountDAO.getCount());
                strinfos = new String[listininfos.size()]; //设置字符数组长度
                int j = 0;    //定义一个开始标识
                for (Tb_inaccount tb_inaccount : listininfos) {    //遍历
                    //将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
                    strinfos[j] = tb_inaccount.get_id() + ") " +
                            tb_inaccount.getType() + "      " +
                            String.valueOf(tb_inaccount.getMoney()) + "元      " +
                            tb_inaccount.getTime();
                    j++; //标识+1
                }
                break;
            case R.id.btnflaginfo:    //如果是查看便签按钮
                strType = "btnflaginfo";
                FlagDAO flagDAO = new FlagDAO(Showinfo.this);
                //获取所有便签信息，并存储到List集合中
                List<Tb_flag> listflaginfos = flagDAO.getScrollData(0,
                        (int) flagDAO.getCount());
                strinfos = new String[listflaginfos.size()];
                int k = 0;
                for (Tb_flag tb_flag : listflaginfos) {
                    strinfos[k] = tb_flag.get_id() + ") " +
                            tb_flag.getTitle();
                    k++;
                }
                break;
		}
        arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1
                ,strinfos);
        lvinfo.setAdapter(arrayAdapter);  //为listView设置数据源
	}
    @Override
    protected  void onRestart(){
        super.onRestart();
        ShowInfo(R.id.btnoutinfo);  //显示支出信息
    }
}
