package com.example.musiccaroseul;





import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CustomListView extends ListView
{

	private final Camera mCamera = new Camera();
	private final Matrix mMatrix = new Matrix();
	private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
	private int hightCHild=-1;
	private float rotationFactor=-1;
	private float childRotation;
	
	
	private OnCustomListViewScrollChangeListener onCustomListViewScrollChanged;
	
	public CustomListView(Context context) {
		super(context);
		
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
	}
	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		
	}
	

	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) 
	{
		
		int childPos=getPositionForView(child);
		
		Bitmap bitmap = getChildBitmap(child,childPos);
	
		final int top = child.getTop();
		
		final int left = child.getLeft();
		
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
		childRotation=rotationFactor*child.getTop();
		Log.v("Tag", "rotX: "+childRotation+" pos "+childPos);
       
		rotateChild(mMatrix,childRotation);
		
		mMatrix.preTranslate(-childCenterX,- childCenterY);
		
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

	private void rotateChild(final Matrix outMatrix, float childRotationX) {
		
		mCamera.save();
		
		mCamera.rotateX( childRotationX);
		
		mCamera.getMatrix(outMatrix);
		mCamera.restore();

		
	}

	private Bitmap getChildBitmap(final View child,int pos) 
	{
		
		Bitmap bitmap = child.getDrawingCache();
			if (bitmap == null) 
			{
				
				child.buildDrawingCache();
				bitmap = child.getDrawingCache();
				
			}
		
		return bitmap;
	}



}
