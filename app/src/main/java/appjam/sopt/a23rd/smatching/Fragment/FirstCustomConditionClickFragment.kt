package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.TestActivity
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_first_custom_condition_click.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FirstCustomConditionClickFragment : Fragment(){
    val dataList : ArrayList<CondSummaryListData> by lazy {
        ArrayList<CondSummaryListData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_custom_condition_click, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_first_custom_condition_click_ll.setOnClickListener {
            replaceFragment(FirstCustomConditionNotClickFragment())
        }
        fragment_first_custom_condition_click_tv_edit.setOnClickListener {
            activity!!.startActivity<TestActivity>()
        }
        getUserSmatchingCondResponse()
        //testResponse()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_first_custom_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragment2(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_smatching_fl, fragment)
        transaction.commit()
    }
    private fun getUserSmatchingCondResponse(){
        val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(activity!!))
        getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
            override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
                if (response.isSuccessful && response.body()!!.data.condSummaryList.get(0) != null) {
                    getUserSmatchingListResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                    fragment_first_custom_condition_click_tv_smatching_name.text = response.body()!!.data.condSummaryList.get(0).condName
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
                    val locationList = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                    if (response.body()!!.data.location.jeonbuk)
                        locationList[0] = "전북"
                    else
                        locationList[0] = ""

                    if (response.body()!!.data.location.gangwon)
                        locationList[1] = "강원"
                    else
                        locationList[1] = ""

                    if (response.body()!!.data.location.gwangju)
                        locationList[2] = "광주"
                    else
                        locationList[2] = ""

                    if (response.body()!!.data.location.ulsan)
                        locationList[3] = "울산"
                    else
                        locationList[3] = ""

                    if (response.body()!!.data.location.kyungbuk)
                        locationList[4] = "경북"
                    else
                        locationList[4] = ""

                    if (response.body()!!.data.location.sejong)
                        locationList[5] = "세종"
                    else
                        locationList[5] = ""

                    if (response.body()!!.data.location.chungbuk)
                        locationList[6] = "충북"
                    else
                        locationList[6] = ""

                    if (response.body()!!.data.location.kyungnam)
                        locationList[7] = "경남"
                    else
                        locationList[7] = ""

                    if (response.body()!!.data.location.seoul)
                        locationList[8] = "서울"
                    else
                        locationList[8] = ""

                    if (response.body()!!.data.location.chungnam)
                        locationList[9] = "충남"
                    else
                        locationList[9] = ""

                    if (response.body()!!.data.location.daejeon)
                        locationList[10] = "대전"
                    else
                        locationList[10] = ""

                    if (response.body()!!.data.location.busan)
                        locationList[11] = "부산"
                    else
                        locationList[11] = ""

                    if (response.body()!!.data.location.jeju)
                        locationList[12] = "제주"
                    else
                        locationList[12] = ""

                    if (response.body()!!.data.location.daegu)
                        locationList[13] = "대구"
                    else
                        locationList[13] = ""

                    if (response.body()!!.data.location.kyunggi)
                        locationList[14] = "경기"
                    else
                        locationList[14] = ""

                    if (response.body()!!.data.location.incheon)
                        locationList[15] = "인천"
                    else
                        locationList[15] = ""

                    if (response.body()!!.data.location.jeonnam)
                        locationList[16] = "전남"
                    else
                        locationList[16] = ""

                    if (response.body()!!.data.location.aborad)
                        locationList[17] = "국외"
                    else
                        locationList[17] = ""
                    var count = 0
                    for (a in 0..17) {
                        if (locationList[a] != "")
                        count++
                    }
                    if (count == 18) {
                        for (a in 0..15)
                            locationList[a] = ""
                        locationList[17] = "국내전체"
                        if (locationList[17] != "")
                            locationList[17] = "@" + locationList[17]
                    } else {
                        for (a in 0..17) {
                            var first: Int = 0
                            if (locationList[a] != "" && a != first)
                                locationList[a] = "@" + locationList[a]
                            else
                                first++
                        }
                    }
                    fragment_first_custom_condition_click_tv_location.text = locationList.toString()
                            .replace(",", "")  //remove the commas
                            .replace("@", ", ")  //add the commas
                            .replace(" ,", ", ")
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                            .trim()

                }
            }
        })
    }
}