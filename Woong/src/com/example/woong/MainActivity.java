package com.example.woong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity.ListItem;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private static final int Alarm = 0;
	Toast mToast;


	 //��� �ㅼ� �대���
	 public TimePicker mTime;

	 private int mHour;
	 private int mMinute;
	 
	 ArrayList<MyItem> arItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 
		arItem = new ArrayList<MyItem>();
		MyItem mi;
		mi = new MyItem(R.drawable.cloud_285x123, "서울시 마포구", "30");
		arItem.add(mi);
		mi = new MyItem(R.drawable.rain_285x197, "서울시 서대문구", "25");
		arItem.add(mi);
		mi = new MyItem(R.drawable.snow_93x225, "서울시 관악구", "40");
		arItem.add(mi);
		
		MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.icontext, arItem);
		
		ListView MyList;
		MyList = (ListView)findViewById(R.id.list);
		MyList.setDividerHeight(0);
		MyList.setAdapter(MyAdapter);
		
		

	}
	 
	public void mOnClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			 startActivity(new Intent(this,SearchActivity.class)); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*public boolean onPrepareOptionsMenu(Menu menu) {
		//int color = background.getTextColors().getDefaultColor();
		if (iv == Color.RED) {
			menu.findItem(R.id.item4).setChecked(true);
		}
	
		if (color == Color.BLUE) {
			menu.findItem(R.id.item5).setChecked(true);
		}
		return true;
	}*/
    
	
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    
    	case R.id.item1:
			//mBtn.setTextColor(Color.RED);
    		//setBackgroundColor(FF0000);
    		showDialog(Alarm);
    		return true;
    		
    	case R.id.item3:
    		startActivity(new Intent(this, InfoActivity.class));
    		return true;
    		
    	case R.id.item4:
			//mBtn.setTextColor(Color.RED);
    		//setBackgroundColor(FF0000);
    		
    		return true;
   
    	case R.id.item5:
			setTitleColor(Color.BLUE);
    		return true;
    	}
    	return false;
    }
	
	//TimePicker 由ъ���
	 private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
	    new TimePickerDialog.OnTimeSetListener(){
	     @Override
	     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	      mHour = hourOfDay;
	      mMinute = minute;
	     
	     }  
	 };
	 

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert;
		
		switch(id) {
		case Alarm:
			setAlarm();
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
			
		} 
		
		//return alert;
		  return null;
	}
	
	
	//������ㅼ�
	  private void setAlarm() {
       Intent intent = new Intent(MainActivity.this, RepeatingAlarm.class);
       PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
               0, intent, 0);
       
       // We want the alarm to go off 30 seconds from now.
       long firstTime = SystemClock.elapsedRealtime(); // 0�쇰� �명�
       firstTime += (mHour * 1000 * 60 * 60) + (mMinute *1000 * 60); //�ㅼ����

       // Schedule the alarm!
       AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
       am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                       firstTime, 86400*1000, sender);

       // Tell the user about what we did.
       if (mToast != null) {
           mToast.cancel();
       }
       mToast = Toast.makeText(MainActivity.this, R.string.repeating_scheduled,
               Toast.LENGTH_LONG);
       mToast.show();
	

	 //return sender;

	}
}

class MyItem {
	int Icon;
	String Name;
	String Temperature;
	boolean Selected;
	
	MyItem(int aIcon, String aName, String aTemperature) {
		Icon = aIcon;
		Name = aName;
		Temperature = aTemperature + "°C";
		Selected = false;
	}
}

// 어댑터 클래스
class MyListAdapter extends BaseAdapter {
	Context maincon;
	LayoutInflater Inflater;
	ArrayList<MyItem> arSrc;
	int layout;
	
	public MyListAdapter(Context context, int alayout, ArrayList<MyItem> aarSrc) {
		maincon = context;
		Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arSrc = aarSrc;
		layout = alayout;
	}
	
	public int getCount() {
		return arSrc.size();
	}
	
	public String getItem(int position) {
		return arSrc.get(position).Name;
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		
		if(convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}
		ImageView img = (ImageView)convertView.findViewById(R.id.weather_icon);
		img.setImageResource(arSrc.get(position).Icon);
		
		TextView textViewAddress = (TextView)convertView.findViewById(R.id.address);
		// textViewAddress.setTypeface(Typeface.createFromAsset(getAssets(), "RixMGoB.ttf"));
		textViewAddress.setText(arSrc.get(position).Name);
		
		TextView textViewTemperature = (TextView)convertView.findViewById(R.id.temperature);
		textViewTemperature.setText(arSrc.get(position).Temperature);
		
		// 배경바꾸기
		switch(pos) {
		case 0:
			convertView.setBackgroundResource(R.drawable.switch1_1);
			break;
		case 1:
			convertView.setBackgroundResource(R.drawable.switch1_2);
			break;
		case 2:
			convertView.setBackgroundResource(R.drawable.switch1_3);
			break;
			
		}
		
		return convertView;
	}
}