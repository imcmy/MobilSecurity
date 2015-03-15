package com.oc.mobilsecurity.decorator;

import com.oc.mobilsecurity.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.*;

public class CustomButtonArrow extends RelativeLayout{
	
	private TextView titleText;
	private TextView contentText;
	
	public CustomButtonArrow(Context context) {
		super(context,null);
	}

	public CustomButtonArrow(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		LayoutInflater.from(context).inflate(R.layout.button_arrow, this, true);

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
