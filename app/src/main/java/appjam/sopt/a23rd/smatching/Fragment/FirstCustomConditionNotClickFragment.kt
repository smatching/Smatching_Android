package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_first_custom_condition_notclick.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FirstCustomConditionNotClickFragment : Fragment(){
    var loadingFirstCustomConditionNotClick = 0
    val dataList : ArrayList<CondSummaryListData> by lazy {
        ArrayList<CondSummaryListData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_custom_condition_notclick, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_first_custom_condition_notclick_rl_custom_custom.setOnClickListener {
            replaceFragment(FirstCustomConditionClickFragment())
        }
        getUserSmatchingCondResponse()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragmentBody(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_condition_notclick_fl, fragment)
        transaction.commit()
    }
    private fun getUserSmatchingCondResponse(){
        loadingFirstCustomConditionNotClick = 0
        val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(activity!!))
        getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
            override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
                if(response.isSuccessful) {
                    if (response.body()!!.status == 200 ||response.body()!!.status == 206) {
                        //frag_first_custom_condition_notclick_rl.visibility == View.VISIBLE
                        fragment_first_custom_condition_notclick_tv_name.text = response.body()!!.data.condSummaryList.get(0).condName
                    } else if(response.body()!!.status == 204) {
                        replaceFragmentBody(FirstCustomConditionNotClickEmptyFragment())
                    }
                    loadingFirstCustomConditionNotClick = 1
                }
            }
        })
    }
}