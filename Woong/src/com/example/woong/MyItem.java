package com.example.woong;

class MyItem {
	int Weather;
	String Address;
	String Temperature;
	boolean IsSelected;

	MyItem(String aAddress, double aTemperature, int aSkyCode, boolean aIsSelected) {
		Address = aAddress;
		Temperature = aTemperature + "��C";
		Weather = aSkyCode;
		IsSelected = aIsSelected;
	}
}