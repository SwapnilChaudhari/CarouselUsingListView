package com.example.musiccaroseul;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.musiccaroseul.MainActivity.CustomAdapter;



import android.os.AsyncTask;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class MainActivity extends Activity 
{
	LayoutParams params;
	ArrayList<String> arraylist = new ArrayList<String>();
	public LayoutInflater inflater;
	private CustomListView listview;
	private int firstVisible;
	protected boolean scrolling;
	public static  float heightScreen;
	public static  float widthScreen;
	private int childHight;
	private CustomAdapter customAdapter;
	
	OnCustomListViewScrollChangeListener onCustomListViewScrollChangeListener=new OnCustomListViewScrollChangeListener() 
	{
		
		@Override
		public void OnCustomListViewScrollChange(int x, int y, int oldX, int OldY) 
		{
		
			firstVisible=(int) (y/(childHight));
			Log.v("Tag", "changed OnCustomListViewScrollChange");
			
			
			
		}
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		display.getSize(size);
		heightScreen = size.y;
		widthScreen=size.x;
		
		childHight=(int) (heightScreen/3);
		params=new LayoutParams((int) widthScreen, childHight);
		
		
		for (int i = 0; i < 20; i++) {
			arraylist.add("Item No:" + i);
		}

		listview = (CustomListView) findViewById(R.id.lisview);

		 customAdapter=new CustomAdapter(getApplicationContext(), arraylist);
		
		listview.setOnCustomListViewScrollChange(onCustomListViewScrollChangeListener);
		
		listview.setAdapter(customAdapter);
		
		
		listview.setOnScrollListener(new OnScrollListener() 
		{
			
			

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE)
				{
					Log.v("Tag", "changed ");
					
					doAnimSmoothScrooll();
					
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	
	protected void doAnimSmoothScrooll() 
	{

		
		View viewFirstVisible=listview.getChildAt(firstVisible);
		
		
		if(viewFirstVisible!=null)
		{
			final int  top=viewFirstVisible.getTop()+childHight/2;
			Log.v("Tag", "top "+top+" "+firstVisible);
			if(top<=0)
			{
				
				
				if(viewFirstVisible.getTop()<0)
					{
			
					listview.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							listview.smoothScrollBy(top+childHight/2, 500);
							
						}
					}, 20);
					
					}
			}
		}
		
	}


	class CustomAdapter extends BaseAdapter 
	{

		private Context context;

		private ArrayList<String> myList;		

		HashMap<Integer, View> map;

		private int hightCHild=-1;

		public CustomAdapter(Context context, ArrayList<String> myList) 
		{
			this.context=context;
			this.myList=myList;
			inflater = LayoutInflater.from(context);
			map = new HashMap<Integer, View>();
		}

		@Override
		public int getCount() {
			return arraylist.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arraylist.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			convertView = map.get(position);

			ViewHolder viewholder;

			if (convertView == null) 
			{
				convertView = inflater.inflate(R.layout.mylist_view, null);
				
				RelativeLayout childLayout = (RelativeLayout)convertView;
				
				childLayout.setLayoutParams(params);
				
				viewholder = new ViewHolder();

				viewholder.textview = (TextView) convertView.findViewById(R.id.textView1);
				viewholder.textview.getLayoutParams().width=(int)widthScreen-200;
				((RelativeLayout.LayoutParams)viewholder.textview.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
				
				viewholder.iv=(ImageView) convertView.findViewById(R.id.ivImg);
				viewholder.iv.getLayoutParams().width=(int)widthScreen-200;
				((RelativeLayout.LayoutParams)viewholder.iv.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
				
				convertView.setTag(viewholder);
				map.put(position, convertView);
				
				
				
				
			}
			
			viewholder=(ViewHolder)convertView.getTag();
			viewholder.textview.setText(arraylist.get(position));
			
			
			return convertView;
		}

		
		public class ViewHolder {
			TextView textview;
			ImageView iv;
		}
		
		/*private class SetImage extends AsyncTask<Integer, Void, Void>
		{
			int pos;

			@Override
			protected Void doInBackground(Integer... params) 
			{
				pos=params[0];
				return null;
			}
			@Override
			protected void onPostExecute(Void result) 
			{
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								((ViewHolder)map.get(pos).getTag()).iv.setImageResource(R.drawable.bridge);
								
							}
						});
						
					}
				}).start();
				
				super.onPostExecute(result);
			}
			
		}*/
	}
	
	
}
