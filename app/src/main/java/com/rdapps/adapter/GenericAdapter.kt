package com.rdapps.adapter

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes


class GenericAdapter<T> @JvmOverloads constructor(
    @LayoutRes val resource: Int,
    val objects: List<T>,
    val hint: String? = null
) : BaseAdapter() {
    /**
     * @return Hint set in adapter, null if not set
     */
    var hintColor = Color.LTGRAY
    private var defaultColor: Int? = null

    override fun getCount(): Int {
        return objects.size + if (!TextUtils.isEmpty(hint)) 1 else 0
    }

    override fun getItem(position: Int): T? {
        return if (hint == null) {
            objects[position]
        } else if (position > 0) {
            objects[position + 1]
        } else {
            null
        }
    }

    /**
     * @param displayText displayText
     * @return custom object
     */
    fun getItem(displayText: String): T? {
        for (t in objects) {
            if (getDisplayText(t) == displayText) {
                return t
            }
        }
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        parent ?: return null

        val view = convertView ?: LayoutInflater.from(
            parent.context
        ).inflate(resource, parent, false)

        val tv = getTextViewFromView(view)
        if (defaultColor == null) {
            defaultColor = tv.textColors.defaultColor
        }
        if (hint != null && position == 0) {
            tv.text = hint
            tv.setTextColor(hintColor)
        } else {
            tv.text = getItem(position)?.let { getDisplayText(it) }
            tv.setTextColor(defaultColor!!)
        }
        return convertView
    }

    /**
     * Override this while using custom layout
     *
     * @param convertView view of the layout
     * @return TextView where displayText will be set
     */
    fun getTextViewFromView(convertView: View): TextView {
        return convertView as TextView
    }

    override fun isEnabled(position: Int): Boolean {
        return if (hint != null) {
            if (position == 0) {
                false
            } else {
                super.isEnabled(position)
            }
        } else {
            super.isEnabled(position)
        }
    }

    /**
     * Override this method while using Custom Objects
     *
     * @param item custom object item
     * @return text to be shown
     */
    fun getDisplayText(item: T): String {
        return item.toString()
    }

    /**
     * Use only for Spinner
     *
     * @param selectedItem use spinner.getSelectedItem()
     * @return true if hint is selected
     */
    fun isHintSelected(selectedItem: Any): Boolean {
        return hint != null && selectedItem is String && hint == selectedItem.toString()
    }

    /**
     * @param text display text
     * @return position of the text in the adapter, 0 if not present
     */
    fun getPosition(text: String): Int {
        for (i in objects.indices) {
            if (getDisplayText(objects[i]) == text) return if (hint == null) i else i + 1
        }
        return 0
    }
}