/*
 *
 *             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 *             $                                                   $
 *             $                       _oo0oo_                     $
 *             $                      o8888888o                    $
 *             $                      88" . "88                    $
 *             $                      (| -_- |)                    $
 *             $                      0\  =  /0                    $
 *             $                    ___/`-_-'\___                  $
 *             $                  .' \\|     |$ '.                 $
 *             $                 / \\|||  :  |||$ \                $
 *             $                / _||||| -:- |||||- \              $
 *             $               |   | \\\  -  $/ |   |              $
 *             $               | \_|  ''\- -/''  |_/ |             $
 *             $               \  .-\__  '-'  ___/-. /             $
 *             $             ___'. .'  /-_._-\  `. .'___           $
 *             $          ."" '<  `.___\_<|>_/___.' >' "".         $
 *             $         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       $
 *             $         \  \ `_.   \_ __\ /__ _/   .-` /  /       $
 *             $     =====`-.____`.___ \_____/___.-`___.-'=====    $
 *             $                       `=-_-='                     $
 *             $     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   $
 *             $                                                   $
 *             $          Buddha Bless         Never Bug           $
 *             $                                                   $
 *             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 *
 *  Copyright (C) 2017 The Mad x Studio's Android Project by Jackie
 */

package com.madxstudio.libs.weight;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.madxstudio.libs.R;
import com.madxstudio.libs.tools.Constants;
import com.madxstudio.libs.tools.PreferencesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Suggestions Adapter.
 *
 * @author Miguel Catalan Bañuls
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> data;
    private Set<String> suggestions = new HashSet<>();
    private Drawable suggestionIcon;
    private LayoutInflater inflater;
    private boolean ellipsize;

    public SearchAdapter(Context context, Collection<String> suggestions) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.suggestions.addAll(suggestions);
    }

    public SearchAdapter(Context context, Collection<String> suggestions, Drawable
            suggestionIcon, boolean ellipsize) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.suggestions.addAll(suggestions);
        this.suggestionIcon = suggestionIcon;
        this.ellipsize = ellipsize;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<String> searchData = new ArrayList<>();
                if (!TextUtils.isEmpty(constraint)) {

                    // Retrieve the autocomplete results.

                    // Assign the data to the FilterResults
                    for (String string : suggestions) {
                        if (string.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            searchData.add(string);
                        }
                    }
                    filterResults.values = searchData;
                    filterResults.count = searchData.size();
                } else {
                    searchData.addAll(suggestions);
                    filterResults.values = searchData;
                    filterResults.count = searchData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    data = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        if (data.isEmpty()) {
            return 0;
        } else if (data.size() > 5) {
            return 6;
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SuggestionsViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.suggest_item, parent, false);
            viewHolder = new SuggestionsViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuggestionsViewHolder) convertView.getTag();
        }

        if (position == getCount() - 1) {
            viewHolder.groupClear.setVisibility(View.VISIBLE);
            viewHolder.groupSuggestion.setVisibility(View.GONE);
        } else {
            viewHolder.groupClear.setVisibility(View.GONE);
            viewHolder.groupSuggestion.setVisibility(View.VISIBLE);
            String currentListData = (String) getItem(position);

            viewHolder.textView.setText(currentListData);
            if (ellipsize) {
                viewHolder.textView.setSingleLine();
                viewHolder.textView.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
        viewHolder.bindEntity(position, null);
        return convertView;
    }

    public void addSuggestion(String suggestion) {
        suggestions.add(suggestion);
        notifyDataSetChanged();
    }

    public void setSuggestions(Collection<String> suggestions) {
        this.suggestions = new HashSet<>(suggestions);
        data = new ArrayList<>();
        notifyDataSetChanged();
    }

    private class SuggestionsViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;
        ViewGroup groupClear;
        ViewGroup groupSuggestion;
        int position;

        public SuggestionsViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.suggestion_text);
            groupClear = (ViewGroup) convertView.findViewById(R.id.layout_clear);
            groupSuggestion = (ViewGroup) convertView.findViewById(R.id.layout_suggest);
            imageView = (ImageView) convertView.findViewById(R.id.suggestion_icon);
            if (suggestionIcon != null) {
                imageView.setImageDrawable(suggestionIcon);
            }

            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == imageView) {
                String s = data.get(position);
                data.remove(s);
                suggestions.remove(s);
                notifyDataSetChanged();
                PreferencesUtil.saveValue(Constants.KEY_SUGGESTIONS, suggestions);

            }
        }

        public void bindEntity(int position, Object entity) {
            this.position = position;
        }
    }
}