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
import android.view.MotionEvent
import appjam.sopt.a23rd.smatching.Put.PutSmatchingCount


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
    val TEMPCATEGORYSBOOL = arrayOfNulls<Boolean>(8)
    val FIELDSBOOL = arrayOfNulls<Boolean>(22)
    var TEMPFIELDSBOOL = arrayOfNulls<Boolean>(22)
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
    var fieldCount = 0
    var tempFieldCount = 0
    var categoryCount = 0
    var tempCategoryCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //region 객관식 옵션들 false로 초기화
        Arrays.fill(LOCATIONSBOOL, true)
        Arrays.fill(AGESBOOL, false)
        Arrays.fill(PERIODSBOOL, false)
        Arrays.fill(CATEGORYSBOOL, false)
        Arrays.fill(FIELDSBOOL, false)
        Arrays.fill(ADVANTAGESBOOL, false)
        Arrays.fill(BUSITYPESBOOL, false)
        Arrays.fill(TEMPFIELDSBOOL, false)
        Arrays.fill(TEMPCATEGORYSBOOL, false)
        //endregion
        val toolbar = findViewById<Toolbar>(R.id.act_test_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        act_test_tv.text = "맞춤지원"
        getUserSmatchingCondResponse()
        for(a in 0..21)
            TEMPFIELDSBOOL[a] = FIELDSBOOL[a]
        for(a in 0..7)
            TEMPCATEGORYSBOOL[a] = CATEGORYSBOOL[a]
        act_test_rl_popup_needless.setOnTouchListener(View.OnTouchListener { v, event -> true })
        act_test_rl_popup_sector.setOnTouchListener(View.OnTouchListener { v, event -> true })
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
        //region 팝업부분
        act_test_rl_busiType.setOnClickListener {
            tempFieldCount = fieldCount
            for(a in 0..21)
                TEMPFIELDSBOOL[a] = FIELDSBOOL[a]
            if (fieldCount > 0)
                act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_tv_field_count.text = fieldCount.toString()
            act_test_tv_fieldCount_count.text = fieldCount.toString()
            act_test_rl_popup_sector.visibility = View.VISIBLE
        }
        act_test_rl_popup_sector_exit.setOnClickListener {
            fieldCount = tempFieldCount
            for(a in 0..21)
                FIELDSBOOL[a] = TEMPFIELDSBOOL[a]
            //region 노가다로 값넣기1 -> 나중에 수정하자...
            if(FIELDSBOOL[0]!!)
                act_test_iv_check_sector_A.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_A.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[1]!!)
                act_test_iv_check_sector_B.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_B.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[2]!!)
                act_test_iv_check_sector_C.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_C.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[3]!!)
                act_test_iv_check_sector_D.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_D.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[4]!!)
                act_test_iv_check_sector_E.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_E.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[5]!!)
                act_test_iv_check_sector_F.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_F.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[6]!!)
                act_test_iv_check_sector_G.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_G.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[7]!!)
                act_test_iv_check_sector_H.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_H.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[8]!!)
                act_test_iv_check_sector_I.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_I.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[9]!!)
                act_test_iv_check_sector_J.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_J.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[10]!!)
                act_test_iv_check_sector_K.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_K.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[11]!!)
                act_test_iv_check_sector_L.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_L.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[12]!!)
                act_test_iv_check_sector_M.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_M.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[13]!!)
                act_test_iv_check_sector_N.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_N.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[14]!!)
                act_test_iv_check_sector_O.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_O.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[15]!!)
                act_test_iv_check_sector_P.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_P.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[16]!!)
                act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[17]!!)
                act_test_iv_check_sector_R.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_R.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[18]!!)
                act_test_iv_check_sector_S.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_S.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[19]!!)
                act_test_iv_check_sector_T.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_T.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[20]!!)
                act_test_iv_check_sector_U.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_U.setImageResource(R.drawable.icn_emptybox)
            if(FIELDSBOOL[21]!!)
                act_test_iv_check_sector_V.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_iv_check_sector_V.setImageResource(R.drawable.icn_emptybox)
            //endregion
            if (fieldCount > 0)
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_tv_field_count.text = fieldCount.toString()
            act_test_tv_fieldCount_count.text = fieldCount.toString()
            act_test_rl_popup_sector.visibility = View.INVISIBLE
        }
        act_test_rl_popup_sector_ok.setOnClickListener {
            tempFieldCount = fieldCount
            for(a in 0..21)
                TEMPFIELDSBOOL[a] = FIELDSBOOL[a]

            //region 노가다로 값넣기2 -> 나중에 수정하자...
            if(FIELDSBOOL[0]!!)
                act_test_ll_field_a.visibility = View.VISIBLE
            else
                act_test_ll_field_a.visibility = View.GONE
            if(FIELDSBOOL[1]!!)
                act_test_ll_field_b.visibility = View.VISIBLE
            else
                act_test_ll_field_b.visibility = View.GONE
            if(FIELDSBOOL[2]!!)
                act_test_ll_field_c.visibility = View.VISIBLE
            else
                act_test_ll_field_c.visibility = View.GONE
            if(FIELDSBOOL[3]!!)
                act_test_ll_field_d.visibility = View.VISIBLE
            else
                act_test_ll_field_d.visibility = View.GONE
            if(FIELDSBOOL[4]!!)
                act_test_ll_field_e.visibility = View.VISIBLE
            else
                act_test_ll_field_e.visibility = View.GONE
            if(FIELDSBOOL[5]!!)
                act_test_ll_field_f.visibility = View.VISIBLE
            else
                act_test_ll_field_f.visibility = View.GONE
            if(FIELDSBOOL[6]!!)
                act_test_ll_field_g.visibility = View.VISIBLE
            else
                act_test_ll_field_g.visibility = View.GONE
            if(FIELDSBOOL[7]!!)
                act_test_ll_field_h.visibility = View.VISIBLE
            else
                act_test_ll_field_h.visibility = View.GONE
            if(FIELDSBOOL[8]!!)
                act_test_ll_field_i.visibility = View.VISIBLE
            else
                act_test_ll_field_i.visibility = View.GONE
            if(FIELDSBOOL[9]!!)
                act_test_ll_field_j.visibility = View.VISIBLE
            else
                act_test_ll_field_j.visibility = View.GONE
            if(FIELDSBOOL[10]!!)
                act_test_ll_field_k.visibility = View.VISIBLE
            else
                act_test_ll_field_k.visibility = View.GONE
            if(FIELDSBOOL[11]!!)
                act_test_ll_field_l.visibility = View.VISIBLE
            else
                act_test_ll_field_l.visibility = View.GONE
            if(FIELDSBOOL[12]!!)
                act_test_ll_field_m.visibility = View.VISIBLE
            else
                act_test_ll_field_m.visibility = View.GONE
            if(FIELDSBOOL[13]!!)
                act_test_ll_field_n.visibility = View.VISIBLE
            else
                act_test_ll_field_n.visibility = View.GONE
            if(FIELDSBOOL[14]!!)
                act_test_ll_field_o.visibility = View.VISIBLE
            else
                act_test_ll_field_o.visibility = View.GONE
            if(FIELDSBOOL[15]!!)
                act_test_ll_field_p.visibility = View.VISIBLE
            else
                act_test_ll_field_p.visibility = View.GONE
            if(FIELDSBOOL[16]!!)
                act_test_ll_field_q.visibility = View.VISIBLE
            else
                act_test_ll_field_q.visibility = View.GONE
            if(FIELDSBOOL[17]!!)
                act_test_ll_field_r.visibility = View.VISIBLE
            else
                act_test_ll_field_r.visibility = View.GONE
            if(FIELDSBOOL[18]!!)
                act_test_ll_field_s.visibility = View.VISIBLE
            else
                act_test_ll_field_s.visibility = View.GONE
            if(FIELDSBOOL[19]!!)
                act_test_ll_field_t.visibility = View.VISIBLE
            else
                act_test_ll_field_t.visibility = View.GONE
            if(FIELDSBOOL[20]!!)
                act_test_ll_field_u.visibility = View.VISIBLE
            else
                act_test_ll_field_u.visibility = View.GONE
            if(FIELDSBOOL[21]!!)
                act_test_ll_field_v.visibility = View.VISIBLE
            else
                act_test_ll_field_v.visibility = View.GONE
            //endregion
            if (fieldCount > 0)
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_tv_field_count.text = fieldCount.toString()
            act_test_tv_fieldCount_count.text = fieldCount.toString()
            act_test_rl_popup_sector.visibility = View.INVISIBLE
        }
        act_test_popup_sector_ll_A.setOnClickListener {
            if (FIELDSBOOL[0]!! && fieldCount > 0) {
                FIELDSBOOL[0] = false
                fieldCount--
                act_test_iv_check_sector_A.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[0]!! && fieldCount < 6) {
                FIELDSBOOL[0] = true
                fieldCount++
                act_test_iv_check_sector_A.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_B.setOnClickListener {
            if (FIELDSBOOL[1]!! && fieldCount > 0) {
                FIELDSBOOL[1] = false
                fieldCount--
                act_test_iv_check_sector_B.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[1]!! && fieldCount < 6) {
                FIELDSBOOL[1] = true
                fieldCount++
                act_test_iv_check_sector_B.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_C.setOnClickListener {
            if (FIELDSBOOL[2]!! && fieldCount > 0) {
                FIELDSBOOL[2] = false
                fieldCount--
                act_test_iv_check_sector_C.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[2]!! && fieldCount < 6) {
                FIELDSBOOL[2] = true
                fieldCount++
                act_test_iv_check_sector_C.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_D.setOnClickListener {
            if (FIELDSBOOL[3]!! && fieldCount > 0) {
                FIELDSBOOL[3] = false
                fieldCount--
                act_test_iv_check_sector_D.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[3]!! && fieldCount < 6) {
                FIELDSBOOL[3] = true
                fieldCount++
                act_test_iv_check_sector_D.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_E.setOnClickListener {
            if (FIELDSBOOL[4]!! && fieldCount > 0) {
                FIELDSBOOL[4] = false
                fieldCount--
                act_test_iv_check_sector_E.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[4]!! && fieldCount < 6) {
                FIELDSBOOL[4] = true
                fieldCount++
                act_test_iv_check_sector_E.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_F.setOnClickListener {
            if (FIELDSBOOL[5]!! && fieldCount > 0) {
                FIELDSBOOL[5] = false
                fieldCount--
                act_test_iv_check_sector_F.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[5]!! && fieldCount < 6) {
                FIELDSBOOL[5] = true
                fieldCount++
                act_test_iv_check_sector_F.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_G.setOnClickListener {
            if (FIELDSBOOL[6]!! && fieldCount > 0) {
                FIELDSBOOL[6] = false
                fieldCount--
                act_test_iv_check_sector_G.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[6]!! && fieldCount < 6) {
                FIELDSBOOL[6] = true
                fieldCount++
                act_test_iv_check_sector_G.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_H.setOnClickListener {
            if (FIELDSBOOL[7]!! && fieldCount > 0) {
                FIELDSBOOL[7] = false
                fieldCount--
                act_test_iv_check_sector_H.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[7]!! && fieldCount < 6) {
                FIELDSBOOL[7] = true
                fieldCount++
                act_test_iv_check_sector_H.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_I.setOnClickListener {
            if (FIELDSBOOL[8]!! && fieldCount > 0) {
                FIELDSBOOL[8] = false
                fieldCount--
                act_test_iv_check_sector_I.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[8]!! && fieldCount < 6) {
                FIELDSBOOL[8] = true
                fieldCount++
                act_test_iv_check_sector_I.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_J.setOnClickListener {
            if (FIELDSBOOL[9]!! && fieldCount > 0) {
                FIELDSBOOL[9] = false
                fieldCount--
                act_test_iv_check_sector_J.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[9]!! && fieldCount < 6) {
                FIELDSBOOL[9] = true
                fieldCount++
                act_test_iv_check_sector_J.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_K.setOnClickListener {
            if (FIELDSBOOL[10]!! && fieldCount > 0) {
                FIELDSBOOL[10] = false
                fieldCount--
                act_test_iv_check_sector_K.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[10]!! && fieldCount < 6) {
                FIELDSBOOL[10] = true
                fieldCount++
                act_test_iv_check_sector_K.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_L.setOnClickListener {
            if (FIELDSBOOL[11]!! && fieldCount > 0) {
                FIELDSBOOL[11] = false
                fieldCount--
                act_test_iv_check_sector_L.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[11]!! && fieldCount < 6) {
                FIELDSBOOL[11] = true
                fieldCount++
                act_test_iv_check_sector_L.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_M.setOnClickListener {
            if (FIELDSBOOL[12]!! && fieldCount > 0) {
                FIELDSBOOL[12] = false
                fieldCount--
                act_test_iv_check_sector_M.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[12]!! && fieldCount < 6) {
                FIELDSBOOL[12] = true
                fieldCount++
                act_test_iv_check_sector_M.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_N.setOnClickListener {
            if (FIELDSBOOL[13]!! && fieldCount > 0) {
                FIELDSBOOL[13] = false
                fieldCount--
                act_test_iv_check_sector_N.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[13]!! && fieldCount < 6) {
                FIELDSBOOL[13] = true
                fieldCount++
                act_test_iv_check_sector_N.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_O.setOnClickListener {
            if (FIELDSBOOL[14]!! && fieldCount > 0) {
                FIELDSBOOL[14] = false
                fieldCount--
                act_test_iv_check_sector_O.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[14]!! && fieldCount < 6) {
                FIELDSBOOL[14] = true
                fieldCount++
                act_test_iv_check_sector_O.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_P.setOnClickListener {
            if (FIELDSBOOL[15]!! && fieldCount > 0) {
                FIELDSBOOL[15] = false
                fieldCount--
                act_test_iv_check_sector_P.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[15]!! && fieldCount < 6) {
                FIELDSBOOL[15] = true
                fieldCount++
                act_test_iv_check_sector_P.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_Q.setOnClickListener {
            if (FIELDSBOOL[16]!! && fieldCount > 0) {
                FIELDSBOOL[16] = false
                fieldCount--
                act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[16]!! && fieldCount < 6) {
                FIELDSBOOL[16] = true
                fieldCount++
                act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_R.setOnClickListener {
            if (FIELDSBOOL[17]!! && fieldCount > 0) {
                FIELDSBOOL[17] = false
                fieldCount--
                act_test_iv_check_sector_R.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[17]!! && fieldCount < 6) {
                FIELDSBOOL[17] = true
                fieldCount++
                act_test_iv_check_sector_R.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_S.setOnClickListener {
            if (FIELDSBOOL[18]!! && fieldCount > 0) {
                FIELDSBOOL[18] = false
                fieldCount--
                act_test_iv_check_sector_S.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[18]!! && fieldCount < 6) {
                FIELDSBOOL[18] = true
                fieldCount++
                act_test_iv_check_sector_S.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_T.setOnClickListener {
            if (FIELDSBOOL[19]!! && fieldCount > 0) {
                FIELDSBOOL[19] = false
                fieldCount--
                act_test_iv_check_sector_T.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[19]!! && fieldCount < 6) {
                FIELDSBOOL[19] = true
                fieldCount++
                act_test_iv_check_sector_T.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_U.setOnClickListener {
            if (FIELDSBOOL[20]!! && fieldCount > 0) {
                FIELDSBOOL[20] = false
                fieldCount--
                act_test_iv_check_sector_U.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[20]!! && fieldCount < 6) {
                FIELDSBOOL[20] = true
                fieldCount++
                act_test_iv_check_sector_U.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        act_test_popup_sector_ll_V.setOnClickListener {
            if (FIELDSBOOL[21]!! && fieldCount > 0) {
                FIELDSBOOL[21] = false
                fieldCount--
                act_test_iv_check_sector_V.setImageResource(R.drawable.icn_emptybox)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            } else if (!FIELDSBOOL[21]!! && fieldCount < 6) {
                FIELDSBOOL[21] = true
                fieldCount++
                act_test_iv_check_sector_V.setImageResource(R.drawable.icn_checkbox_white)
                if (fieldCount > 0)
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_tv_fieldCount_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_tv_fieldCount_count.text = fieldCount.toString()
            }
        }
        //endregion
        //region 뷰 부분
        act_test_ll_field_a.setOnClickListener {
            act_test_ll_field_a.visibility = View.GONE
            FIELDSBOOL[0] = false
            fieldCount--
            act_test_iv_check_sector_A.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_b.setOnClickListener {
            act_test_ll_field_b.visibility = View.GONE
            FIELDSBOOL[1] = false
            fieldCount--
            act_test_iv_check_sector_B.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_c.setOnClickListener {
            act_test_ll_field_c.visibility = View.GONE
            FIELDSBOOL[2] = false
            fieldCount--
            act_test_iv_check_sector_C.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_d.setOnClickListener {
            act_test_ll_field_d.visibility = View.GONE
            FIELDSBOOL[3] = false
            fieldCount--
            act_test_iv_check_sector_D.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_e.setOnClickListener {
            act_test_ll_field_e.visibility = View.GONE
            FIELDSBOOL[4] = false
            fieldCount--
            act_test_iv_check_sector_E.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_f.setOnClickListener {
            act_test_ll_field_f.visibility = View.GONE
            FIELDSBOOL[5] = false
            fieldCount--
            act_test_iv_check_sector_F.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_g.setOnClickListener {
            act_test_ll_field_g.visibility = View.GONE
            FIELDSBOOL[6] = false
            fieldCount--
            act_test_iv_check_sector_G.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_h.setOnClickListener {
            act_test_ll_field_h.visibility = View.GONE
            FIELDSBOOL[7] = false
            fieldCount--
            act_test_iv_check_sector_H.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_i.setOnClickListener {
            act_test_ll_field_i.visibility = View.GONE
            FIELDSBOOL[8] = false
            fieldCount--
            act_test_iv_check_sector_I.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_j.setOnClickListener {
            act_test_ll_field_j.visibility = View.GONE
            FIELDSBOOL[9] = false
            fieldCount--
            act_test_iv_check_sector_J.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_k.setOnClickListener {
            act_test_ll_field_k.visibility = View.GONE
            FIELDSBOOL[10] = false
            fieldCount--
            act_test_iv_check_sector_K.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_l.setOnClickListener {
            act_test_ll_field_l.visibility = View.GONE
            FIELDSBOOL[11] = false
            fieldCount--
            act_test_iv_check_sector_L.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_m.setOnClickListener {
            act_test_ll_field_m.visibility = View.GONE
            FIELDSBOOL[12] = false
            fieldCount--
            act_test_iv_check_sector_M.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_n.setOnClickListener {
            act_test_ll_field_n.visibility = View.GONE
            FIELDSBOOL[13] = false
            fieldCount--
            act_test_iv_check_sector_N.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_o.setOnClickListener {
            act_test_ll_field_o.visibility = View.GONE
            FIELDSBOOL[14] = false
            fieldCount--
            act_test_iv_check_sector_O.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_p.setOnClickListener {
            act_test_ll_field_p.visibility = View.GONE
            FIELDSBOOL[15] = false
            fieldCount--
            act_test_iv_check_sector_P.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_q.setOnClickListener {
            act_test_ll_field_q.visibility = View.GONE
            FIELDSBOOL[16] = false
            fieldCount--
            act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_r.setOnClickListener {
            act_test_ll_field_r.visibility = View.GONE
            FIELDSBOOL[17] = false
            fieldCount--
            act_test_iv_check_sector_R.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_s.setOnClickListener {
            act_test_ll_field_s.visibility = View.GONE
            FIELDSBOOL[18] = false
            fieldCount--
            act_test_iv_check_sector_S.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_t.setOnClickListener {
            act_test_ll_field_t.visibility = View.GONE
            FIELDSBOOL[19] = false
            fieldCount--
            act_test_iv_check_sector_T.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_u.setOnClickListener {
            act_test_ll_field_u.visibility = View.GONE
            FIELDSBOOL[20] = false
            fieldCount--
            act_test_iv_check_sector_U.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        act_test_ll_field_v.setOnClickListener {
            act_test_ll_field_v.visibility = View.GONE
            FIELDSBOOL[21] = false
            fieldCount--
            act_test_iv_check_sector_V.setImageResource(R.drawable.icn_emptybox)
            if (fieldCount > 0) {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_field_count.text = fieldCount.toString()
        }
        //endregion
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
        //region 필요 없는 지원사업분야
        //region 팝업 부분
        act_test_rl_needless.setOnClickListener {
            tempCategoryCount = categoryCount
            for(a in 0..7)
                TEMPCATEGORYSBOOL[a] = CATEGORYSBOOL[a]
            if (categoryCount > 0)
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_popup_needless_tv_count.text = categoryCount.toString()
            act_test_tv_excCategory_count.text = categoryCount.toString()
            act_test_rl_popup_needless.visibility = View.VISIBLE
        }
        act_test_rl_popup_needless_exit.setOnClickListener {
            categoryCount = tempCategoryCount
            for(a in 0..7)
                CATEGORYSBOOL[a] = TEMPCATEGORYSBOOL[a]
            //region 노가다로 값넣기1 -> 나중에 수정하자...
            if(CATEGORYSBOOL[0]!!)
                act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[1]!!)
                act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[2]!!)
                act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[3]!!)
                act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[4]!!)
                act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[5]!!)
                act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[6]!!)
                act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_emptybox)
            if(CATEGORYSBOOL[7]!!)
                act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_checkbox_white)
            else
                act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_emptybox)
            //endregion
            if (categoryCount > 0)
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_popup_needless_tv_count.text = categoryCount.toString()
            act_test_tv_excCategory_count.text = categoryCount.toString()
            act_test_rl_popup_needless.visibility = View.INVISIBLE
        }
        act_test_rl_popup_needless_ok.setOnClickListener {
            tempCategoryCount = categoryCount
            for(a in 0..7)
                TEMPCATEGORYSBOOL[a] = CATEGORYSBOOL[a]
            //region 노가다로 값넣기2 -> 나중에 수정하자...
            if(CATEGORYSBOOL[0]!!)
                act_test_ll_excCategory_edu.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_edu.visibility = View.GONE
            if(CATEGORYSBOOL[1]!!)
                act_test_ll_excCategory_know.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_know.visibility = View.GONE
            if(CATEGORYSBOOL[2]!!)
                act_test_ll_excCategory_place.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_place.visibility = View.GONE
            if(CATEGORYSBOOL[3]!!)
                act_test_ll_excCategory_local.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_local.visibility = View.GONE
            if(CATEGORYSBOOL[4]!!)
                act_test_ll_excCategory_global.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_global.visibility = View.GONE
            if(CATEGORYSBOOL[5]!!)
                act_test_ll_excCategory_make.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_make.visibility = View.GONE
            if(CATEGORYSBOOL[6]!!)
                act_test_ll_excCategory_gov.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_gov.visibility = View.GONE
            if(CATEGORYSBOOL[7]!!)
                act_test_ll_excCategory_loan.visibility = View.VISIBLE
            else
                act_test_ll_excCategory_loan.visibility = View.GONE
            //endregion
            if (categoryCount > 0)
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            else
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            act_test_popup_needless_tv_count.text = categoryCount.toString()
            act_test_tv_excCategory_count.text = categoryCount.toString()
            act_test_rl_popup_needless.visibility = View.INVISIBLE
        }
        act_test_ll_popup_needless_A.setOnClickListener {
            if (CATEGORYSBOOL[0]!! && categoryCount > 0) {
                CATEGORYSBOOL[0] = false
                categoryCount--
                act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[0]!! && categoryCount < 5) {
                CATEGORYSBOOL[0] = true
                categoryCount++
                act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_B.setOnClickListener {
            if (CATEGORYSBOOL[1]!! && categoryCount > 0) {
                CATEGORYSBOOL[1] = false
                categoryCount--
                act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[1]!! && categoryCount < 5) {
                CATEGORYSBOOL[1] = true
                categoryCount++
                act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_C.setOnClickListener {
            if (CATEGORYSBOOL[2]!! && categoryCount > 0) {
                CATEGORYSBOOL[2] = false
                categoryCount--
                act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[2]!! && categoryCount < 5) {
                CATEGORYSBOOL[2] = true
                categoryCount++
                act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_D.setOnClickListener {
            if (CATEGORYSBOOL[3]!! && categoryCount > 0) {
                CATEGORYSBOOL[3] = false
                categoryCount--
                act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[3]!! && categoryCount < 5) {
                CATEGORYSBOOL[3] = true
                categoryCount++
                act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_E.setOnClickListener {
            if (CATEGORYSBOOL[4]!! && categoryCount > 0) {
                CATEGORYSBOOL[4] = false
                categoryCount--
                act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[4]!! && categoryCount < 5) {
                CATEGORYSBOOL[4] = true
                categoryCount++
                act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_F.setOnClickListener {
            if (CATEGORYSBOOL[5]!! && categoryCount > 0) {
                CATEGORYSBOOL[5] = false
                categoryCount--
                act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[5]!! && categoryCount < 5) {
                CATEGORYSBOOL[5] = true
                categoryCount++
                act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_G.setOnClickListener {
            if (CATEGORYSBOOL[6]!! && categoryCount > 0) {
                CATEGORYSBOOL[6] = false
                categoryCount--
                act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[6]!! && categoryCount < 5) {
                CATEGORYSBOOL[6] = true
                categoryCount++
                act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        act_test_ll_popup_needless_H.setOnClickListener {
            if (CATEGORYSBOOL[7]!! && categoryCount > 0) {
                CATEGORYSBOOL[7] = false
                categoryCount--
                act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_emptybox)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            } else if (!CATEGORYSBOOL[7]!! && categoryCount < 5) {
                CATEGORYSBOOL[7] = true
                categoryCount++
                act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_checkbox_white)
                if (categoryCount > 0)
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorBlue))
                else
                    act_test_popup_needless_tv_count.setTextColor(resources.getColor(R.color.colorText))
                act_test_popup_needless_tv_count.text = categoryCount.toString()
            }
        }
        //endregion
        //region 뷰 부분
        act_test_ll_excCategory_edu.setOnClickListener {
            act_test_ll_excCategory_edu.visibility = View.GONE
            CATEGORYSBOOL[0] = false
            categoryCount--
            act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_know.setOnClickListener {
            act_test_ll_excCategory_know.visibility = View.GONE
            CATEGORYSBOOL[1] = false
            categoryCount--
            act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_place.setOnClickListener {
            act_test_ll_excCategory_place.visibility = View.GONE
            CATEGORYSBOOL[2] = false
            categoryCount--
            act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_local.setOnClickListener {
            act_test_ll_excCategory_local.visibility = View.GONE
            CATEGORYSBOOL[3] = false
            categoryCount--
            act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_global.setOnClickListener {
            act_test_ll_excCategory_global.visibility = View.GONE
            CATEGORYSBOOL[4] = false
            categoryCount--
            act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_make.setOnClickListener {
            act_test_ll_excCategory_make.visibility = View.GONE
            CATEGORYSBOOL[5] = false
            categoryCount--
            act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_gov.setOnClickListener {
            act_test_ll_excCategory_gov.visibility = View.GONE
            CATEGORYSBOOL[6] = false
            categoryCount--
            act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        act_test_ll_excCategory_loan.setOnClickListener {
            act_test_ll_excCategory_loan.visibility = View.GONE
            CATEGORYSBOOL[7] = false
            categoryCount--
            act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_emptybox)
            if (categoryCount > 0) {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
            }
            else {
                act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorText))
            }
            act_test_tv_excCategory_count.text = categoryCount.toString()
        }
        //endregion
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
                if (response.isSuccessful && response.body()!!.data.condSummaryList.get(0) != null) {
                    mCondIdx = response.body()!!.data.condSummaryList.get(0).condIdx
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
                    /*
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
                    */
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
                    if(response.body()!!.data.field.a) {
                        fieldCount++
                        FIELDSBOOL[0] = true
                        act_test_iv_check_sector_A.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_a.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.b) {
                        FIELDSBOOL[1] = true
                        fieldCount++
                        act_test_iv_check_sector_B.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_b.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.c) {
                        FIELDSBOOL[2] = true
                        fieldCount++
                        act_test_iv_check_sector_C.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_c.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.d) {
                        FIELDSBOOL[3] = true
                        fieldCount++
                        act_test_iv_check_sector_D.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_d.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.e) {
                        FIELDSBOOL[4] = true
                        fieldCount++
                        act_test_iv_check_sector_E.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_e.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.f) {
                        FIELDSBOOL[5] = true
                        fieldCount++
                        act_test_iv_check_sector_F.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_f.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.g) {
                        FIELDSBOOL[6] = true
                        fieldCount++
                        act_test_iv_check_sector_G.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_g.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.h) {
                        FIELDSBOOL[7] = true
                        fieldCount++
                        act_test_iv_check_sector_H.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_h.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.i) {
                        FIELDSBOOL[8] = true
                        fieldCount++
                        act_test_iv_check_sector_I.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_i.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.j) {
                        FIELDSBOOL[9] = true
                        fieldCount++
                        act_test_iv_check_sector_J.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_j.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.k) {
                        FIELDSBOOL[10] = true
                        fieldCount++
                        act_test_iv_check_sector_K.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_k.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.l) {
                        FIELDSBOOL[11] = true
                        fieldCount++
                        act_test_iv_check_sector_L.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_l.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.m) {
                        FIELDSBOOL[12] = true
                        fieldCount++
                        act_test_iv_check_sector_M.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_m.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.n) {
                        FIELDSBOOL[13] = true
                        fieldCount++
                        act_test_iv_check_sector_N.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_n.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.o) {
                        FIELDSBOOL[14] = true
                        fieldCount++
                        act_test_iv_check_sector_O.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_o.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.p) {
                        FIELDSBOOL[15] = true
                        fieldCount++
                        act_test_iv_check_sector_P.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_p.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.q) {
                        FIELDSBOOL[16] = true
                        fieldCount++
                        act_test_iv_check_sector_Q.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_q.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.r) {
                        FIELDSBOOL[17] = true
                        fieldCount++
                        act_test_iv_check_sector_R.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_r.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.s) {
                        FIELDSBOOL[18] = true
                        fieldCount++
                        act_test_iv_check_sector_S.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_s.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.t) {
                        FIELDSBOOL[19] = true
                        fieldCount++
                        act_test_iv_check_sector_T.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_t.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.u) {
                        FIELDSBOOL[20] = true
                        fieldCount++
                        act_test_iv_check_sector_U.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_u.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.field.v) {
                        FIELDSBOOL[21] = true
                        fieldCount++
                        act_test_iv_check_sector_V.setImageResource(R.drawable.icn_checkbox_white)
                        act_test_ll_field_v.visibility = View.VISIBLE
                    }
                    if(fieldCount > 0)
                        act_test_tv_field_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_field_count.text = fieldCount.toString()
                    //endregion
                    //region excCategory
                    if(response.body()!!.data.excCategory.edu) {
                        CATEGORYSBOOL[0] = true
                        act_test_popup_needless_iv_A.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_edu.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.know) {
                        CATEGORYSBOOL[1] = true
                        act_test_popup_needless_iv_B.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_know.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.place) {
                        CATEGORYSBOOL[2] = true
                        act_test_popup_needless_iv_C.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_place.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.local) {
                        CATEGORYSBOOL[3] = true
                        act_test_popup_needless_iv_D.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_local.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.global) {
                        CATEGORYSBOOL[4] = true
                        act_test_popup_needless_iv_E.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_global.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.make) {
                        CATEGORYSBOOL[5] = true
                        act_test_popup_needless_iv_F.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_make.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.gov) {
                        CATEGORYSBOOL[6] = true
                        act_test_popup_needless_iv_G.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_gov.visibility = View.VISIBLE
                    }
                    if(response.body()!!.data.excCategory.loan) {
                        CATEGORYSBOOL[7] = true
                        act_test_popup_needless_iv_H.setImageResource(R.drawable.icn_checkbox_white)
                        categoryCount++
                        act_test_ll_excCategory_loan.visibility = View.VISIBLE
                    }
                    if(categoryCount > 0)
                        act_test_tv_excCategory_count.setTextColor(resources.getColor(R.color.colorBlue))
                    act_test_tv_excCategory_count.text = categoryCount.toString()
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
/* 테스트
                    //region 요청바디에 들어갈 객체 생성
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
                    for(a in 0..21)
                        fieldJson.addProperty(FIELDSNAME[a], FIELDSBOOL[a])
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
                    for(a in 0..7)
                        excCategoryJson.addProperty(CATEGORYSNAME[a], CATEGORYSBOOL[a])
                    jsonObject.add("excCategory", excCategoryJson)
                    //endregion
                    putSmatchingCondsCountResponse(jsonObject)*/
                }
            }
        })
    }
    private fun putUserSmatchingResponse(condIdx: Int) {
        //region 요청바디에 들어갈 객체 생성
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
        for(a in 0..21)
            fieldJson.addProperty(FIELDSNAME[a], FIELDSBOOL[a])
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
        for(a in 0..7)
            excCategoryJson.addProperty(CATEGORYSNAME[a], CATEGORYSBOOL[a])
        jsonObject.add("excCategory", excCategoryJson)
        //endregion
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
                    refresh.putExtra("page", 0)
                    startActivity(refresh)//Start the same Activity
                    finish() //finish Activity.
                }
            }
        })
    }
    /* 테스트
    private fun putSmatchingCondsCountResponse(jsonObject: JsonObject){
        val putSmatchingCondsCountResponse = networkService.putSmatchingCondsCountResponse("application/json", jsonObject)
        putSmatchingCondsCountResponse.enqueue(object : Callback<PutSmatchingCount> {
            override fun onFailure(call: Call<PutSmatchingCount>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<PutSmatchingCount>, response: Response<PutSmatchingCount>) {
                if (response.isSuccessful) {
                    act_test_tv_smatching_count.text = response.body()!!.data.toString()
                    if(response.body()!!.data > 0) {
                        act_test_rl.setBackgroundColor(resources.getColor(R.color.colorBlue))
                        act_test_tv_smatching_count.setTextColor(resources.getColor(R.color.colorWhite))
                    } else {
                        act_test_rl.setBackgroundColor(resources.getColor(R.color.colorBackgroundshallow))
                        act_test_tv_smatching_count.setTextColor(resources.getColor(R.color.colorText))
                    }
                }
            }
        })
    }*/
}