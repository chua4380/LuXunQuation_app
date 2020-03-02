package top.codeplot.luxunquotation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_passage.*

class ViewPassageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_passage)
        view_passage_title.text = intent.extras?.get("title") as String
        view_passage_text.text = intent.extras?.get("text") as String
    }
}
