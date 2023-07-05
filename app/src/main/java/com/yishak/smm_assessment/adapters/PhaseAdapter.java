package com.yishak.smm_assessment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.PhaseAdapterDto;

import java.util.List;

public class PhaseAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<PhaseAdapterDto> list;

    public PhaseAdapter(Context context, List<PhaseAdapterDto> list)
    {
        this.context = context;
        this.list = list;

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;

        rowView = inflater.inflate(R.layout.item_result_phase, null);

        TextView phaseName = rowView.findViewById(R.id.txtItemResultPhaseName);
        TextView maturityLevel = rowView.findViewById(R.id.txtItemResultLevel);

        String desc = "";
        switch (list.get(i).getmL())
        {
            case "1":
                desc = "Very Low";
                break;
            case "2":
                desc = "Low";
                break;
            case "3":
                desc = "Moderate";
                break;
            case "4":
                desc = "High";
                break;
            case "5":
                desc = "Very High";
                break;
        }
        phaseName.setText(list.get(i).getName());
        maturityLevel.setText("Maturity Level - " + list.get(i).getmL() + "/5 (" + desc + ")");

        return rowView;
    }
}
