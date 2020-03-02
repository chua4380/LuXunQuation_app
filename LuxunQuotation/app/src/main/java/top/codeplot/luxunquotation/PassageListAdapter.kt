package top.codeplot.luxunquotation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PassageListAdapter(val list: Array<Array<String>>, val context: Context): BaseAdapter() {
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
        holder.title.text = list[position][0]
        holder.content.text = list[position][1]
        return v
    }
}