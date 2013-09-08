package com.example.woong;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Adapter class
class MyListAdapter extends BaseAdapter {

	Context maincon;
	LayoutInflater Inflater;
	ArrayList<MyItem> arSrc;
	int layout;
	// color
	int currentColor;

	public MyListAdapter(Context context, int alayout,
			ArrayList<MyItem> aarSrc, int aColor) {
		maincon = context;
		Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arSrc = aarSrc;
		layout = alayout;
		// color
		currentColor = aColor;
	}

	public int getCount() {
		return arSrc.size();
	}

	public String getItem(int position) {
		return arSrc.get(position).Address;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;

		if (convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}

		TextView textViewAddress = (TextView) convertView
				.findViewById(R.id.address);
		// typeface.
		// textViewAddress.setTypeface(Typeface.createFromAsset(getAssets(),"RixMGoB.ttf"));
		textViewAddress.setText(arSrc.get(position).Address);

		TextView textViewTemperature = (TextView) convertView
				.findViewById(R.id.temperature);
		textViewTemperature.setText(arSrc.get(position).Temperature);

		ImageView img = (ImageView) convertView.findViewById(R.id.weather_icon);
		img.setImageResource(arSrc.get(position).Weather);

		// Toggle?
		convertView.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor myEdit = ((MainActivity) MainActivity.mContext).edit;
				
				if (arSrc.get(pos).IsSelected == true) {
					v.setBackgroundResource(R.drawable.unselected_720x128);
					arSrc.get(pos).IsSelected = false;
				} else {
					// 색칠하는 함수.
					switchPaint(v, pos);
					arSrc.get(pos).IsSelected = true;
				}
				
				myEdit.putBoolean("isSelected"+pos, arSrc.get(pos).IsSelected);
				myEdit.commit();
			}
		});
		
		convertView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) MainActivity.mContext).deleteItem(pos);
				Toast.makeText(MainActivity.mContext, "Long-clicked", Toast.LENGTH_SHORT).show();
				return true;
			}
			
		});

		// change background
		if (arSrc.get(pos).IsSelected == false) {
			convertView.setBackgroundResource(R.drawable.unselected_720x128);
		} else {
			switchPaint(convertView, pos);
		}

		return convertView;
	}

	public void switchPaint(View convertView, int pos) {
		switch (currentColor) {
		case 0:
			// blue = 0;
			switch (pos) {
			case 0:
				convertView.setBackgroundResource(R.drawable.switch1_1);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.switch1_2);
				break;
			case 2:
				convertView.setBackgroundResource(R.drawable.switch1_3);
				break;
			case 3:
				convertView.setBackgroundResource(R.drawable.switch1_4);
				break;
			case 4:
				convertView.setBackgroundResource(R.drawable.switch1_5);
				break;
			case 5:
				convertView.setBackgroundResource(R.drawable.switch1_6);
				break;
			case 6:
				convertView.setBackgroundResource(R.drawable.switch1_7);
				break;
			case 7:
				convertView.setBackgroundResource(R.drawable.switch1_8);
				break;
			case 8:
				convertView.setBackgroundResource(R.drawable.switch1_9);
				break;
			case 9:
				convertView.setBackgroundResource(R.drawable.switch1_10);
				break;
			}
			break;
		case 1:
			// red = 1;
			switch (pos) {
			case 0:
				convertView.setBackgroundResource(R.drawable.switch2_1);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.switch2_2);
				break;
			case 2:
				convertView.setBackgroundResource(R.drawable.switch2_3);
				break;
			case 3:
				convertView.setBackgroundResource(R.drawable.switch2_4);
				break;
			case 4:
				convertView.setBackgroundResource(R.drawable.switch2_5);
				break;
			case 5:
				convertView.setBackgroundResource(R.drawable.switch2_6);
				break;
			case 6:
				convertView.setBackgroundResource(R.drawable.switch2_7);
				break;
			case 7:
				convertView.setBackgroundResource(R.drawable.switch2_8);
				break;
			case 8:
				convertView.setBackgroundResource(R.drawable.switch2_9);
				break;
			case 9:
				convertView.setBackgroundResource(R.drawable.switch2_10);
				break;
			}
			break;
		case 2:
			// purple = 2;
			switch (pos) {
			case 0:
				convertView.setBackgroundResource(R.drawable.switch3_1);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.switch3_2);
				break;
			case 2:
				convertView.setBackgroundResource(R.drawable.switch3_3);
				break;
			case 3:
				convertView.setBackgroundResource(R.drawable.switch3_4);
				break;
			case 4:
				convertView.setBackgroundResource(R.drawable.switch3_5);
				break;
			case 5:
				convertView.setBackgroundResource(R.drawable.switch3_6);
				break;
			case 6:
				convertView.setBackgroundResource(R.drawable.switch3_7);
				break;
			case 7:
				convertView.setBackgroundResource(R.drawable.switch3_8);
				break;
			case 8:
				convertView.setBackgroundResource(R.drawable.switch3_9);
				break;
			case 9:
				convertView.setBackgroundResource(R.drawable.switch3_10);
				break;
			}
			break;

		case 3:
			// green = 3;
			switch (pos) {
			case 0:
				convertView.setBackgroundResource(R.drawable.switch4_1);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.switch4_2);
				break;
			case 2:
				convertView.setBackgroundResource(R.drawable.switch4_3);
				break;
			case 3:
				convertView.setBackgroundResource(R.drawable.switch4_4);
				break;
			case 4:
				convertView.setBackgroundResource(R.drawable.switch4_5);
				break;
			case 5:
				convertView.setBackgroundResource(R.drawable.switch4_6);
				break;
			case 6:
				convertView.setBackgroundResource(R.drawable.switch4_7);
				break;
			case 7:
				convertView.setBackgroundResource(R.drawable.switch4_8);
				break;
			case 8:
				convertView.setBackgroundResource(R.drawable.switch4_9);
				break;
			case 9:
				convertView.setBackgroundResource(R.drawable.switch4_10);
				break;
			}
			break;
		case 4:
			// pink = 4;
			switch (pos) {
			case 0:
				convertView.setBackgroundResource(R.drawable.switch5_1);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.switch5_2);
				break;
			case 2:
				convertView.setBackgroundResource(R.drawable.switch5_3);
				break;
			case 3:
				convertView.setBackgroundResource(R.drawable.switch5_4);
				break;
			case 4:
				convertView.setBackgroundResource(R.drawable.switch5_5);
				break;
			case 5:
				convertView.setBackgroundResource(R.drawable.switch5_6);
				break;
			case 6:
				convertView.setBackgroundResource(R.drawable.switch5_7);
				break;
			case 7:
				convertView.setBackgroundResource(R.drawable.switch5_8);
				break;
			case 8:
				convertView.setBackgroundResource(R.drawable.switch5_9);
				break;
			case 9:
				convertView.setBackgroundResource(R.drawable.switch5_10);
				break;
			}
			break;
		}

	}
}
