package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SmatchingCustomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smatching_custom_edd_activty)
        //val toolbar = act_smatching_custom_toolbar
        //setSupportActionBar(toolbar)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        //act_smatching_custom_tv_title.setText("맞춤지원")

    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/
}
