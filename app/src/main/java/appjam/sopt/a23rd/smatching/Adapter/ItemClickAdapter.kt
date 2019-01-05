package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Data.DetailData
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R

class ItemClickAdapter(val ctx : Context, val dataList : ArrayList<DetailData>)
    : RecyclerView.Adapter<ItemClickAdapter.Holder>() {
    var currentView: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemClickAdapter.Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.fragment_detailcontent, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.update_date.text = dataList[position].reg_date
        holder.title.text = dataList[position].title
        holder.institution.text = dataList[position].institution
        holder.supportperiod.text = dataList[position].start_date + " ~ " + dataList[position].end_date
        holder.sv_summary.text = dataList[position].summary
        holder.sv_target.text = dataList[position].target
        holder.sv_content.text = dataList[position].content
        holder.sv_institution.text = dataList[position].institution
        holder.sv_department.text = dataList[position].part
        holder.sv_phonenumber.text = dataList[position].phone

        /*
        // 스크랩이 되지 않았을 경우
        if(dataList[position].scrap == 0)
            holder.scrap.setImageResource(R.drawable.icn_scrap_grey)
        // 스크랩이 됐을 경우
        else
            holder.scrap.setImageResource(R.drawable.icn_scrap_yellow)
            */
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val update_date: TextView = itemView.findViewById(R.id.fragment_detailcontent_tv_update_date)
        val title : TextView = itemView.findViewById(R.id.fragment_detailcontent_tv_title)
        val institution: TextView = itemView.findViewById(R.id.fragment_detailcontent_tv_institution)
        val supportperiod: TextView = itemView.findViewById(R.id.fragment_detailcontent_tv_supportperiod)
        val sv_supportperiod: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_supportperiod)
        val sv_summary: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_summary)
        val sv_target: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_target)
        val sv_content: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_content)
        val sv_institution: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_institution)
        val sv_department: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_department)
        val sv_phonenumber: TextView = itemView.findViewById(R.id.fragment_detailcontent_sv_phone)
        val scrap: ImageView = itemView.findViewById(R.id.fragment_detailcontent_iv_scrap)
    }
}
