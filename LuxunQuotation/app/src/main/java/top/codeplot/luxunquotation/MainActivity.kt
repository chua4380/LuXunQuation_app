package top.codeplot.luxunquotation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val f_data = applicationContext.assets.open("data_text")
        val f_map = applicationContext.assets.open("data_map_r").bufferedReader()
        TextSearch.load_file(f_data, f_map)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val key_word: String = editText.text.toString();
                val arr: ArrayList<Result>
                arr = TextSearch.search_text(key_word, 0, 300)
                val intent = Intent(this@MainActivity, ListView::class.java)
                intent.putExtra("type", "result")
                intent.putExtra("data", arr)
                startActivity(intent)
            }
        })
        button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(this@MainActivity, ListView::class.java)
                intent.putExtra("type", "volume")
                intent.putExtra("data", TextSearch.getTitle())
                startActivity(intent)
            }
        })
    }
}
