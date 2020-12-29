package com.example.covid_19.ui.news;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.covid_19.R;

/**
 * An adapter that links a ExpandableListView with the underlying data.
 * The implementation of this interface will provide access to the data of the children (categorized by groups),
 * and also instantiate Views for children and groups.
 *
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    private HashMap<String, List<String>> listDataChild;//child title

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other children in the group
     * @return Object       the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }


    /**
     * Gets the ID for the given child within the given group.
     * This ID must be unique across all children within the group.
     * The combined ID (see getCombinedChildId(long, long)) must be unique across ALL items (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which the ID is wanted
     * @return long	        the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Gets a View that displays the data for the given child within the given group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible.Should check that this view is non-null and of an appropriate type before using.
     *                      If it is not possible to convert this view to display the correct data, this method can create a new view.
     * @param parent        the parent that this view will eventually be attached to
     * @return View	        the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        //check if this view is null
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        //Child
        TextView txtListChild = (TextView) convertView.findViewById(R.id.content);
        txtListChild.setText(childText);

        return convertView;
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition  the position of the group for which the children count should be returned
     * @return int	         the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition  the position of the group
     * @return Object	     the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    /**
     * Gets the number of groups.
     *
     * @return int the number of groups
     */
    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be unique across groups.
     * The combined ID (see getCombinedGroupId(long)) must be unique across ALL items (groups and all children).
     *
     * @param groupPosition  the position of the group for which the ID is wanted
     * @return long	         the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets a View that displays the given group. This View is only for the group--
     * the Views for the group's children will be fetched using getChildView(int, int, boolean, android.view.View, android.view.ViewGroup).
     *
     * @param groupPosition the position of the group for which the View is returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check that this view is non-null and of an appropriate type before using.
     *                      If it is not possible to convert this view to display the correct data, this method can create a new view.
     * @param parent        the parent that this view will eventually be attached to
     * @return View	        the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.news_list, null);
        }

        //Header
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.newstitle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the underlying data.
     *
     * @return boolean	whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Whether the child at the specified position is selectable.
     * @param groupPosition  the position of the group that contains the child
     * @param childPosition  the position of the child within the group
     * @return boolean	     whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}