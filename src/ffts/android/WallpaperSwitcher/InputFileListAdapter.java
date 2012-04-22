package ffts.android.WallpaperSwitcher;

import java.util.List;

import ffts.android.WallpaperSwitcher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//自定义Adapter
public class InputFileListAdapter extends BaseAdapter {
    
    LayoutInflater mInflater;
    
    private List<InputFileListItem> mList;
    
    public InputFileListAdapter(Context context, List<InputFileListItem> data) {
            super();
            mInflater = LayoutInflater.from(context);
            mList = data;

    }
    

    public View getView(int position, View convertView, ViewGroup parent) {
    	
            convertView = mInflater.inflate(R.layout.input_file_list_item, null);
    		TextView tN = (TextView) convertView.findViewById(R.id.multiple_name);//文件名
    		tN.setText((String)mList.get(position).getName());
        
    		TextView tP = (TextView) convertView.findViewById(R.id.multiple_path);//文件路径
    		tP.setText((String)mList.get(position).getDir());
    		
    		ImageView im = (ImageView)convertView.findViewById(R.id.tag);
    		im.setBackgroundDrawable(mList.get(position).getIcon());

    		return convertView;
    }

public int getCount() {
	// TODO Auto-generated method stub
	return mList.size();
}

public Object getItem(int arg0) {
	// TODO Auto-generated method stub
	return mList.get(arg0);
}

public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return arg0;
}
    
}