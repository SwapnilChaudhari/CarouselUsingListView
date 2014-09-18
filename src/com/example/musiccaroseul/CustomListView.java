package com.example.musiccaroseul;





import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CustomListView extends ListView
{

	private HashMap<Integer, Bitmap> map;
	private OnCustomListViewScrollChangeListener onCustomListViewScrollChanged;
	public CustomListView(Context context) {
		super(context);
		map=new HashMap<Integer, Bitmap>();
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		map=new HashMap<Integer, Bitmap>();
		
	}
	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		map=new HashMap<Integer, Bitmap>();
		
	}
	

	private final Camera mCamera = new Camera();
	private final Matrix mMatrix = new Matrix();
	/** Paint object to draw with */
	private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
	private int hightCHild=-1;
	private float rotationFactor=-1;
	private float childRotation;
	private int lastFirstVisible;
	//private double scaleFactorY=-1;
	
	private int firatVisible;
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		
		//setChildParams(child);
		firatVisible=getFirstVisiblePosition();
        
		int childPos=getPositionForView(child);
		
		
		Bitmap bitmap = getChildDrawingCache(child,childPos);
		// (top,left) is the pixel position of the child inside the list
		final int top = child.getTop();
		final int left = child.getLeft();
		// center point of child
		final int childCenterY = child.getHeight() / 2;
		final int childCenterX = child.getWidth() / 2;
		// center of list
		final int parentCenterY = getHeight() / 2;
		// center point of child relative to list
		final int absChildCenterY = child.getTop() + childCenterY;
		// distance of child center to the list center
		final int distanceY = parentCenterY - absChildCenterY;
		// radius of imaginary cirlce
		final int r = getHeight() / 2;

		
		if(hightCHild==-1) hightCHild=child.getHeight();
		if(rotationFactor==-1)rotationFactor=(160/(MainActivity.heightScreen+hightCHild+100));
		//if(degreeFactor==-1)degreeFactor=90/(MainActivity.heightscreen);
		//childRotation=(child.getTop()+MainActivity.heightscreen+400)*(160/(MainActivity.heightscreen))+80;
		childRotation=rotationFactor*child.getTop();
		Log.v("Tag", "rotX: "+childRotation+" pos "+childPos);
        //degree=(child.getTop())*(degreeFactor);
        
        
        double scaleMax=2.0/(top);
		
		
		if(lastFirstVisible!=firatVisible)
		{
			
		lastFirstVisible=firatVisible;
		}
		
		prepareMatrix(mMatrix, distanceY, r,childRotation);
		
		mMatrix.preTranslate(-childCenterX,- childCenterY);
		
		//mMatrix.postScale(1.0f,(float)(2.0-scaleMax));// min scale-1 max scale 1.5
		
		mMatrix.postTranslate(childCenterX, childCenterY);
		
		mMatrix.postTranslate(left, top);
		
		canvas.drawBitmap(bitmap, mMatrix, mPaint);
		
		return  true;

	}

	public void setOnCustomListViewScrollChange(OnCustomListViewScrollChangeListener onCustomListViewScrollChanged)
	{
		this.onCustomListViewScrollChanged=onCustomListViewScrollChanged;
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY) 
	{
		if(onCustomListViewScrollChanged!=null)onCustomListViewScrollChanged.OnCustomListViewScrollChange(x, y, oldX, oldY);
		super.onScrollChanged(x, y, oldX, oldY);
		
		
	}

	private void prepareMatrix(final Matrix outMatrix, int distanceY, int r, float childRotationX) {
		// clip the distance
		final int d = Math.min(r, Math.abs(distanceY));
		Math.sqrt((r * r) - (d * d));
		mCamera.save();
		
		
		mCamera.rotateX( childRotationX);
		
		mCamera.getMatrix(outMatrix);
		mCamera.restore();

		
	}

	private Bitmap getChildDrawingCache(final View child,int pos) 
	{
		/*Bitmap bitmap=map.get(pos);
		if(bitmap!=null)
		{
			return bitmap;
		}
		else
		{*/
		Bitmap bitmap = child.getDrawingCache();
			if (bitmap == null) 
			{
				/*child.setDrawingCacheEnabled(false);*/
				child.buildDrawingCache();
				bitmap = child.getDrawingCache();
				map.put(pos, bitmap);
			}
		//}
		return bitmap;
	}

	/*private LightingColorFilter calculateLight(final float rotation) {
		final double cosRotation = Math.cos(Math.PI * rotation / 180);
		int intensity = AMBIENT_LIGHT + (int) (DIFFUSE_LIGHT * cosRotation);
		int highlightIntensity = (int) (SPECULAR_LIGHT * Math.pow(cosRotation,
				SHININESS));
		if (intensity > MAX_INTENSITY) {
			intensity = MAX_INTENSITY;
		}
		if (highlightIntensity > MAX_INTENSITY) {
			highlightIntensity = MAX_INTENSITY;
		}
		final int light = Color.rgb(intensity, intensity, intensity);
		final int highlight = Color.rgb(highlightIntensity, highlightIntensity,
				highlightIntensity);
		return new LightingColorFilter(light, highlight);
	}*/
	

}
