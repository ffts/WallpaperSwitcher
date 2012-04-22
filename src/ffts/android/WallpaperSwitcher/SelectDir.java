package ffts.android.WallpaperSwitcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ffts.android.WallpaperSwitcher.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SelectDir extends Activity {
	
	private List<InputFileListItem>	directoryEntries = new ArrayList<InputFileListItem>();
	private File				currentDirectory = new File("/sdcard/");
	//ListView
	ListView fileList;
	InputFileListAdapter listAdapter;
//	Intent it = this.getIntent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_file_list);
		fileList = (ListView) findViewById(R.id.listview);
		browseToRoot();
		
		
		
		//设置ListView被点击时的监听
		fileList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> l, View v, int position,long id) {
				// TODO Auto-generated method stub
				String selectedFileString = directoryEntries.get(position).getName();
				
				if (selectedFileString.equals(getString(R.string.up_one_level)))
				{
					//返回上一级目录
					upOneLevel();
				}
				else
				{
							
					File clickedFile = null;
					Log.i("InputFile", directoryEntries.get(position).getDir());
					clickedFile = new File(directoryEntries.get(position).getDir());
					if(directoryEntries.get(position).isFolder()){
						browseTo(clickedFile);
					}
					else{
						//ListView点击事件
						/*String fileName = directoryEntries.get(position).getDir();
						Log.i("filename",fileName);
						Intent txtIntent = new Intent();
						txtIntent.putExtra("filename", fileName);
						if(fileName.endsWith(".txt")){
							txtIntent.setClass(Readroid.this, TxtActivity.class);
							startActivity(txtIntent);
						}else if(fileName.endsWith(".doc")){
							txtIntent.setClass(Readroid.this, WordActivity.class);
							startActivity(txtIntent);
						}else Toast.makeText(getBaseContext(), "对不起，暂不支持该文件类型", Toast.LENGTH_SHORT).show();*/
					}

				}
				
			}
			
		});
		
		Button select = (Button) findViewById(R.id.select);
		select.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra("dir", currentDirectory.getAbsolutePath());
				it.setClass(SelectDir.this, WallpaperSwitcherSettings.class);
				SelectDir.this.setResult(RESULT_FIRST_USER, it);
				SelectDir.this.finish();
			}
		});
	}
	//浏览SD卡的根目录
	private void browseToRoot() 
	{
		browseTo(new File("/sdcard"));
    }
	//返回上一级目录
	private void upOneLevel()
	{
		if(this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}
	//浏览指定的目录
	private void browseTo(final File file)
	{
		this.setTitle(file.getAbsolutePath());
		if (file.isDirectory())
		{
			this.currentDirectory = file;
			fill(file.listFiles());
		}
	}
	
	//List的源
	private void fill(File[] files)
	{
		//清空列表
		this.directoryEntries.clear();
		
		Drawable AudioIcon = getResources().getDrawable(R.drawable.image);
		Drawable FolderIcon = getResources().getDrawable(R.drawable.folder);
		for (File currentFile : files)
		{
			//取得文件名
			String fileName = currentFile.getName();
			
			if(currentFile.isDirectory()){
				this.directoryEntries.add(new InputFileListItem(fileName,currentFile.getAbsolutePath(), FolderIcon,true));
			}
			else if(checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingImage))){//过滤掉图片以外的文件
				this.directoryEntries.add(new InputFileListItem(fileName,currentFile.getAbsolutePath(), AudioIcon,false));
			}
		}
		Collections.sort(this.directoryEntries,new Comparator<InputFileListItem>() { 
	        public int compare(InputFileListItem a, InputFileListItem b) { 
	            return a.getDir().compareToIgnoreCase(b.getDir()); 
	            } 
		});
		
		//如果不是根目录则添加上一级目录项
				if (this.currentDirectory.getParent() != null){
					this.directoryEntries.add(0,new InputFileListItem(getString(R.string.up_one_level),getString(R.string.none), getResources().getDrawable(R.drawable.uponelevel),true));
				}
				
		//创建Adapter
		listAdapter = new InputFileListAdapter(this,this.directoryEntries);
		//添加Adapter
		fileList.setAdapter(listAdapter);
	}
	

	//通过文件名判断是什么类型的文件
	private boolean checkEndsWithInStringArray(String checkItsEnd, 
					String[] fileEndings)
	{
		for(String aEnd : fileEndings)
		{
			if(checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
}
