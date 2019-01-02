package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.rv_item_mypage_smatching_scrap.view.*

class SmatchingScrapRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<NoticeData>)
    : RecyclerView.Adapter<SmatchingScrapRecyclerViewAdapter.Holder>() {
    var currentView: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmatchingScrapRecyclerViewAdapter.Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_mypage_smatching_scrap, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.supervisor.text = dataList[position].institution
        if(dataList[position].dday.toString() > "1000"){
            holder.dday.text = "예산 소진시"
        }
        else {
            holder.dday.text = dataList[position].dday.toString()
        }
        holder.title.text = dataList[position].title
        // 스크랩이 되지 않았을 경우
        if(dataList[position].scrap == 1)
            holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
        // 스크랩이 됐을 경우
        else
            holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val supervisor: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_supervisor)
        val dday: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_dday)
        val title: TextView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_tv_title)
        val scrap: ImageView = itemView.findViewById(R.id.rv_item_mypage_smatching_scrap_iv_scrap)
    }
}