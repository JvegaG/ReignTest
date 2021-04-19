package com.example.reigntest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.reigntest.Adapter.ItemSwipe
import com.example.reigntest.Adapter.RecyclerAdapter
import com.example.reigntest.DataBase.DeletedDataHelper
import com.example.reigntest.Service.HitNews
import com.example.reigntest.Service.RetrofitBuilder
import com.example.reigntest.Utils.ApiHelper
import com.example.reigntest.Utils.DataViewModel
import com.example.reigntest.Utils.Status
import com.example.reigntest.Utils.ViewModelFactory
import java.time.LocalDateTime
import java.time.ZoneOffset

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var currentTime: LocalDateTime
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var viewModel: DataViewModel
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.news_recycler)
        swipeRefresh = findViewById(R.id.refresh)
        swipeRefresh.setColorSchemeResources(R.color.purple_500)

        val divider = DividerItemDecoration(
            recycler.context,
            LinearLayoutManager(applicationContext).orientation
        )

        recycler.addItemDecoration(divider)

        setUpViewModel()
        setUpUIRecycler()
        setupObservers()

        swipeRefresh.setOnRefreshListener {
            if (internetConnection()) {
                setUpViewModel()
                setUpUIRecycler()
                setupObservers()
            } else {
                Toast.makeText(applicationContext, "No Network Connection", Toast.LENGTH_LONG).show()
                swipeRefresh.isRefreshing = false
            }
        }

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.dataFromService))
        ).get(DataViewModel::class.java)
    }

    private fun setUpUIRecycler() {
        currentTime = LocalDateTime.now(ZoneOffset.UTC)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        adapter = RecyclerAdapter(applicationContext, arrayListOf(), currentTime)
        recycler.adapter = adapter

        ItemTouchHelper(object : ItemSwipe(0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.delete(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(recycler)

    }

    private fun setupObservers() {
        viewModel.getInfoModel().observe(this, Observer {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        recycler.visibility = View.VISIBLE
                        swipeRefresh.isRefreshing = false

                        currentTime = LocalDateTime.now(ZoneOffset.UTC)

                        val dataDeleted = readDeletedData()
                        var dataFiltered: List<HitNews> = arrayListOf()
                        if (dataDeleted != null) {
                            dataFiltered = it.data!!.filterNot { data -> dataDeleted.any { it == data.story_id } }
                        }

                        val dataList = if (dataFiltered.isNotEmpty()) dataFiltered else it.data
                        retrieveList(dataList!!, currentTime)

                    }
                    Status.ERROR -> {
                        recycler.visibility = View.VISIBLE
                        swipeRefresh.isRefreshing = false
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        swipeRefresh.isRefreshing = true
                        recycler.visibility = View.GONE
                    }
                }
            }
        })

    }

    private fun retrieveList(hits: List<HitNews>, time: LocalDateTime) {
        adapter.apply {
            addDataItem(hits, time)
            notifyDataSetChanged()
        }
    }

    private fun readDeletedData(): List<Int>? {
        val dbHelper = DeletedDataHelper(applicationContext)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, DeletedDataHelper.FeedReader.FeedEntry.COLUMN_NAME_STORY_ID)

        val cursor = db.query(
            DeletedDataHelper.FeedReader.FeedEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val itemIds = mutableListOf<Int>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(DeletedDataHelper.FeedReader.FeedEntry.COLUMN_NAME_STORY_ID))
                itemIds.add(itemId)
            }
        }
        return itemIds
    }

    private fun internetConnection(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }
}