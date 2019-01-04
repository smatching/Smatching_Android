package appjam.sopt.a23rd.smatching

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.media.Image
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import appjam.sopt.a23rd.smatching.Data.*
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.Put.PutSmatchingEdit
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import appjam.sopt.a23rd.smatching.Fragment.CustomSecondFragment
import appjam.sopt.a23rd.smatching.Fragment.SecondCustomConditionClickFragment
import appjam.sopt.a23rd.smatching.Fragment.SmatchingCustomPopupSectorFragment
import kotlinx.android.synthetic.main.fragment_smatching_custom_popup_sector.*


class TestActivity : AppCompatActivity() {

     //region 복수선택 가능한 객관식 옵션들 정의
    val LOCATIONSNAME = arrayOf("seoul", "busan", "daegu", "incheon", "gwangju", "daejeon", "ulsan", "sejong", "gangwon", "kyunggi", "kyungnam", "kyungbuk", "jeonnam", "jeonbuk", "chungnam", "chungbuk", "jeju", "aborad")
    val AGESNAME = arrayOf("twenty_less", "twenty_forty", "forty_more")
    val PERIODSNAME = arrayOf("zero_one", "one_two", "two_three", "three_four", "four_five", "five_six", "six_seven", "seven_more", "yet")
    val CATEGORYSNAME = arrayOf("edu", "know", "place", "local", "global", "make", "gov", "loan")
    val FIELDSNAME = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v")
    val ADVANTAGESNAME = arrayOf("retry", "woman", "disabled", "social", "sole", "fourth", "univ", "togather")
    val BUSITYPESNAME = arrayOf("midsmall", "midbig", "big", "sole", "small", "tradi", "pre")
    val LOCATIONSBOOL = arrayOfNulls<Boolean>(18)
    val AGESBOOL = arrayOfNulls<Boolean>(3)
    val PERIODSBOOL = arrayOfNulls<Boolean>(9)
    val CATEGORYSBOOL = arrayOfNulls<Boolean>(8)
    val FIELDSBOOL = arrayOfNulls<Boolean>(22)
    val ADVANTAGESBOOL = arrayOfNulls<Boolean>(8)
    val BUSITYPESBOOL = arrayOfNulls<Boolean>(7)
    //endregion 복수선택 가능한 객관식 옵션들 정의
    val dataList : ArrayList<CondSummaryListData> by lazy {
        ArrayList<CondSummaryListData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var mCondIdx = 0
    var periodCount = 0
    var busiTypeCount = 0
    var advantageCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //region 객관식 옵션들 false로 초기화
        Arrays.fill(LOCATIONSBOOL, false)
        Arrays.fill(AGESBOOL, false)
        Arrays.fill(PERIODSBOOL, false)
        Arrays.fill(CATEGORYSBOOL, false)
        Arrays.fill(FIELDSBOOL, false)
        Arrays.fill(ADVANTAGESBOOL, false)
        Arrays.fill(BUSITYPESBOOL, false)
        //endregion
        val toolbar = findViewById<Toolbar>(R.id.act_test_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        act_test_tv.text = "맞춤지원"
        getUserSmatchingCondResponse()
        //region 나이 설정
        act_test_iv_age20.setOnClickListener {
            if(AGESBOOL[0]!!) {
                AGESBOOL[0] = false
                act_test_iv_age20.setImageResource(R.drawable.btn_pick_age20)
            }
            else {
                AGESBOOL[0] = true
                act_test_iv_age20.setImageResource(R.drawable.btn_pick_age_20_click_blue)
                AGESBOOL[1] = false
                act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age2039)
                AGESBOOL[2] = false
                act_test_iv_age40.setImageResource(R.drawable.btn_pick_age40_click)
            }
        }
        act_test_iv_age2039.setOnClickListener {
            if(AGESBOOL[1]!!) {
                AGESBOOL[1] = false
                act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age2039)
            }
            else {
                AGESBOOL[0] = false
                act_test_iv_age20.setImageResource(R.drawable.btn_pick_age20)
                AGESBOOL[1] = true
                act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age_2039_click_blue)
                AGESBOOL[2] = false
                act_test_iv_age40.setImageResource(R.drawable.btn_pick_age40_click)
            }
        }
        act_test_iv_age40.setOnClickListener {
            if(AGESBOOL[2]!!) {
                AGESBOOL[2] = false
                act_test_iv_age40.setImageResource(R.drawable.btn_pick_age40_click)
            }
            else {
                AGESBOOL[0] = false
                act_test_iv_age20.setImageResource(R.drawable.btn_pick_age20)
                AGESBOOL[1] = false
                act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age2039)
                AGESBOOL[2] = true
                act_test_iv_age40.setImageResource(R.drawable.btn_pick_age_40_click_blue)
            }
        }
        //endregion
        //region 업종 설정
        act_test_rl_busiType.setOnClickListener {
            act_test_rl_popup_sector.visibility = View.VISIBLE
        }
        act_test_rl_popup_sector_exit.setOnClickListener {
            act_test_rl_popup_sector.visibility = View.INVISIBLE
        }
        //endregion
        //region 설립 경과 년수 설정
        act_test_iv_01.setOnClickListener {
            if(PERIODSBOOL[0]!! && periodCount > 0) {
                PERIODSBOOL[0] = false
                periodCount--
                act_test_iv_01.setImageResource(R.drawable.btn_pick_year_01)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[0]!! && periodCount < 3) {
                PERIODSBOOL[0] = true
                periodCount++
                act_test_iv_01.setImageResource(R.drawable.btn_pick_year_01_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_12.setOnClickListener {
            if(PERIODSBOOL[1]!! && periodCount > 0) {
                PERIODSBOOL[1] = false
                periodCount--
                act_test_iv_12.setImageResource(R.drawable.btn_pick_year_12)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[1]!! && periodCount < 3) {
                PERIODSBOOL[1] = true
                periodCount++
                act_test_iv_12.setImageResource(R.drawable.btn_pick_year_12_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_23.setOnClickListener {
            if(PERIODSBOOL[2]!! && periodCount > 0) {
                PERIODSBOOL[2] = false
                periodCount--
                act_test_iv_23.setImageResource(R.drawable.btn_pick_year_23)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[2]!! && periodCount < 3) {
                PERIODSBOOL[2] = true
                periodCount++
                act_test_iv_23.setImageResource(R.drawable.btn_pick_year_23_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_34.setOnClickListener {
            if(PERIODSBOOL[3]!! && periodCount > 0) {
                PERIODSBOOL[3] = false
                periodCount--
                act_test_iv_34.setImageResource(R.drawable.btn_pick_year_34)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[3]!! && periodCount < 3) {
                PERIODSBOOL[3] = true
                periodCount++
                act_test_iv_34.setImageResource(R.drawable.btn_pick_year_34_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_45.setOnClickListener {
            if(PERIODSBOOL[4]!! && periodCount > 0) {
                PERIODSBOOL[4] = false
                periodCount--
                act_test_iv_45.setImageResource(R.drawable.btn_pick_year_45)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[4]!! && periodCount < 3) {
                PERIODSBOOL[4] = true
                periodCount++
                act_test_iv_45.setImageResource(R.drawable.btn_pick_year_45_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_56.setOnClickListener {
            if(PERIODSBOOL[5]!! && periodCount > 0) {
                PERIODSBOOL[5] = false
                periodCount--
                act_test_iv_56.setImageResource(R.drawable.btn_pick_year_56)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[5]!! && periodCount < 3) {
                PERIODSBOOL[5] = true
                periodCount++
                act_test_iv_56.setImageResource(R.drawable.btn_pick_year_56_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_67.setOnClickListener {
            if(PERIODSBOOL[6]!! && periodCount > 0) {
                PERIODSBOOL[6] = false
                periodCount--
                act_test_iv_67.setImageResource(R.drawable.btn_pick_year_67)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[6]!! && periodCount < 3) {
                PERIODSBOOL[6] = true
                periodCount++
                act_test_iv_67.setImageResource(R.drawable.btn_pick_year_67_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_7.setOnClickListener {
            if(PERIODSBOOL[7]!! && periodCount > 0) {
                PERIODSBOOL[7] = false
                periodCount--
                act_test_iv_7.setImageResource(R.drawable.btn_pick_year_7)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[7]!! && periodCount < 3) {
                PERIODSBOOL[7] = true
                periodCount++
                act_test_iv_7.setImageResource(R.drawable.btn_pick_year_7_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        act_test_iv_yet.setOnClickListener {
            if(PERIODSBOOL[8]!! && periodCount > 0) {
                PERIODSBOOL[8] = false
                periodCount--
                act_test_iv_yet.setImageResource(R.drawable.btn_pick_year_under0)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            } else if(!PERIODSBOOL[8]!! && periodCount < 3) {
                PERIODSBOOL[8] = true
                periodCount++
                act_test_iv_yet.setImageResource(R.drawable.btn_pick_year_under_0_click)
                if(periodCount > 0)
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_period_count.text = periodCount.toString()
            }
        }
        //endregion
        // region 기업형태 설정
        act_test_iv_company_1.setOnClickListener {
            if(BUSITYPESBOOL[0]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[0] = false
                busiTypeCount--
                act_test_iv_company_1.setImageResource(R.drawable.btn_pick_company_1)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[0]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[0] = true
                busiTypeCount++
                act_test_iv_company_1.setImageResource(R.drawable.btn_pick_company_1_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_2.setOnClickListener {
            if(BUSITYPESBOOL[1]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[1] = false
                busiTypeCount--
                act_test_iv_company_2.setImageResource(R.drawable.btn_pick_company_2)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[1]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[1] = true
                busiTypeCount++
                act_test_iv_company_2.setImageResource(R.drawable.btn_pick_company_2_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_3.setOnClickListener {
            if(BUSITYPESBOOL[2]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[2] = false
                busiTypeCount--
                act_test_iv_company_3.setImageResource(R.drawable.btn_pick_company_3)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[2]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[2] = true
                busiTypeCount++
                act_test_iv_company_3.setImageResource(R.drawable.btn_pick_company_3_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_4.setOnClickListener {
            if(BUSITYPESBOOL[3]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[3] = false
                busiTypeCount--
                act_test_iv_company_4.setImageResource(R.drawable.btn_pick_company_4)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[3]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[3] = true
                busiTypeCount++
                act_test_iv_company_4.setImageResource(R.drawable.btn_pick_company_4_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_5.setOnClickListener {
            if(BUSITYPESBOOL[4]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[4] = false
                busiTypeCount--
                act_test_iv_company_5.setImageResource(R.drawable.btn_pick_company_5)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[4]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[4] = true
                busiTypeCount++
                act_test_iv_company_5.setImageResource(R.drawable.btn_pick_company_5_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_6.setOnClickListener {
            if(BUSITYPESBOOL[5]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[5] = false
                busiTypeCount--
                act_test_iv_company_6.setImageResource(R.drawable.btn_pick_company_6)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[5]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[5] = true
                busiTypeCount++
                act_test_iv_company_6.setImageResource(R.drawable.btn_pick_company_6_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        act_test_iv_company_7.setOnClickListener {
            if(BUSITYPESBOOL[6]!! && busiTypeCount > 0) {
                BUSITYPESBOOL[6] = false
                busiTypeCount--
                act_test_iv_company_7.setImageResource(R.drawable.btn_pick_company_7)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            } else if(!BUSITYPESBOOL[6]!! && busiTypeCount < 7) {
                BUSITYPESBOOL[6] = true
                busiTypeCount++
                act_test_iv_company_7.setImageResource(R.drawable.btn_pick_company_7_click)
                if(busiTypeCount > 0)
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_busiType_count.text = busiTypeCount.toString()
            }
        }
        //endregion
        // region 우대사항 설정
        act_test_iv_preference_1.setOnClickListener {
            if(ADVANTAGESBOOL[0]!! && advantageCount > 0) {
                ADVANTAGESBOOL[0] = false
                advantageCount--
                act_test_iv_preference_1.setImageResource(R.drawable.btn_pick_preference_1)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[0]!! && advantageCount < 8) {
                ADVANTAGESBOOL[0] = true
                advantageCount++
                act_test_iv_preference_1.setImageResource(R.drawable.btn_pick_preference_1_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_2.setOnClickListener {
            if(ADVANTAGESBOOL[1]!! && advantageCount > 0) {
                ADVANTAGESBOOL[1] = false
                advantageCount--
                act_test_iv_preference_2.setImageResource(R.drawable.btn_pick_preference_2)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[1]!! && advantageCount < 8) {
                ADVANTAGESBOOL[1] = true
                advantageCount++
                act_test_iv_preference_2.setImageResource(R.drawable.btn_pick_preference_2_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_3.setOnClickListener {
            if(ADVANTAGESBOOL[2]!! && advantageCount > 0) {
                ADVANTAGESBOOL[2] = false
                advantageCount--
                act_test_iv_preference_3.setImageResource(R.drawable.btn_pick_preference_3)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[2]!! && advantageCount < 8) {
                ADVANTAGESBOOL[2] = true
                advantageCount++
                act_test_iv_preference_3.setImageResource(R.drawable.btn_pick_preference_3_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_4.setOnClickListener {
            if(ADVANTAGESBOOL[3]!! && advantageCount > 0) {
                ADVANTAGESBOOL[3] = false
                advantageCount--
                act_test_iv_preference_4.setImageResource(R.drawable.btn_pick_preference_4)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[3]!! && advantageCount < 8) {
                ADVANTAGESBOOL[3] = true
                advantageCount++
                act_test_iv_preference_4.setImageResource(R.drawable.btn_pick_preference_4_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_5.setOnClickListener {
            if(ADVANTAGESBOOL[4]!! && advantageCount > 0) {
                ADVANTAGESBOOL[4] = false
                advantageCount--
                act_test_iv_preference_5.setImageResource(R.drawable.btn_pick_preference_5)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[4]!! && advantageCount < 8) {
                ADVANTAGESBOOL[4] = true
                advantageCount++
                act_test_iv_preference_5.setImageResource(R.drawable.btn_pick_preference_5_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_6.setOnClickListener {
            if(ADVANTAGESBOOL[5]!! && advantageCount > 0) {
                ADVANTAGESBOOL[5] = false
                advantageCount--
                act_test_iv_preference_6.setImageResource(R.drawable.btn_pick_preference_6)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[5]!! && advantageCount < 8) {
                ADVANTAGESBOOL[5] = true
                advantageCount++
                act_test_iv_preference_6.setImageResource(R.drawable.btn_pick_preference_6_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_7.setOnClickListener {
            if(ADVANTAGESBOOL[6]!! && advantageCount > 0) {
                ADVANTAGESBOOL[6] = false
                advantageCount--
                act_test_iv_preference_7.setImageResource(R.drawable.btn_pick_preference_7)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[6]!! && advantageCount < 8) {
                ADVANTAGESBOOL[6] = true
                advantageCount++
                act_test_iv_preference_7.setImageResource(R.drawable.btn_pick_preference_7_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        act_test_iv_preference_8.setOnClickListener {
            if(ADVANTAGESBOOL[7]!! && advantageCount > 0) {
                ADVANTAGESBOOL[7] = false
                advantageCount--
                act_test_iv_preference_8.setImageResource(R.drawable.btn_pick_preference_8)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            } else if(!ADVANTAGESBOOL[7]!! && advantageCount < 8) {
                ADVANTAGESBOOL[7] = true
                advantageCount++
                act_test_iv_preference_8.setImageResource(R.drawable.btn_pick_preference_8_click)
                if(advantageCount > 0)
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_advantage_count.text = advantageCount.toString()
            }
        }
        //endregion
        act_test_rl.setOnClickListener {
            putUserSmatchingResponse(mCondIdx)
        }
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
                    mCondIdx = response.body()!!.data.condSummaryList.get(1).condIdx
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
                    //region location
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
                    if(locationCount > 0)
                        fragment_smatching_custom_location.setTextColor(resources.getColor(R.color.colorBlue))
                    fragment_smatching_custom_location.text = locationCount.toString()
                    //endregion
                    //region age
                    if(response.body()!!.data.age.twenty_less) {
                        act_test_iv_age20.setImageResource(R.drawable.btn_pick_age_20_click_blue)
                        AGESBOOL[0] = true
                    }
                    if(response.body()!!.data.age.twenty_forty) {
                        act_test_iv_age2039.setImageResource(R.drawable.btn_pick_age_2039_click_blue)
                        AGESBOOL[1] = true
                    }
                    if(response.body()!!.data.age.forty_more) {
                        act_test_iv_age40.setImageResource(R.drawable.btn_pick_age_40_click_blue)
                        AGESBOOL[2] = true
                    }
                    //endregion
                    //region period
                    if(response.body()!!.data.period.zero_one) {
                        periodCount++
                        PERIODSBOOL[0] = true
                        act_test_iv_01.setImageResource(R.drawable.btn_pick_year_01_click)
                    }
                    if(response.body()!!.data.period.one_two) {
                        periodCount++
                        PERIODSBOOL[1] = true
                        act_test_iv_12.setImageResource(R.drawable.btn_pick_year_12_click)
                    }
                    if(response.body()!!.data.period.two_three) {
                        periodCount++
                        PERIODSBOOL[2] = true
                        act_test_iv_23.setImageResource(R.drawable.btn_pick_year_23_click)
                    }
                    if(response.body()!!.data.period.three_four) {
                        periodCount++
                        PERIODSBOOL[3] = true
                        act_test_iv_34.setImageResource(R.drawable.btn_pick_year_34_click)
                    }
                    if(response.body()!!.data.period.four_five) {
                        periodCount++
                        PERIODSBOOL[4] = true
                        act_test_iv_45.setImageResource(R.drawable.btn_pick_year_45_click)
                    }
                    if(response.body()!!.data.period.five_six) {
                        periodCount++
                        PERIODSBOOL[5] = true
                        act_test_iv_56.setImageResource(R.drawable.btn_pick_year_56_click)
                    }
                    if(response.body()!!.data.period.six_seven) {
                        periodCount++
                        PERIODSBOOL[6] = true
                        act_test_iv_67.setImageResource(R.drawable.btn_pick_year_67_click)
                    }
                    if(response.body()!!.data.period.seven_more) {
                        periodCount++
                        PERIODSBOOL[7] = true
                        act_test_iv_7.setImageResource(R.drawable.btn_pick_year_7_click)
                    }
                    if(response.body()!!.data.period.yet) {
                        periodCount++
                        PERIODSBOOL[8] = true
                        act_test_iv_yet.setImageResource(R.drawable.btn_pick_year_under_0_click)
                    }
                    if(periodCount > 0)
                        act_test_tv_period_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_period_count.text = periodCount.toString()
                    //endregion
                    //region busiType
                    if(response.body()!!.data.busiType.midsmall) {
                        busiTypeCount++
                        BUSITYPESBOOL[0] = true
                        act_test_iv_company_1.setImageResource(R.drawable.btn_pick_company_1_click)
                    }
                    if(response.body()!!.data.busiType.midbig) {
                        BUSITYPESBOOL[1] = true
                        busiTypeCount++
                        act_test_iv_company_2.setImageResource(R.drawable.btn_pick_company_2_click)
                    }
                    if(response.body()!!.data.busiType.big) {
                        BUSITYPESBOOL[2] = true
                        busiTypeCount++
                        act_test_iv_company_3.setImageResource(R.drawable.btn_pick_company_3_click)
                    }
                    if(response.body()!!.data.busiType.sole) {
                        BUSITYPESBOOL[3] = true
                        busiTypeCount++
                        act_test_iv_company_4.setImageResource(R.drawable.btn_pick_company_4_click)
                    }
                    if(response.body()!!.data.busiType.small) {
                        BUSITYPESBOOL[4] = true
                        busiTypeCount++
                        act_test_iv_company_5.setImageResource(R.drawable.btn_pick_company_5_click)
                    }
                    if(response.body()!!.data.busiType.tradi) {
                        BUSITYPESBOOL[5] = true
                        busiTypeCount++
                        act_test_iv_company_6.setImageResource(R.drawable.btn_pick_company_6_click)
                    }
                    if(response.body()!!.data.busiType.pre) {
                        BUSITYPESBOOL[6] = true
                        busiTypeCount++
                        act_test_iv_company_7.setImageResource(R.drawable.btn_pick_company_7_click)
                    }
                    if(busiTypeCount > 0)
                        act_test_tv_busiType_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_busiType_count.text = busiTypeCount.toString()
                    //endregion
                    //region field
                    var fieldCount = 0
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        act_test_ll_field_a.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.b) {
                        fieldCount++
                        act_test_ll_field_b.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.c) {
                        fieldCount++
                        act_test_ll_field_c.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.d) {
                        fieldCount++
                        act_test_ll_field_d.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.e) {
                        fieldCount++
                        act_test_ll_field_e.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.f) {
                        fieldCount++
                        act_test_ll_field_f.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.g) {
                        fieldCount++
                        act_test_ll_field_g.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.h) {
                        fieldCount++
                        act_test_ll_field_h.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.i) {
                        fieldCount++
                        act_test_ll_field_i.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.j) {
                        fieldCount++
                        act_test_ll_field_j.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.k) {
                        fieldCount++
                        act_test_ll_field_k.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.l) {
                        fieldCount++
                        act_test_ll_field_l.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.m) {
                        fieldCount++
                        act_test_ll_field_m.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.n) {
                        fieldCount++
                        act_test_ll_field_n.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.o) {
                        fieldCount++
                        act_test_ll_field_o.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.p) {
                        fieldCount++
                        act_test_ll_field_p.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.q) {
                        fieldCount++
                        act_test_ll_field_q.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.r) {
                        fieldCount++
                        act_test_ll_field_r.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.s) {
                        fieldCount++
                        act_test_ll_field_s.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.t) {
                        fieldCount++
                        act_test_ll_field_t.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.u) {
                        fieldCount++
                        act_test_ll_field_u.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.v) {
                        fieldCount++
                        act_test_ll_field_v.visibility = View.VISIBLE
                    }
                    if(fieldCount > 0)
                        act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_field_count.text = fieldCount.toString()
                    //endregion
                    //region excCategory
                    var excCategoryCount = 0
                    if(response.body()!!.data.excCategory.edu) {
                        excCategoryCount++
                        act_test_ll_excCategory_edu.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.know) {
                        excCategoryCount++
                        act_test_ll_excCategory_know.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.place) {
                        excCategoryCount++
                        act_test_ll_excCategory_place.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.local) {
                        excCategoryCount++
                        act_test_ll_excCategory_local.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.global) {
                        excCategoryCount++
                        act_test_ll_excCategory_global.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.make) {
                        excCategoryCount++
                        act_test_ll_excCategory_make.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.gov) {
                        excCategoryCount++
                        act_test_ll_excCategory_gov.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.loan) {
                        excCategoryCount++
                        act_test_ll_excCategory_loan.visibility = View.VISIBLE
                    }
                    if(excCategoryCount > 0)
                        act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_excCategory_count.text = excCategoryCount.toString()
                    //endregion
                    //region advantage
                    if(response.body()!!.data.advantage.retry) {
                        advantageCount++
                        ADVANTAGESBOOL[0] = true
                        act_test_iv_preference_1.setImageResource(R.drawable.btn_pick_preference_1_click)
                    }
                    if(response.body()!!.data.advantage.woman) {
                        advantageCount++
                        ADVANTAGESBOOL[1] = true
                        act_test_iv_preference_2.setImageResource(R.drawable.btn_pick_preference_2_click)
                    }
                    if(response.body()!!.data.advantage.disabled) {
                        advantageCount++
                        ADVANTAGESBOOL[2] = true
                        act_test_iv_preference_3.setImageResource(R.drawable.btn_pick_preference_3_click)
                    }
                    if(response.body()!!.data.advantage.social) {
                        advantageCount++
                        ADVANTAGESBOOL[3] = true
                        act_test_iv_preference_4.setImageResource(R.drawable.btn_pick_preference_4_click)
                    }
                    if(response.body()!!.data.advantage.sole) {
                        advantageCount++
                        ADVANTAGESBOOL[4] = true
                        act_test_iv_preference_5.setImageResource(R.drawable.btn_pick_preference_5_click)
                    }
                    if(response.body()!!.data.advantage.fourth) {
                        advantageCount++
                        ADVANTAGESBOOL[5] = true
                        act_test_iv_preference_6.setImageResource(R.drawable.btn_pick_preference_6_click)
                    }
                    if(response.body()!!.data.advantage.univ) {
                        advantageCount++
                        ADVANTAGESBOOL[6] = true
                        act_test_iv_preference_7.setImageResource(R.drawable.btn_pick_preference_7_click)
                    }
                    if(response.body()!!.data.advantage.togather) {
                        advantageCount++
                        ADVANTAGESBOOL[7] = true
                        act_test_iv_preference_8.setImageResource(R.drawable.btn_pick_preference_8_click)
                    }
                    if(advantageCount > 0)
                        act_test_tv_advantage_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_advantage_count.text = advantageCount.toString()
                    //endregion
                }
            }
        })
    }
    private fun putUserSmatchingResponse(condIdx: Int) {
        var jsonObject = JsonObject() // 요청바디 전체 객체

        jsonObject.addProperty("condName", act_test_et_title.text.toString())

        var ageJson = JsonObject() // age 내부 객체
        for(a in 0..2)
            ageJson.addProperty(AGESNAME[a], AGESBOOL[a])
        jsonObject.add("age", ageJson)

        var locationJson = JsonObject() // location 내부 객체
        for(a in 0..17)
            locationJson.addProperty(LOCATIONSNAME[a], LOCATIONSBOOL[a])
        jsonObject.add("location", locationJson)

        var periodJson = JsonObject() // period 내부 객체
        for(a in 0..8)
            periodJson.addProperty(PERIODSNAME[a], PERIODSBOOL[a])
        jsonObject.add("period", periodJson)

        var fieldJson = JsonObject() // field 내부 객체
        fieldJson.addProperty("a", true)
        fieldJson.addProperty("b", true)
        fieldJson.addProperty("c", true)
        fieldJson.addProperty("d", true)
        fieldJson.addProperty("e", true)
        fieldJson.addProperty("f", true)
        fieldJson.addProperty("g", true)
        fieldJson.addProperty("h", true)
        fieldJson.addProperty("i", true)
        fieldJson.addProperty("j", true)
        fieldJson.addProperty("k", true)
        fieldJson.addProperty("l", true)
        fieldJson.addProperty("m", true)
        fieldJson.addProperty("n", true)
        fieldJson.addProperty("o", true)
        fieldJson.addProperty("p", true)
        fieldJson.addProperty("q", true)
        fieldJson.addProperty("r", true)
        fieldJson.addProperty("s", true)
        fieldJson.addProperty("t", true)
        fieldJson.addProperty("u", true)
        fieldJson.addProperty("v", true)
        jsonObject.add("field", fieldJson)




        var advantageJson = JsonObject() // advantage 내부 객체
        for(a in 0..7)
            advantageJson.addProperty(ADVANTAGESNAME[a], ADVANTAGESBOOL[a])
        jsonObject.add("advantage", advantageJson)



        var busiTypeJson = JsonObject() // busiType 내부 객체
        for(a in 0..6)
            busiTypeJson.addProperty(BUSITYPESNAME[a], BUSITYPESBOOL[a])
        jsonObject.add("busiType", busiTypeJson)


        var excCategoryJson = JsonObject() // excCategory 내부 객체
        excCategoryJson.addProperty("loan", false)
        excCategoryJson.addProperty("edu", false)
        excCategoryJson.addProperty("know", false)
        excCategoryJson.addProperty("global", false)
        excCategoryJson.addProperty("place", false)
        excCategoryJson.addProperty("make", false)
        excCategoryJson.addProperty("local", false)
        excCategoryJson.addProperty("gov", false)
        jsonObject.add("excCategory", excCategoryJson)


        //통신 시작
        val postSignUpResponse: Call<PutSmatchingEdit> =
                networkService.putSmatchingCondsChangeResponse("application/json", SharedPreferenceController.getAuthorization(this), condIdx, jsonObject)

        postSignUpResponse.enqueue(object : Callback<PutSmatchingEdit> {
            override fun onFailure(call: Call<PutSmatchingEdit>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<PutSmatchingEdit>, response: Response<PutSmatchingEdit>) {
                if (response.isSuccessful) {

                    val refresh = Intent(this@TestActivity, MainActivity::class.java )
                    refresh.putExtra("view", 1)
                    refresh.putExtra("page", 1)
                    startActivity(refresh)//Start the same Activity
                    finish() //finish Activity.
                }
            }
        })
    }

}