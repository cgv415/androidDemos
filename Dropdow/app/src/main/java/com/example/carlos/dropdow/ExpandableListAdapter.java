package com.example.carlos.dropdow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 12/04/2017
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<String> langs;
    Map<String,ArrayList<String>> topics;

    public ExpandableListAdapter(Context context, ArrayList<String> langs, Map<String, ArrayList<String>> topics) {
        this.context = context;
        this.langs = langs;
        this.topics = topics;
    }

    @Override
    public int getGroupCount() {
        return langs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return topics.get(langs.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return langs.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return topics.get(langs.get(groupPosition)).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        String lang = (String) getGroup(groupPosition);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_parent,null);
        }

        TextView txtParent = (TextView) view.findViewById(R.id.tvParent);
        txtParent.setText(lang);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        String topic = (String) getChild(groupPosition,childPosition);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_child,null);
        }

        TextView txtChild = (TextView) view.findViewById(R.id.tvChild);
        txtChild.setText(topic);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
