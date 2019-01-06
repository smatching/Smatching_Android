package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
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
import appjam.sopt.a23rd.smatching.*
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_second_custom_condition_click.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SecondCustomConditionClickFragment : Fragment(){
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_custom_condition_click, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        //

        fragment_second_custom_condition_click_ll.setOnClickListener {
            replaceFragment(SecondCustomConditionNotClickFragment())
        }
        fragment_second_custom_condition_click_tv_edit.setOnClickListener {
            startActivity<Test2Activity>()
        }
        getUserSmatchingCondResponse()

        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }, 1000)
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_second_custom_fl, fragment)
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
                if (response.isSuccessful && response.body()!!.data.condSummaryList.size == 2) {
                    getUserSmatchingListResponse(response.body()!!.data.condSummaryList.get(1).condIdx)
                    fragment_second_custom_condition_click_tv_smatching_name.text = response.body()!!.data.condSummaryList.get(1).condName
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
                    fragment_second_custom_condition_click_tv_location.text = locationList.toString()
                            .replace(",", "")  //remove the commas
                            .replace("@", ", ")  //add the commas
                            .replace(" ,", ", ")
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                            .trim()
                    */
                    //region 나이
                    if (response.body()!!.data.age.twenty_less)
                        fragment_second_custom_condition_click_tv_age.text = "만 20세 미만"
                    else if (response.body()!!.data.age.twenty_forty)
                        fragment_second_custom_condition_click_tv_age.text = "만 20세 이상 ~ 만 39세 이하"
                    else if (response.body()!!.data.age.forty_more)
                        fragment_second_custom_condition_click_tv_age.text = "만 40세 이상"
                    else if(!response.body()!!.data.age.twenty_less && !response.body()!!.data.age.twenty_forty && response.body()!!.data.age.forty_more)
                        fragment_second_custom_condition_click_tv_age.text = ""
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
                    fragment_second_custom_condition_click_tv_period.text = periodList.toString()
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
                    fragment_second_custom_condition_click_tv_busiType.text = busiTypeList.toString()
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
                    fragment_second_custom_condition_click_tv_field.text = fielsList.toString()
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
                    fragment_second_custom_condition_click_tv_exceCategory.text = exceCategoryList.toString()
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
                    fragment_second_custom_condition_click_tv_advantage.text = advantageList.toString()
                            .replace("[", "")  //remove the right bracket
                            .replace("]", "")  //remove the left bracket
                    //endregion
                }
            }
        })
    }
}