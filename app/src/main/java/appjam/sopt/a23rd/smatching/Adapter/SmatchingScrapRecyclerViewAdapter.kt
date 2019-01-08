package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.NoticeData
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
        if(dataList[position].dday.toString() > "1000"){
            holder.dday.text = ""
            holder.ddayImg.setImageResource(R.drawable.txt_budgetend)
        }
        else {
            holder.dday.text = dataList[position].dday.toString()
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
}

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val supervisor: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_supervisor)
        val ddayImg : ImageView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_iv_dday)
        val dday: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_dday)
        val title: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_title)
        val scrap: ImageView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_iv_scrap)
    }
    private fun putNoticeScrap(noticeIdx : Int){
        val putNoticeScrap : Call<PutNoticeScrap> = networkService.putNoticeScrap(token, noticeIdx)
        putNoticeScrap.enqueue(object : Callback<PutNoticeScrap> {
            override fun onFailure(call: Call<PutNoticeScrap>, t: Throwable) {
                Log.e("Scrap Setting Fail ", t.toString())
            }

            override fun onResponse(call: Call<PutNoticeScrap>, response: Response<PutNoticeScrap>) {
                if(response.isSuccessful){
                    Log.e("Scrap Setting Success ", response.body()!!.message)
                }
            }
        })
    }
}
