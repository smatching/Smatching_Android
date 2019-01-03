package appjam.sopt.a23rd.smatching

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.media.Image
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
                    fragment_smatching_custom_location.setTextColor(resources.getColor(R.color.colorBlue))
                    fragment_smatching_custom_location.text = locationCount.toString()

                    if(response.body()!!.data.age.forty_more)
                        act_test_iv_age40.setImageResource(R.drawable.btn_pick_age_40_click_blue)
                    if(response.body()!!.data.age.twenty_forty)
                        act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age_2039_click_blue)
                    if(response.body()!!.data.age.twenty_less)
                        act_test_iv_age20.setImageResource(R.drawable.btn_pick_age_20_click_blue)

                    var periodCount = 0
                    if(response.body()!!.data.period.zero_one) {
                        periodCount++
                        act_test_iv_01.setImageResource(R.drawable.btn_pick_year_01_click)
                    }
                    if(response.body()!!.data.period.one_two) {
                        periodCount++
                        act_test_iv_12.setImageResource(R.drawable.btn_pick_year_12_click)
                    }
                    if(response.body()!!.data.period.two_three) {
                        periodCount++
                        act_test_iv_23.setImageResource(R.drawable.btn_pick_year_23_click)
                    }
                    if(response.body()!!.data.period.three_four) {
                        periodCount++
                        act_test_iv_34.setImageResource(R.drawable.btn_pick_year_34_click)
                    }
                    if(response.body()!!.data.period.four_five) {
                        periodCount++
                        act_test_iv_45.setImageResource(R.drawable.btn_pick_year_45_click)
                    }
                    if(response.body()!!.data.period.five_six) {
                        periodCount++
                        act_test_iv_56.setImageResource(R.drawable.btn_pick_year_56_click)
                    }
                    if(response.body()!!.data.period.six_seven) {
                        periodCount++
                        act_test_iv_67.setImageResource(R.drawable.btn_pick_year_67_click)
                    }
                    if(response.body()!!.data.period.seven_more) {
                        periodCount++
                        act_test_iv_7.setImageResource(R.drawable.btn_pick_year_7_click)
                    }
                    if(response.body()!!.data.period.yet) {
                        periodCount++
                        act_test_iv_yet.setImageResource(R.drawable.btn_pick_year_under_0_click)
                    }
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_period_count.text = periodCount.toString()
                    var fieldCount = 0
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_1.setImageResource(R.drawable.btn_pick_company_1_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_2.setImageResource(R.drawable.btn_pick_company_2_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_3.setImageResource(R.drawable.btn_pick_company_3_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_4.setImageResource(R.drawable.btn_pick_company_4_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_5.setImageResource(R.drawable.btn_pick_company_5_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_6.setImageResource(R.drawable.btn_pick_company_6_click)
                    }
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_iv_company_7.setImageResource(R.drawable.btn_pick_company_7_click)
                    }
                    act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_field_count.text = fieldCount.toString()

                    act_test_iv_field_a.layoutParams.width = act_test_tv_field_a.layoutParams.width

                }
            }
        })
    }
}
