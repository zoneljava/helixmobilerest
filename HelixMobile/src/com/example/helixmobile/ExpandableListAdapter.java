package com.example.helixmobile;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Activity context;
    private HashMap<String, ArrayList<String>> masiCollections;
    private ArrayList<String> masi;
    
    
    
    public ExpandableListAdapter(Activity context, ArrayList<String> masi, HashMap<String, ArrayList<String>> masiCollections) {
        this.context = context;
        this.masiCollections = masiCollections;
        this.masi = masi;
    }
    
    
    
	public ExpandableListAdapter() {
		super();
	}



	@Override
	public int getGroupCount() {
		return masi.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return masiCollections.get(masi.get(groupPosition)).size();
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
        item.setTypeface(null, Typeface.BOLD);
        item.setText(masaName);
        return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		 final String masa = (String) getChild(groupPosition, childPosition);
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
//	                                ArrayList<String> child = masiCollections.get(masi.get(groupPosition));
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
	 
	        item.setText(masa);
	        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
