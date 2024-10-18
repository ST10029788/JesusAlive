package com.scriptsquad.reflectionsoffaith.Notices_Page.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.scriptsquad.reflectionsoffaith.Notices_Page.FilterNoticeBoard
import com.scriptsquad.reflectionsoffaith.Notices_Page.activities.Notice_Details_Activity
import com.scriptsquad.reflectionsoffaith.Notices_Page.models.modelNotice
import com.scriptsquad.reflectionsoffaith.Devotional_Material.MyApplication
import com.scriptsquad.reflectionsoffaith.databinding.RowNoticeBinding

class AdapterNotice : RecyclerView.Adapter<AdapterNotice.HolderNotice>, Filterable {

    private var context: Context
    var noticeArrayList: List<modelNotice> = emptyList()
    private var filteredNoticeList: List<modelNotice> = emptyList()
    private var filter: FilterNoticeBoard? = null

    constructor(context: Context, noticeArrayList: List<modelNotice>) : super() {
        this.context = context
        this.noticeArrayList = noticeArrayList
        this.filteredNoticeList = noticeArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNotice {
        val binding = RowNoticeBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderNotice(binding)
    }

    override fun onBindViewHolder(holder: HolderNotice, position: Int) {
        val model = filteredNoticeList[position]

        holder.titleTv.text = model.title
        holder.descriptionTv.text = model.description
        holder.dateTv.text = MyApplication.formatTimestampDate(model.timestamp)

        MyApplication.loadBookImage(
            model.imageUrl,
            holder.progressBar,
            context,
            holder.noticeImageIv
        )

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Notice_Details_Activity::class.java).apply {
                putExtra("noticeId", model.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = filteredNoticeList.size

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = FilterNoticeBoard(noticeArrayList, this)
        }
        return filter as FilterNoticeBoard
    }

    inner class HolderNotice(private val binding: RowNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val progressBar = binding.progressBar
        val titleTv = binding.titleTv
        val descriptionTv = binding.descriptionTv
        val dateTv = binding.dateTv
        val noticeImageIv = binding.noticeImageIv
    }
}



