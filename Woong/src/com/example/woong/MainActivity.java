package com.example.woong;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
private static final int Alarm = 0;
Toast mToast;


	 //시작 설정 클래스
	 public TimePicker mTime;

	 private int mHour;
	 private int mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		  
		  setContentView(R.layout.activity_main);
		
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
	
	//TimePicker 리스너
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
	
	
	//알람의 설정
	  private void setAlarm() {
       Intent intent = new Intent(MainActivity.this, RepeatingAlarm.class);
       PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
               0, intent, 0);
       
       // We want the alarm to go off 30 seconds from now.
       long firstTime = SystemClock.elapsedRealtime(); // 0으로 세팅
       firstTime += (mHour * 1000 * 60 * 60) + (mMinute *1000 * 60); //설정시간

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
