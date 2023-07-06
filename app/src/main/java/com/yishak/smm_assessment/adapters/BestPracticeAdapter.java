package com.yishak.smm_assessment.adapters;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.model.Investment;

import java.util.HashMap;
import java.util.List;

public class BestPracticeAdapter extends BaseExpandableListAdapter
{
    private List<String> lstGroups;
    private HashMap<String, List<Investment>> lstItemsGroups;
    private Context context;

    public BestPracticeAdapter(Context context, List<String> groups, HashMap<String, List<Investment>> itemsGroups){
        // initialize class variables
        this.context = context;
        lstGroups = groups;
        lstItemsGroups = itemsGroups;
    }

    @Override
    public int getGroupCount() {
        // returns groups count
        return lstGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // returns items count of a group
        return lstItemsGroups.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // returns a group
        return lstGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // returns a group item
        return lstItemsGroups.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // return the group id
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // returns the item id of group
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // returns if the ids are specific ( unique for each group or item)
        // or relatives
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // create main items (groups)
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_best_practice_group, null);
        }

        TextView tvGroup = (TextView) convertView.findViewById(R.id.tvGroup);
//        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);

        tvGroup.setText((String) getGroup(groupPosition));
//        tvAmount.setText(String.valueOf(getChildrenCount(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // create the subitems (items of groups)

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_best_practice_item, null);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
//        TextView tvRealAmount = (TextView)convertView.findViewById(R.id.tvRealAmount);

        Investment investment = (Investment) getChild(groupPosition, childPosition);
        tvItem.setText(investment.getName());
//        tvRealAmount.setText(investment.getStringAmount());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // returns if the subitem (item of group) can be selected
        return true;
    }
}
