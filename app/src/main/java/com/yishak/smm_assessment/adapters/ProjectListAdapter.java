package com.yishak.smm_assessment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.Project;
import java.util.List;

public class ProjectListAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<BaseTransaction> projects;

    public ProjectListAdapter(Context context, List<BaseTransaction> projects)
    {
        this.context = context;
        this.projects = projects;

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;

        rowView = inflater.inflate(R.layout.item_project, null);

        TextView projectName = rowView.findViewById(R.id.txtItemProjectName);
        TextView projectClient = rowView.findViewById(R.id.txtItemProjectClient);
        TextView date = rowView.findViewById(R.id.txtItemDateCreated);
        TextView stat = rowView.findViewById(R.id.txtItemStatus);

        projectName.setText("Project - " + projects.get(i).getProjectName());
        projectClient.setText("Client - " + projects.get(i).getProjectClient());
        date.setText("Start Date - " + projects.get(i).getProjectStartDate());
        stat.setText("Status - Assessment Completed");

        return rowView;
    }
}
