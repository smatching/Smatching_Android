package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Fragment.SmatchingCustomCorporateDetailFragment
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.Put.PutNoticeScrap
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.R.drawable.line
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import org.jetbrains.anko.lines
import org.jetbrains.anko.rightPadding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<NoticeData>, val token : String)
    : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>() {
    var currentView: Int = 0
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_home, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.supervisor.text = dataList[position].institution
        if(dataList[position].dday.toString() > "1000") {
            holder.deadline.text = "예산 소진시"
            holder.tag.text = ""
        }else if (dataList[position].dday.toString() == "0") {
            holder.deadline.text = "day"
            holder.tag.text = "D-"
        } else {
            holder.deadline.text = dataList[position].dday.toString()
            holder.tag.text = "D-"
        }
        holder.title.text = dataList[position].title
        if(currentView == 0)
            holder.scrap.visibility = View.INVISIBLE
        else {
            holder.scrap.visibility = View.VISIBLE
            // 스크랩이 되지 않았을 경우
            if(dataList[position].scrap == 0)
                holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
            // 스크랩이 됐을 경우
            else
                holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
        }
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
        if(currentView == 0) {
            holder.line.visibility = View.INVISIBLE
        } else {
            holder.line.visibility = View.VISIBLE
        }

    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val supervisor : TextView = itemView.findViewById(R.id.rv_item_home_tv_supervisor) as TextView
        val tag : TextView = itemView.findViewById(R.id.rv_item_home_tv_tag) as TextView
        val deadline : TextView = itemView.findViewById(R.id.rv_item_home_tv_deadline) as TextView
        val title : TextView = itemView.findViewById(R.id.rv_item_home_tv_title) as TextView
        val scrap : ImageView = itemView.findViewById(R.id.rv_item_home_iv_scrap) as ImageView
        val width : LinearLayout = itemView.findViewById(R.id.rv_item_home_ll_dday) as LinearLayout
        val item : RelativeLayout = itemView.findViewById(R.id.rv_item_home_rl_item) as RelativeLayout
        val line : ImageView = itemView.findViewById(R.id.rv_item_home_iv_line) as ImageView
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