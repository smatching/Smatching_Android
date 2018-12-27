package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.HomeData
import appjam.sopt.a23rd.smatching.R

class HomeRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<HomeData>) : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_home, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.supervisor.text = dataList[position].supervisor
        holder.deadline.text = dataList[position].deadline
        holder.title.text = dataList[position].title
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val supervisor : TextView = itemView.findViewById(R.id.rv_item_home_tv_supervisor) as TextView
        val deadline : TextView = itemView.findViewById(R.id.rv_item_home_tv_deadline) as TextView
        val title : TextView = itemView.findViewById(R.id.rv_item_home_tv_title) as TextView
        //val scrap : ImageView = itemView.findViewById(R.id.rv_item_home_iv_scrap) as ImageView
    }
}