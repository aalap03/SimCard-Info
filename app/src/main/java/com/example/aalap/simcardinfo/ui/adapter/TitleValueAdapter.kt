package com.example.aalap.simcardinfo.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.db.PhoneInfo
import com.example.aalap.simcardinfo.db.SimInfo

class TitleValueAdapter(var context: Context, var list: MutableList<Any>) : RecyclerView.Adapter<TitleValueAdapter.MyHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        val item = list[p1]

        if(item is SimInfo) {
            holder.title.text = item.title
            holder.value.text = item.info
        }else {
            var item1 = item as PhoneInfo
            holder.title.text = item1.title
            holder.value.text = item1.info
        }
    }


    inner class MyHolder (itemview: View): RecyclerView.ViewHolder(itemview){
        var title = itemview.findViewById<TextView>(R.id.item_title)
        var value = itemview.findViewById<TextView>(R.id.item_value)
    }

}