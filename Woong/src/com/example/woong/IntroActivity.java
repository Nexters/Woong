package com.example.woong;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class IntroActivity extends Activity {

	Handler h = new Handler();
	Runnable r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		r = new Runnable() {
			public void run() {
				finish();
				Intent i = new Intent(IntroActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			 }
	};

	    h.postDelayed(r,2000); //2000 ��� �ㅼ� 1000->1珥�

	}  	
	
	protected void noDestroy() {
		super.onDestroy();
		h.removeCallbacks(r);
	}
	public void onBackPressed(){} //splash �대�吏������怨쇱���諛�踰������� ��� ���. 諛깅���留�린

}

