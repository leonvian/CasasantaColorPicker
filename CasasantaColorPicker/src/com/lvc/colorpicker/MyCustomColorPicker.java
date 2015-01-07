package com.lvc.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

//com.loop.Paperwall.MyCustomColorPicker
//com.lvc.casasantacolorpicker.MyCustomColorPicker
public class MyCustomColorPicker extends View {

	private String fancyColor = null;
	private MyCustomColorPickerListener myCustomColorPickerListener;

	public MyCustomColorPicker(Context context) {
		super(context);
	}

	public MyCustomColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		configureAttrs(context, attrs);
	}

	public MyCustomColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		configureAttrs(context, attrs);
	}

	public void setOnCustomColorPickerListener(MyCustomColorPickerListener myCustomColorPickerListener) {
		this.myCustomColorPickerListener = myCustomColorPickerListener;
	}
 

	private void configureAttrs(final Context context, AttributeSet attrs) { 
		setOnClickListener(new OnClickListener() {
			 

			public void onClick(View v) {
				if(myCustomColorPickerListener != null) {
					myCustomColorPickerListener.changeColor(fancyColor);
				} 
			}
		});
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCustomColorPicker);
		fancyColor = a.getString(R.styleable.MyCustomColorPicker_fancyColor);		

		a.recycle();
	}

	public String getFancyColor() {
		return fancyColor;
	}
	
	public void setFancyColor(String fancyColor) {
		this.fancyColor = fancyColor;
	}


}