package com.lvc.colorpicker;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//com.lvc.colorpicker.CasasantaColorPicker
public class CasasantaColorPicker  extends LinearLayout {

	private static final int RED = 1;
	private static final int GREEN = 2;
	private static final int BLUE = 3;
	private static final int ALEATORY = 4;

	public static final int MODE_FEW_ALEATORY_COLORS  = 1;
	public static final int MODE_MEDIUM_ALEATORY_COLORS  = 2;
	public static final int MODE_SEVERAL_ALEATORY_COLORS  = 3;
	public static final int MODE_PRIMARY_COLORS  = 4;

	private OnColorChange onColorChange;
	private Random random = new Random();
	private String[] colors = null;
	private int mode = 0;

	public CasasantaColorPicker(Context context) {
		super(context);
		configureView(context, null, 0);
	}


	public CasasantaColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		configureView(context, attrs, 0);
	}

	public CasasantaColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr); 
		configureView(context, attrs, defStyleAttr);
	}


	public void setOnColorChange(OnColorChange onColorChange) {
		this.onColorChange = onColorChange;
	}

	private void configureView(Context context, AttributeSet attrs, int defStyleAttr) {
		loadAttributes(context, attrs); 

		ViewGroup viewGroup = (ViewGroup) inflate(context, R.layout.casasanta_color_picker_layout, null);
		LinearLayout thrail = (LinearLayout) viewGroup.findViewById(R.id.thrail);
		if(colors == null) {
			loadColorPickerByMode(context, viewGroup, thrail);	
		} else {
			loadColorPicker(colors, context, thrail);
		}

		//[#f20909, #2f09f2, #09f2f2, #1cf209, #cbf209, #f28d09]

		addView(viewGroup);
	}


	private void loadAttributes(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CasasantaColorPicker);


		int resourceID = typedArray.getResourceId(R.styleable.CasasantaColorPicker_colors, 0);
		if(resourceID != 0)
			colors =   typedArray.getResources().getStringArray(resourceID);
		mode = typedArray.getInt(R.styleable.CasasantaColorPicker_mode, 0);
		typedArray.recycle();
	}


	private void loadColorPickerByMode(Context context, ViewGroup viewGroup, LinearLayout thrail) {

		switch (mode) {
		case MODE_FEW_ALEATORY_COLORS:
			loadColorPickerAsAleatory(20, context, viewGroup, thrail);
			break;

		case MODE_MEDIUM_ALEATORY_COLORS:
			loadColorPickerAsAleatory(40, context, viewGroup, thrail);
			break;

		case MODE_SEVERAL_ALEATORY_COLORS:
			loadColorPickerAsAleatory(60, context, viewGroup, thrail);
			break;

		case MODE_PRIMARY_COLORS:
			String[] colors = getResources().getStringArray(R.array.primary_colors);
			loadColorPicker(colors, context, thrail);
			break;

		default:
			break;
		}

	}

	private void loadColorPickerAsAleatory(int numberItens, Context context, ViewGroup viewGroup, LinearLayout thrail) {
		String[] targetColors =  createColorsAleatory(numberItens);
		loadColorPicker(targetColors,context, thrail);
	}

	private void loadColorPicker(String[] targetColors,Context context, LinearLayout thrail) {
		for(String colorHex : targetColors) {
			int color = 0;
			try {
				color = Color.parseColor(colorHex);	
			} catch(IllegalArgumentException e) {
				Log.e("PROBLEMATIC COLOR ", "PROBLEMATIC COLOR  : " + colorHex);
			}

			MyCustomColorPicker colorPicker  = (MyCustomColorPicker)  inflate(context, R.layout.custom_color_picker_item, null);
			colorPicker.setFancyColor(colorHex);
			colorPicker.setBackgroundColor(color);
			colorPicker.setOnCustomColorPickerListener(new MyCustomColorPickerListener() {

				@Override
				public void changeColor(String fancyColor) {
					if(onColorChange != null) {
						Log.i("FANCY COLOR", " Color " + fancyColor);
						onColorChange.changeColor(Color.parseColor(fancyColor));
					} 
				}
			});

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dipToPixel(45), dipToPixel(45));
			layoutParams.setMargins(dipToPixel(2), dipToPixel(2), dipToPixel(2), dipToPixel(2));
			thrail.addView(colorPicker, layoutParams);
		}
	}

	private String[] createColorsAleatory(int numberItens) {
		return createColorsAleatorySpecificallyColor(numberItens, ALEATORY) ;
	}

	private String[] createColorsAleatorySpecificallyColor(int numberItens, int colorTarget) {
		String[] itens = new String[numberItens];

		for(int i = 0; i < numberItens; i++) {
			int red = getRandonNumber1To255();
			if(colorTarget == RED)
				red = 255;

			int green = getRandonNumber1To255();
			if(colorTarget == GREEN)
				green = 255;

			int blue = getRandonNumber1To255();
			if(colorTarget == BLUE)
				blue = 255;

			int color = Color.argb(255, red, green, blue);
			String colorHex = "#" + Integer.toHexString(color);
			itens[i] =  colorHex;
		}

		return itens;
	}

	public void onClickChangeColor(MyCustomColorPicker view) {
		String fancyColor = view.getFancyColor();
		Log.i("CASASANTA COLOR PICKER", " ********** " + fancyColor);
	}


	private int dipToPixel(int size) {
		Resources r = getResources();
		int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, r.getDisplayMetrics()));
		return px;
	}

	private int getRandonNumber1To255() {
		int randomValue = random.nextInt(255);
		return randomValue;
	}

}