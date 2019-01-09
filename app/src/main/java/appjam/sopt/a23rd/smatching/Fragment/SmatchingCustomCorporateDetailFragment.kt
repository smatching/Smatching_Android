package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmatchingCustomCorporateDetailFragment : Fragment() {
    var url : String = ""
    var phone : String = ""
    var noticeIdx : Int = -1
    var scrap : Int = 0
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        noticeIdx = (activity as MainActivity).index
        getNoticeScrap()
        if(scrap == 1)
            fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_yellow)
        else
            fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_grey)
        getDetailContentResponse()
        return inflater.inflate(R.layout.fragment_detailcontent, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_detailcontent_iv_call.setOnClickListener{

        }
        fragment_detailcontent_iv_link.setOnClickListener{

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
                        fragment_detailcontent_tv_supportperiod.setText(data.start_date + " ~ " + data.end_date)
                        fragment_detailcontent_sv_supportperiod.setText(data.start_date + " ~ " + data.end_date)
                        fragment_detailcontent_sv_summary.setText(data.summary)
                        fragment_detailcontent_sv_target.setText(data.target)
                        fragment_detailcontent_sv_content.setText(data.content)
                        fragment_detailcontent_sv_institution.setText(data.institution)
                        fragment_detailcontent_sv_department.setText(data.part)
                        fragment_detailcontent_sv_phone.setText(data.phone)
//                        url = data.original_url.toString()
//                        phone = data.phone.toString()
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
                    if(response.body()!!.data == 1)
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_yellow)
                    else
                        fragment_detailcontent_iv_scrap.setImageResource(R.drawable.icn_scrap_grey)
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
                    scrap = response.body()!!.data
                }
            }
        })
    }
}
