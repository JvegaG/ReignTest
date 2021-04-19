package com.example.reigntest.Adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reigntest.DataBase.DeletedDataHelper
import com.example.reigntest.DetailActivity
import com.example.reigntest.R
import com.example.reigntest.Service.HitNews
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class RecyclerAdapter(
    context: Context,
    private val mData: List<HitNews>,
    private val mCurrentTime: LocalDateTime,
) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var data = mutableListOf<HitNews>()
    var mContext: Context
    var currentTime = mCurrentTime

    init {
        this.data = mData as MutableList<HitNews>
        mContext = context
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.txt_title)
        private val authorTime: TextView = itemView.findViewById(R.id.txt_author_time)

        fun bind(mTitle: String, mAuthor: String) {
            title.text = mTitle
            authorTime.text = mAuthor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_model, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemInfo = data[position]
        val title = itemInfo.title ?: itemInfo.story_title
        val authorTime = itemInfo.author + " - " + getTime(itemInfo.created_at)

        holder.bind(title, authorTime)
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("URL", itemInfo.url?:itemInfo.story_url)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size

    fun addDataItem(hits: List<HitNews>, time: LocalDateTime){
        data.clear()
        data = hits as MutableList<HitNews>
        currentTime = time
    }

    fun delete(position: Int) {
        val dbHelper = DeletedDataHelper(mContext)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DeletedDataHelper.FeedReader.FeedEntry.COLUMN_NAME_STORY_ID, data.get(position).story_id)
        }
        db?.insert(DeletedDataHelper.FeedReader.FeedEntry.TABLE_NAME, null, values)

        data.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getTime(createdDate: String?): String? {
        var createAt = createdDate
        createAt = createdDate!!.substring(0, createAt!!.length - 1)
        val timeCreated = LocalDateTime.parse(createAt)

        val x = timeCreated.until(currentTime, ChronoUnit.MILLIS)
        val sec = x / 1000
        val min = sec / 60
        val hour = min / 60
        val days = hour / 24

        return when {
            days > 1 -> {
                days.toString() + "days ago"
            }
            days.compareTo(1) == 0 -> {
                "Yesterday"
            }
            else -> {
                if ((hour % 24) > 0) {
                    if ((min % 60) > 30) {
                        (hour % 24).toString() + ".5h"
                    } else {
                        (hour % 24).toString() + "h"
                    }
                } else {
                    if ((min % 60) > 0) {
                        (min % 60).toString() + "m"
                    } else {
                        "Now"
                    }
                }
            }
        }
    }


}