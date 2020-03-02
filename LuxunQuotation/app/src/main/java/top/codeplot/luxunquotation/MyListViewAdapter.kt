package top.codeplot.luxunquotation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyListViewAdapter(val list: ArrayList<Result>, val context: Context): BaseAdapter() {
    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: MyViewHolder
        val v: View
        if (convertView == null) {
            v = View.inflate(context, R.layout.list_item, null)
            holder = MyViewHolder(v)
            v.tag = holder
        } else {
            v = convertView
            holder = v.tag as MyViewHolder
        }
        holder.title.text = list.get(position).volume + " " + list.get(position).title
        holder.content.text = list.get(position).sentence
        return v
    }
}

class MyViewHolder(var viewItem: View) {
    var title: TextView = viewItem.findViewById(R.id.title) as TextView
    var content: TextView = viewItem.findViewById(R.id.content) as TextView
}