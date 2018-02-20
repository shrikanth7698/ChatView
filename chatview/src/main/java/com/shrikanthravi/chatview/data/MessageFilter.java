package com.shrikanthravi.chatview.data;


import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */



class MessageFilter extends Filter {

    private List<Message> messageList;
    private List<Message> filteredMessageList;
    private MessageAdapter adapter;

    public MessageFilter(List<Message> messageList, MessageAdapter adapter) {
        this.adapter = adapter;
        this.messageList = messageList;
        this.filteredMessageList = new ArrayList();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredMessageList.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredMessageList
        for (final Message item : messageList) {
            if (item.getBody().toLowerCase().trim().contains(constraint.toString().toLowerCase())) {
                filteredMessageList.add(item);
            }
        }

        results.values = filteredMessageList;
        results.count = filteredMessageList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredMessageList);
        adapter.notifyDataSetChanged();
    }


}

