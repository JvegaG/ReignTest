package com.example.reigntest.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.reigntest.R
import com.example.reigntest.Service.HackerNews
import java.util.*

class ListAdapter(
    private val context: Context,
    private val data: List<HackerNews>,
    private val actualTime: Date
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemsView = inflater.inflate(R.layout.list_model, parent, false)

        val title: TextView = convertView!!.findViewById(R.id.txt_title)
        val authorTime: TextView = convertView!!.findViewById(R.id.txt_author_time)

        val new: HackerNews = data[position]
        title.setText(new.hits[position].title ?: new.hits[position].story_title)

        val authorTimeText = new.hits[position].author + " - " + getCreatedTime(new.hits[position].creaetd_at)
        authorTime.setText(authorTimeText)

        return itemsView
    }

    fun getCreatedTime(createdDate: String?): String? {
        var restTime: String? = null
        return restTime
    }
}