package com.oc.mobilsecurity.decorator;

import com.oc.mobilsecurity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.*;

public class CustomButton extends RelativeLayout {

	private TextView titleText;
	private TextView contentText;
	
	public CustomButton(Context context) {
		super(context,null);
	}

	public CustomButton(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		LayoutInflater.from(context).inflate(R.layout.button, this, true);

		this.titleText = (TextView) findViewById(R.id.customButtonText1);
		this.contentText = (TextView) findViewById(R.id.customButtonText2);

		this.setClickable(true);
		this.setFocusable(true);
	}

	public void setTitleText(String text) {
		titleText.setText(text);
	}
	
	public void setContentText(String text) {
		contentText.setText(text);
	}
}
