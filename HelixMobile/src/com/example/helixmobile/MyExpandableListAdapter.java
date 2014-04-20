package com.example.helixmobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Activity context;
    private HashMap<String, ArrayList<String>> masiCollections;
    private List<String> masi;
    
    public MyExpandableListAdapter(Activity context, List<String> masi, HashMap<String, ArrayList<String>> masiCollections) {
        this.context = context;
        this.masiCollections = masiCollections;
        this.masi = masi;
    }
    
    public MyExpandableListAdapter() {
		super();
	}


	@Override
	public int getGroupCount() {
		return masi.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Log.v("GET CHILDREN COUNT ---> :  ", Arrays.asList(masiCollections) + "| " + Arrays.asList(masi));
		return masiCollections.get(masi.get(groupPosition)).size();
		//return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return masi.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return masiCollections.get(masi.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		String masaName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.masa);
        Typeface typeFace = Typeface.createFromAsset( convertView.getContext().getAssets() ,"fonts/zonelmak.ttf");
        item.setTypeface(typeFace, Typeface.BOLD);
        
        item.setText(masaName);
        return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		 final String masiGroup = (String) getChild(groupPosition, childPosition);
	        LayoutInflater inflater = context.getLayoutInflater();
	 
	        if (convertView == null) {
	            convertView = inflater.inflate(   R.layout.child_item   , null);
	        }
	 
	        TextView item = (TextView) convertView.findViewById(R.id.masa);
	        
//	        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
	        
//	        delete.setOnClickListener(new OnClickListener() {
//	 
//	            public void onClick(View v) {
//	                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//	                builder.setMessage("Do you want to remove?");
//	                builder.setCancelable(false);
//	                builder.setPositiveButton("Yes",
//	                        new DialogInterface.OnClickListener() {
//	                            public void onClick(DialogInterface dialog, int id) {
//	                                List<String> child = masiCollections.get(masi.get(groupPosition));
//	                                child.remove(childPosition);
//	                                notifyDataSetChanged();
//	                            }
//	                        });
//	                builder.setNegativeButton("No",
//	                        new DialogInterface.OnClickListener() {
//	                            public void onClick(DialogInterface dialog, int id) {
//	                                dialog.cancel();
//	                            }
//	                        });
//	                AlertDialog alertDialog = builder.create();
//	                alertDialog.show();
//	            }
//	        });
	        Typeface typeFace = Typeface.createFromAsset( convertView.getContext().getAssets() ,"fonts/zonelmak.ttf");
	        item.setTypeface(typeFace,Typeface.BOLD);
	        item.setText(masiGroup);
	        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
