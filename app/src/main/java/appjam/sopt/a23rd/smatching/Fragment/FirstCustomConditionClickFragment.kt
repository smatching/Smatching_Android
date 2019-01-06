package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.FirstSmatchingCustomActivity
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.Test2Activity
import appjam.sopt.a23rd.smatching.TestActivity
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_first_custom_condition_click.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
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
        //
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        //

        fragment_first_custom_condition_click_ll.setOnClickListener {
            replaceFragment(FirstCustomConditionNotClickFragment())
        }
        fragment_first_custom_condition_click_tv_edit.setOnClickListener {
            startActivity<TestActivity>()
        }
        getUserSmatchingCondResponse()

        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }, 1000)
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_fl, fragment)
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
                    //region 나이
                    if (response.body()!!.data.age.twenty_less)
                        fragment_first_custom_condition_click_tv_age.text = "만 20세 미만"
                    else if (response.body()!!.data.age.twenty_forty)
                        fragment_first_custom_condition_click_tv_age.text = "만 20세 이상 ~ 만 39세 이하"
                    else if (response.body()!!.data.age.forty_more)
                        fragment_first_custom_condition_click_tv_age.text = "만 40세 이상"
                    else if(!response.body()!!.data.age.twenty_less && !response.body()!!.data.age.twenty_forty && response.body()!!.data.age.forty_more)
                        fragment_first_custom_condition_click_tv_age.text = ""
                    //endregion
                    //region 설립 경과 년수
                    val periodList = ArrayList<String>()
                    if (response.body()!!.data.period.yet)
                        periodList.add("예비창업자")
                    if (response.body()!!.data.period.zero_one)
                        periodList.add("0~1년")
                    if (response.body()!!.data.period.one_two)
                        periodList.add("1~2년")
                    if (response.body()!!.data.period.two_three)
                        periodList.add("2~3년")
                    if (response.body()!!.data.period.three_four)
                        periodList.add("3~4년")
                    if (response.body()!!.data.period.four_five)
                        periodList.add("4~5년")
                    if (response.body()!!.data.period.five_six)
                        periodList.add("5~6년")
                    if (response.body()!!.data.period.six_seven)
                        periodList.add("6~7년")
                    if (response.body()!!.data.period.seven_more)
                        periodList.add("7년 이상")
                    fragment_first_custom_condition_click_tv_period.text = periodList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                    //region 기업형태
                    val busiTypeList = ArrayList<String>()
                    if (response.body()!!.data.busiType.midsmall)
                        busiTypeList.add("중소기업")
                    if (response.body()!!.data.busiType.midbig)
                        busiTypeList.add("중견기업")
                    if (response.body()!!.data.busiType.big)
                        busiTypeList.add("대기업")
                    if (response.body()!!.data.busiType.sole)
                        busiTypeList.add("1인창조기업")
                    if (response.body()!!.data.busiType.small)
                        busiTypeList.add("소상공인")
                    if (response.body()!!.data.busiType.tradi)
                        busiTypeList.add("전통시장")
                    if (response.body()!!.data.busiType.pre)
                        busiTypeList.add("예비창업자")
                    fragment_first_custom_condition_click_tv_busiType.text = busiTypeList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                    //region 업종
                    val fielsList = ArrayList<String>()
                    if (response.body()!!.data.field.a)
                        fielsList.add("농업, 임업 및 어업")
                    if (response.body()!!.data.field.b)
                        fielsList.add("광업")
                    if (response.body()!!.data.field.c)
                        fielsList.add("제조업")
                    if (response.body()!!.data.field.d)
                        fielsList.add("전기, 가스, 증기 및 공기 조절 공급업")
                    if (response.body()!!.data.field.e)
                        fielsList.add("수도, 하수 및 폐기물 처리, 원료 재생업")
                    if (response.body()!!.data.field.f)
                        fielsList.add("건설업")
                    if (response.body()!!.data.field.g)
                        fielsList.add("도매 및 소매업")
                    if (response.body()!!.data.field.h)
                        fielsList.add("운수 및 창고업")
                    if (response.body()!!.data.field.i)
                        fielsList.add("숙박 및 음식점업")
                    if (response.body()!!.data.field.j)
                        fielsList.add("정보통신업")
                    if (response.body()!!.data.field.k)
                        fielsList.add("금융 및 보험업")
                    if (response.body()!!.data.field.l)
                        fielsList.add("부동산업")
                    if (response.body()!!.data.field.m)
                        fielsList.add("전문, 과학 및 기술 서비스업")
                    if (response.body()!!.data.field.n)
                        fielsList.add("사업시설 관리, 사업지원 및 임대서비스업")
                    if (response.body()!!.data.field.o)
                        fielsList.add("공공 행정, 국방 및 사회보장 행정")
                    if (response.body()!!.data.field.p)
                        fielsList.add("교육 서비스업")
                    if (response.body()!!.data.field.q)
                        fielsList.add("보건업 및 사회복지 서비스업")
                    if (response.body()!!.data.field.r)
                        fielsList.add("예술, 스포츠 및 기타 개인 서비스업")
                    if (response.body()!!.data.field.s)
                        fielsList.add("협회 및 단체, 수리 및 기타 개인 서비스업")
                    if (response.body()!!.data.field.t)
                        fielsList.add("가구 내 고용활동 및 달리 분류되지 않은 자가 소비 생산활동")
                    if (response.body()!!.data.field.u)
                        fielsList.add("국제 및 외국기관")
                    if (response.body()!!.data.field.v)
                        fielsList.add("4차산업분야")
                    fragment_first_custom_condition_click_tv_field.text = fielsList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                    //region 필요 없는 지원
                    val exceCategoryList = ArrayList<String>()
                    if (response.body()!!.data.excCategory.edu)
                        exceCategoryList.add("창업교육, 창업 멘토링")
                    if (response.body()!!.data.excCategory.know)
                        exceCategoryList.add("지식재산권 (특허, 저작권 등)")
                    if (response.body()!!.data.excCategory.place)
                        exceCategoryList.add("시설, 공간")
                    if (response.body()!!.data.excCategory.local)
                        exceCategoryList.add("국내판로 확대")
                    if (response.body()!!.data.excCategory.global)
                        exceCategoryList.add("해외판로 확대")
                    if (response.body()!!.data.excCategory.make)
                        exceCategoryList.add("시제품 제작 제조양산")
                    if (response.body()!!.data.excCategory.gov)
                        exceCategoryList.add("정부 대출 지원")
                    if (response.body()!!.data.excCategory.loan)
                        exceCategoryList.add("무대출 자금 지원")
                    fragment_first_custom_condition_click_tv_exceCategory.text = exceCategoryList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                    //region 우대사랑
                    val advantageList = ArrayList<String>()
                    if (response.body()!!.data.advantage.retry)
                        advantageList.add("재도전기업")
                    if (response.body()!!.data.advantage.woman)
                        advantageList.add("여성기업")
                    if (response.body()!!.data.advantage.disabled)
                        advantageList.add("장애인기업")
                    if (response.body()!!.data.advantage.social)
                        advantageList.add("사회적기업")
                    if (response.body()!!.data.advantage.sole)
                        advantageList.add("1인창조기업")
                    if (response.body()!!.data.advantage.fourth)
                        advantageList.add("4차산업관련기업")
                    if (response.body()!!.data.advantage.univ)
                        advantageList.add("대학(원)생")
                    if (response.body()!!.data.advantage.togather)
                        advantageList.add("공동창업")
                    fragment_first_custom_condition_click_tv_advantage.text = advantageList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                }
            }
        })
    }
}