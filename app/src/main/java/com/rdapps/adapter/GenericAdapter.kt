package com.rdapps.adapter

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes


abstract class GenericAdapter<T> @JvmOverloads constructor(
    @LayoutRes private val resource: Int,
    val list: List<T>,
    val hint: String? = null
) : BaseAdapter() {
    /**
     * @return Hint set in adapter, null if not set
     */
    var hintColor = Color.LTGRAY
    private var defaultColor: Int? = null

    /**
     * @return Returns total count including hint if set
     */
    override fun getCount(): Int {
        return list.size + if (!TextUtils.isEmpty(hint)) 1 else 0
    }

    override fun getItem(position: Int): T? {
        return if (hint == null) {
            list[position]
        } else if (position > 0) {
            list[position + 1]
        } else {
            null
        }
    }

    /**
     * @param displayText displayText
     * @return Object
     */
    fun getItem(displayText: String): T? {
        for (t in list) {
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

    private fun getTextViewFromView(convertView: View): TextView {
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
     * @return Text to be shown
     */
    abstract fun getDisplayText(item: T): String

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
     * @return Position of the text in the adapter, 0 if not present
     */
    fun getPosition(text: String): Int {
        for (i in list.indices) {
            if (getDisplayText(list[i]) == text) return if (hint == null) i else i + 1
        }
        return 0
    }
}