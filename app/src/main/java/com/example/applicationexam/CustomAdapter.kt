package com.example.applicationexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ListView

class CustomAdapter(context: Context, private val dataList: List<Pair<String, Int>>) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_layout, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val (name, itog) = dataList[position]
        viewHolder.textViewName.text = name
        viewHolder.textViewItog.text = "$itog/100"

        return view
    }

    private class ViewHolder(view: View) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewItog: TextView = view.findViewById(R.id.textViewItog)
    }
}
