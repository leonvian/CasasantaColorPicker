package com.lvc.casasantacolorpicker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lvc.colorpicker.CasasantaColorPicker;
import com.lvc.colorpicker.MyCustomColorPicker;
import com.lvc.colorpicker.OnColorChange;

public class MainActivity extends Activity implements OnColorChange {

	private View viewTarget = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewTarget = findViewById(R.id.target);

		String[] colors = getResources().getStringArray(R.array.leon_choice_colors);
		Log.i("COLORS", "COLORS: " + colors);
		for(CharSequence charSequence : colors) {
			Log.i("COLORS", "COLORS: " + charSequence);
		}

		CasasantaColorPicker casasantaColorPicker = (CasasantaColorPicker) findViewById(R.id.casasanta_color_picker);
		casasantaColorPicker.setOnColorChange(this);
	}

	@Override
	public void changeColor(int newColor) {
		viewTarget.setBackgroundColor(newColor);
	}

	public void onClickChangeColor(MyCustomColorPicker view) {
		String fancyColor = view.getFancyColor();
		Log.i("MAIN ACTIVITY", " ********** " + fancyColor);
	}

}
