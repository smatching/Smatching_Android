package appjam.sopt.a23rd.smatching

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.activity_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList




class TestActivity : AppCompatActivity() {
    val dataList : ArrayList<CondSummaryListData> by lazy {
        ArrayList<CondSummaryListData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar = findViewById<Toolbar>(R.id.act_test_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        act_test_tv.text = "맞춤지원"

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //return super.onCreateOptionsMenu(menu);
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_smatching, menu)
        return true
    }
    private fun getUserSmatchingCondResponse(){
        val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(this))
        getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
            override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
                if (response.isSuccessful && response.body()!!.data.condSummaryList.get(1) != null) {
                    getUserSmatchingListResponse(response.body()!!.data.condSummaryList.get(1).condIdx)
                    act_test_et_title.setText(response.body()!!.data.condSummaryList.get(1).condName)
                }
            }
        }
        )
    }

    private fun getUserSmatchingListResponse(condIdx: Int){
        val getUserSmatchingListResponse = networkService.getSmatchingCondsResponse(condIdx)
        getUserSmatchingListResponse.enqueue(object : Callback<GetSmatchingListResponse> {
            override fun onFailure(call: Call<GetSmatchingListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetSmatchingListResponse>, response: Response<GetSmatchingListResponse>) {
                if (response.isSuccessful) {
                    act_test_gv

                }
            }
        })
    }
}
