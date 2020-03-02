package top.codeplot.luxunquotation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_list_view.*

class ListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        val type = intent.extras?.get("type") as String
        if (type == "result") {
            val data = intent.extras?.get("data") as ArrayList<Result>
            val adapter = MyListViewAdapter(data, this)
            list_view.adapter = adapter
            list_view.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    val intent = Intent(this@ListView, ViewPassageActivity::class.java)
                    intent.putExtra("title", data[position].volume + " " + data[position].title)
                    intent.putExtra("text", TextSearch.getText(data[position].id))
                    startActivity(intent)
                }
            }
        } else if (type == "volume"){
            val data = intent.extras?.get("data") as Array<Array<String>>
            val adapter = PassageListAdapter(data, this)
            list_view.adapter = adapter
            list_view.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    val intent = Intent(this@ListView,ViewPassageActivity::class.java)
                    intent.putExtra("title", data[position][0] + " " + data[position][1])
                    intent.putExtra("text", TextSearch.getText(position))
                    startActivity(intent)
                }
            })
        }
    }
}