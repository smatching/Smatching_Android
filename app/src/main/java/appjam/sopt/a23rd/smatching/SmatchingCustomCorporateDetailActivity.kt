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
import kotlinx.android.synthetic.main.activity_smatching_custom_corporate_detail.*


class SmatchingCustomCorporateDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smatching_custom_corporate_detail)
        val toolbar = findViewById<Toolbar>(R.id.act_smatching_custom_corporate_detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        act_smatching_custom_corporate_detail_toolvar_title.text = "기업형태"
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
}