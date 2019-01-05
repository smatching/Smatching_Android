package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.R.drawable.line
import org.jetbrains.anko.lines
import org.jetbrains.anko.rightPadding


class HomeRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<NoticeData>)
    : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>() {
    var currentView: Int = 0
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
        }
        else {
            holder.deadline.text = dataList[position].dday.toString()
            holder.tag.text = "D-"
        }
        holder.title.text = dataList[position].title
        if(currentView == 0)
            holder.scrap.visibility = View.INVISIBLE
        else {
            holder.scrap.visibility = View.VISIBLE
            when(dataList[position].scrap){//자바에서의 switch
                0 -> return holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
                1 -> return holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
            }
        }

    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val supervisor : TextView = itemView.findViewById(R.id.rv_item_home_tv_supervisor) as TextView
        val tag : TextView = itemView.findViewById(R.id.rv_item_home_tv_tag) as TextView
        val deadline : TextView = itemView.findViewById(R.id.rv_item_home_tv_deadline) as TextView
        val title : TextView = itemView.findViewById(R.id.rv_item_home_tv_title) as TextView
        val scrap : ImageView = itemView.findViewById(R.id.rv_item_home_iv_scrap) as ImageView
        val width : LinearLayout = itemView.findViewById(R.id.rv_item_home_ll_dday) as LinearLayout
    }
}