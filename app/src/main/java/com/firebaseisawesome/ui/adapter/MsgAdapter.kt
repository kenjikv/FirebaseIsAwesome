package com.firebaseisawesome.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.firebaseisawesome.R

class MsgAdapter(
    private var context: Context,
    private var dataSource: MutableList<Map<String, Any>>
) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Map<String, Any> {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.item_msg, parent, false)

        var item = getItem(position)
        val itemMsg = rowView.findViewById(R.id.itemTvMsg) as TextView
        val itemTvEmail = rowView.findViewById(R.id.itemTvEmail) as TextView

        itemMsg.text = item.get("mensaje") as CharSequence?
        itemTvEmail.text = item.get("email") as CharSequence?
        rowView.tag = item

        return rowView
    }
}