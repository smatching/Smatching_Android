package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Fragment.SmatchingCustomCorporateDetailFragment
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.Put.PutNoticeScrap
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmatchingScrapRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<NoticeData>, val token : String)
    : RecyclerView.Adapter<SmatchingScrapRecyclerViewAdapter.Holder>() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var currentView: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmatchingScrapRecyclerViewAdapter.Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_mypage_smatching_scrap, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.supervisor.text = dataList[position].institution
        /*
        if(dataList[position].dday.toString() > "1000"){
            holder.dday.text = ""
            holder.ddayImg.setImageResource(R.drawable.txt_budgetend)
        }
        else {
            holder.dday.text = dataList[position].dday.toString()
        }*/
        if(dataList[position].dday > 1000) {
            holder.dday.text = "예산 소진시"
            holder.day.text = ""
            holder.ddayImg.setImageResource(R.drawable.txt_budgetend_box)
        } else {
            holder.day.text = "D-"
            holder.dday.text = dataList[position].dday.toString()

            // 7일 이하는 색깔 빨간색으로 바꿔줌
            // 원인 : 함수가 호출될때마다 holder가 초기화되서 불려오는게 아니라 이전 정보가 남아있는 holder 그대로 가져와서 하나씩 덮어쓰는 방식임
            //        그래서 빨간색으로 바꾼게 다른 정보를 표시할때에도 그대로 이어짐
            // 버그 픽스 방법 : 검정색으로 원복해주는 else 문 추가
            if(dataList[position].dday <= 7) {
                if (dataList[position].dday == 0)
                    holder.dday.text = "day"
                holder.day.setTextColor(Color.parseColor("#BF7474"))
                holder.dday.setTextColor(Color.parseColor("#BF7474"))
                holder.ddayImg.setImageResource(R.drawable.box_dday_red)
            }
            else {
                holder.day.setTextColor(Color.parseColor("#9B9B9B"))
                holder.dday.setTextColor(Color.parseColor("#9B9B9B"))
                holder.ddayImg.setImageResource(R.drawable.box_dday_gray)
            }
        }

        holder.title.text = dataList[position].title
        // 스크랩이 되지 않았을 경우
        if(dataList[position].scrap == 0)
            holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
        // 스크랩이 됐을 경우
        else
            holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
        holder.scrap.setOnClickListener{
            Log.d("scrap on/off", dataList[position].scrap.toString())
            if(dataList[position].scrap == 0) {
                putNoticeScrap(dataList[position].noticeIdx)
                dataList[position].scrap = 1
                holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
            }
            else {
                putNoticeScrap(dataList[position].noticeIdx)
                dataList[position].scrap = 0
                holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
            }
        }
        holder.item.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SmatchingCustomCorporateDetailFragment(), dataList[position].noticeIdx)
        }
}

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val supervisor: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_supervisor)
        val ddayImg : ImageView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_iv_dday)
        val dday: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_dday)
        val day: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_day)
        val title: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_title)
        val scrap: ImageView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_iv_scrap)
        val item : RelativeLayout = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_rl_item) as RelativeLayout

    }
    private fun putNoticeScrap(noticeIdx : Int){
        val putNoticeScrap : Call<PutNoticeScrap> = networkService.putNoticeScrap(token, noticeIdx)
        putNoticeScrap.enqueue(object : Callback<PutNoticeScrap> {
            override fun onFailure(call: Call<PutNoticeScrap>, t: Throwable) {
                Log.e("Scrap Setting Fail ", t.toString())
            }

            override fun onResponse(call: Call<PutNoticeScrap>, response: Response<PutNoticeScrap>) {
                if(response.isSuccessful){
                    Log.e("Smatching Scrap Success", response.body()!!.message)
                    if(response.body()!!.data == 1){
                        Log.e("Smatching Scrap", response.body()!!.message)
                        Toast.makeText(ctx, "스크랩 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.e("Smatching Scrap", response.body()!!.message)
                        Toast.makeText(ctx, "스크랩 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
