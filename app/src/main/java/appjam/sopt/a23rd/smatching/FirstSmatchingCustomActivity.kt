package appjam.sopt.a23rd.smatching

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

class FirstSmatchingCustomActivity : AppCompatActivity() {
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
        getUserSmatchingCondResponse()
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
                if (response.isSuccessful && response.body()!!.data.condSummaryList.get(0) != null) {
                    getUserSmatchingListResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                    act_test_et_title.setText(response.body()!!.data.condSummaryList.get(0).condName)

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
                    var locationCount: Int = 0
                    if(response.body()!!.data.location.aborad)
                        locationCount++
                    if(response.body()!!.data.location.busan)
                        locationCount++
                    if(response.body()!!.data.location.chungbuk)
                        locationCount++
                    if(response.body()!!.data.location.chungnam)
                        locationCount++
                    if(response.body()!!.data.location.daegu)
                        locationCount++
                    if(response.body()!!.data.location.daejeon)
                        locationCount++
                    if(response.body()!!.data.location.gangwon)
                        locationCount++
                    if(response.body()!!.data.location.gwangju)
                        locationCount++
                    if(response.body()!!.data.location.incheon)
                        locationCount++
                    if(response.body()!!.data.location.jeju)
                        locationCount++
                    if(response.body()!!.data.location.jeonbuk)
                        locationCount++
                    if(response.body()!!.data.location.jeonnam)
                        locationCount++
                    if(response.body()!!.data.location.kyungbuk)
                        locationCount++
                    if(response.body()!!.data.location.kyunggi)
                        locationCount++
                    if(response.body()!!.data.location.kyungnam)
                        locationCount++
                    if(response.body()!!.data.location.sejong)
                        locationCount++
                    if(response.body()!!.data.location.seoul)
                        locationCount++
                    if(response.body()!!.data.location.ulsan)
                        locationCount++

                }
            }
        })
    }
}
