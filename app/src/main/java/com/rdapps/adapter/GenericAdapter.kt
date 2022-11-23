package com.rdapps.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GenericAdapter<T> extends BaseAdapter {

    private String hint = null;
    private int hintColor = Color.LTGRAY;
    private Integer defaultColor = null;
    private List<T> objects;
    private int resource;

    public GenericAdapter(int resource, @NonNull List<T> objects) {
        this.resource = resource;
        this.objects = objects;
    }

    public GenericAdapter(int resource, @NonNull List<T> objects, @NonNull String hint) {
        this.resource = resource;
        this.hint = hint;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size() + (!TextUtils.isEmpty(hint) ? 1 : 0);
    }

    @Override
    public T getItem(int position) {
        if (hint == null) {
            return objects.get(position);
        } else if (position > 0) {
            return objects.get(position + 1);
        } else {
            return null;
        }
    }

    /**
     * @param displayText displayText
     * @return custom object
     */
    @Nullable
    public T getItem(String displayText) {
        for (T t : objects) {
            if (getDisplayText(t).equals(displayText)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        TextView tv = getTextViewFromView(convertView);

        if (defaultColor == null) {
            defaultColor = tv.getTextColors().getDefaultColor();
        }

        if (hint != null && position == 0) {
            tv.setText(hint);
            tv.setTextColor(hintColor);
        } else {
            tv.setText(getDisplayText(getItem(position)));
            tv.setTextColor(defaultColor);
        }
        return convertView;
    }

    /**
     * Override this while using custom layout
     *
     * @param convertView view of the layout
     * @return TextView where displayText will be set
     */
    public TextView getTextViewFromView(View convertView) {
        return (TextView) convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (hint != null) {
            if (position == 0) {
                return false;
            } else {
                return super.isEnabled(position);
            }
        } else {
            return super.isEnabled(position);
        }
    }

    public List<T> getObjects() {
        return objects;
    }

    /**
     * Override this method while using Custom Objects
     *
     * @param item custom object item
     * @return text to be shown
     */
    public String getDisplayText(T item) {
        return item.toString();
    }

    public void setHintColor(int hintColor) {
        this.hintColor = hintColor;
    }

    public int getHintColor() {
        return hintColor;
    }

    /**
     * @return Hint set in adapter, null if not set
     */
    @Nullable
    public String getHint() {
        return hint;
    }

    /**
     * Use only for Spinner
     *
     * @param selectedItem use spinner.getSelectedItem()
     * @return true if hint is selected
     */
    public boolean isHintSelected(Object selectedItem) {
        return hint != null && selectedItem instanceof String && hint.equals(selectedItem.toString());
    }

    /**
     * @param text display text
     * @return position of the text in the adapter, 0 if not present
     */
    public int getPosition(String text) {
        for (int i = 0; i < objects.size(); i++) {
            if (getDisplayText(objects.get(i)).equals(text))
                return (hint == null) ? i : i + 1;
        }
        return 0;
    }
}
