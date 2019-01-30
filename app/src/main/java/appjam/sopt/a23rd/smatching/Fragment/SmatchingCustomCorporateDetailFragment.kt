package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.AllNoticeListFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.DetailData
import appjam.sopt.a23rd.smatching.Get.GetDetailContentResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.Put.PutNoticeScrap
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_detailcontent.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.widget.RelativeLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView


class SmatchingCustomCorporateDetailFragment : Fragment() {
    var url : String = ""
    var phone : String = ""
    var noticeIdx : Int = -1
    var scrap : Int = 0
    var stateDetail = 0
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        noticeIdx = (activity as MainActivity).index
        getNoticeScrap()
        getDetailContentResponse()
        return inflater.inflate(R.layout.fragment_detailcontent, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        stateDetail = 0
        //
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).backButtomVisibility = 1
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.icn_back_white_ver_2)
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
        //(activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBlue))
        (activity as AppCompatActivity).findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorWhite))
        (activity as AppCompatActivity).findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("상세정보")
        (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_bottom_navi_iv_title).visibility = View.INVISIBLE
        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
            stateDetail = 1
        }, 1000)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(scrap == 1){
            fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_yellow)}
        else{
            fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_grey)}
        getNoticeScrap()

        fragment_detailcontent_iv_call.setOnClickListener {
            if (phone != "") {
                if(phone == "링크 참조")
                    toast("링크를 참고하세요.")
                else
                    startActivity(Intent("android.intent.action.DIAL", Uri.parse(phone)))
            }
            else
                toast("전화번호가 없습니다.")
        }
        fragment_detailcontent_iv_link.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        fragment_detailcontent_iv_scrap.setOnClickListener{
            putNoticeScrap(noticeIdx)
        }

    }
    private fun getDetailContentResponse(){
        val getDetailContentResponse = networkService.getDetailContentResponse(noticeIdx)
        getDetailContentResponse.enqueue(object : Callback<GetDetailContentResponse> {
            override fun onFailure(call: Call<GetDetailContentResponse>, t: Throwable) {
                Log.e("DetailContentLoadFail", t.toString())
            }

            override fun onResponse(call: Call<GetDetailContentResponse>, response: Response<GetDetailContentResponse>) {
                Log.e("DetailContentLoad : ", response.body()!!.status.toString())
                if(response.isSuccessful){
                    if(response.body()!!.status == 400)
                        toast(response.body()!!.message)
                    else if(response.body()!!.status == 200)
                    {
                        val data : DetailData = response.body()!!.data
                        fragment_detailcontent_tv_update_date.setText(data.reg_date)
                        fragment_detailcontent_tv_title.setText(data.title)
                        fragment_detailcontent_tv_institution.setText(data.institution)
                        fragment_detailcontent_sv_supportperiod.setText(data.start_date + " ~ " + data.end_date)
                        fragment_detailcontent_sv_summary.setText(data.summary)
                        fragment_detailcontent_sv_target.setText(data.target)
                        fragment_detailcontent_sv_content.setText(data.content)
                        fragment_detailcontent_sv_institution.setText(data.institution)
                        fragment_detailcontent_sv_department.setText(data.part)
                        fragment_detailcontent_sv_phone.setText(data.phone)
                        url = data.origin_url
                        if(data.phone == "") {
                            fragment_detailcontent_iv_call.setImageResource(R.drawable.icn_detailpage_call_grey)
                            phone = ""
                        } else if(data.phone == "링크 참조") {
                            fragment_detailcontent_iv_call.setImageResource(R.drawable.icn_detailpage_calll)
                            phone = "링크 참조"
                        }else {
                            fragment_detailcontent_iv_call.setImageResource(R.drawable.icn_detailpage_calll)
                            phone = "tel:" + data.phone
                        }
                    }
                }
            }
        })
    }
    private fun putNoticeScrap(noticeIdx : Int){
        val putNoticeScrap : Call<PutNoticeScrap> = networkService.putNoticeScrap(SharedPreferenceController.getAuthorization(activity!!), noticeIdx)
        putNoticeScrap.enqueue(object : Callback<PutNoticeScrap> {
            override fun onFailure(call: Call<PutNoticeScrap>, t: Throwable) {
                Log.e("Scrap Setting Fail ", t.toString())
            }

            override fun onResponse(call: Call<PutNoticeScrap>, response: Response<PutNoticeScrap>) {
                if(response.isSuccessful){
                    Log.e("Scrap Setting Success ", response.body()!!.message)
                    if(scrap == 0) {
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_yellow)
                        scrap = 1
                        toast("스크랩 되었습니다.")
                    }
                    else{
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_grey)
                        scrap = 0
                        toast("스크랩 해제 되었습니다.")
                    }
                }
            }
        })
    }
    private fun getNoticeScrap(){
        val getNoticeScrap = networkService.getNoticeScrap(SharedPreferenceController.getAuthorization(activity!!), noticeIdx)
        getNoticeScrap.enqueue(object : Callback<PutNoticeScrap>{
            override fun onFailure(call: Call<PutNoticeScrap>, t: Throwable) {
                Log.e("Scrap Select Fail", t.toString())
            }

            override fun onResponse(call: Call<PutNoticeScrap>, response: Response<PutNoticeScrap>) {
                if(response.isSuccessful){
                    Log.e("Scrap Select Success", response.body()!!.message)
                    Log.e("Scrap Select Success", response.body()!!.data.toString())

                    if(response.body()!!.data == 0) {
                       scrap == 0
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_grey)
                    }
                    else{
                        scrap = 1
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_yellow)
                    }
                }
            }
        })
    }
}
