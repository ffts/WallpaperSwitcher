package ffts.android.WallpaperSwitcher;

import java.io.File;

import ffts.android.WallpaperSwitcher.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class WallpaperSwitcher extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),R.string.have_a_try , Toast.LENGTH_LONG).show();
		return new wallpaper();
	}
	
	class wallpaper extends Engine implements OnGestureListener, OnSharedPreferenceChangeListener{
		
		private GestureDetector gestureScanner;
		private boolean mVisible;
		String filename = null;
		private SharedPreferences sp;

		String path = null;
		File papers = null;
	    String[] names = null;
	    int length = 0;
		int count = 0;
		
		wallpaper(){
			sp = WallpaperSwitcher.this.getSharedPreferences("wall",MODE_PRIVATE);
			sp.registerOnSharedPreferenceChangeListener(this);
			onSharedPreferenceChanged(sp, null);
		}

		void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    drawPaper(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
        }
		
		void drawPaper(Canvas c){
			Bitmap paper = BitmapFactory.decodeFile(path);
			if(paper==null){
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inSampleSize = 1;
				paper = BitmapFactory.decodeResource(getResources(), R.drawable.saber_lily, opts);
			}
        	c.drawBitmap(paper, 0, 0, null);
		}
		void switchPaper(){
				if(names!=null){
					path = papers.getAbsolutePath()+"/"+names[count];
					Log.i("names",path);
					count++;
					if(count == length-1){
						count = 0;
					}
				}
				
//	    	Log.i("change", "changed");
	    }
		public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // By default we don't get touch events, so enable them.
            setTouchEventsEnabled(true);
            gestureScanner = new GestureDetector(this);
            //加载指定路径的图片
            loadPapers();
        }
		
		//加载指定路径的图片
		protected void loadPapers(){
			filename = sp.getString("dir", "");
            papers = new File(filename);
            names = papers.list();
            if(names!=null){
            	length = names.length;
            }
		}
		
		//重新加载指定路径的图片，并将count归0，即移动到第一张图片
		protected void reLoadPapers(){
			filename = sp.getString("dir", "");
            papers = new File(filename);
            names = papers.list();
            if(names!=null){
            	length = names.length;
            }
            count = 0;
		}

		public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                drawFrame();
            }
        }
		
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			switchPaper();
			drawFrame();
			return false;
		}

		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			super.onTouchEvent(event);
			gestureScanner.onTouchEvent(event);
		}

		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub
//			Log.i("wall", "setting_changed:"+sp.getString("dir", ""));
			//设置改变的时候重新加载图片
			filename = sp.getString("dir", "");
        	reLoadPapers();
        	drawFrame();
//        	Log.i("wall", "change dirs:"+sp.getString("dir", ""));
		}
		
	}
	
}