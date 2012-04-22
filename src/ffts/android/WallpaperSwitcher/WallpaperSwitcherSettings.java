package ffts.android.WallpaperSwitcher;

import ffts.android.WallpaperSwitcher.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

public class WallpaperSwitcherSettings extends PreferenceActivity implements OnPreferenceClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
	}
	
	@Override
    protected void onResume() {
        super.onResume();
//        Log.i("wall", "setting_resume");
    }

	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		Log.i("wall", preference.getKey());
		Toast.makeText(getApplicationContext(), preference.getKey(), Toast.LENGTH_LONG).show();
		return false;
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		//跳转到选择路径界面，需要返回一个结果，使用startActivityForResult
		Intent it = new Intent();
		it.setClass(WallpaperSwitcherSettings.this, SelectDir.class);
		startActivityForResult(it,RESULT_FIRST_USER);//requestCode必须大于0，onActivityResult才会被调用
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
	       case RESULT_FIRST_USER:
//	           Toast.makeText(getApplicationContext(), "this:"+data.getStringExtra("dir"), Toast.LENGTH_LONG).show();
	           Log.i("wall", "this:"+data.getStringExtra("dir"));
	           SharedPreferences sp = getSharedPreferences("wall", MODE_PRIVATE);
	           Editor editor = sp.edit();
	           editor.putString("dir", data.getStringExtra("dir"));
	           editor.commit();//确认修改
	           break;
	       default:
	           break;
	       }
		
	}
}