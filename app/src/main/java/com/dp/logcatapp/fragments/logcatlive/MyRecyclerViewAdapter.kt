package com.dp.logcatapp.fragments.logcatlive

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dp.logcat.Log
import com.dp.logcatapp.R

class MyRecyclerViewAdapter(context: Context) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(),
        View.OnClickListener {
    private val data = mutableListOf<Log>()
    private var onClickListener: ((View) -> Unit)? = null

    private val assertColor = ContextCompat.getColor(context, R.color.priority_assert)
    private val debugColor = ContextCompat.getColor(context, R.color.priority_debug)
    private val errorColor = ContextCompat.getColor(context, R.color.priority_error)
    private val infoColor = ContextCompat.getColor(context, R.color.priority_info)
    private val verboseColor = ContextCompat.getColor(context, R.color.priority_verbose)
    private val warningColor = ContextCompat.getColor(context, R.color.priority_warning)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val log = data[position]
        holder.date.text = log.date
        holder.time.text = log.time
        holder.pid.text = log.pid
        holder.tid.text = log.tid
        holder.priority.text = log.priority
        holder.tag.text = log.tag
        holder.message.text = log.msg

        val priorityColor = when (log.priority) {
            "A" -> assertColor
            "D" -> debugColor
            "E" -> errorColor
            "I" -> infoColor
            "W" -> warningColor
            else -> verboseColor
        }
        holder.priority.setBackgroundColor(priorityColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_logcat_live_list_item, parent, false)
        view.setOnClickListener(this)
        return MyViewHolder(view)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logcat_list_item_root -> onClickListener?.invoke(v)
        }
    }

    override fun getItemCount() = data.size

    internal fun addItem(item: Log) {
        val size = data.size
        data += item
        notifyItemInserted(size)
    }

    internal fun addItems(items: List<Log>) {
        val size = data.size
        data += items
        notifyItemRangeInserted(size, items.size)
    }

    operator fun get(index: Int) = data[index]

    internal fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

    internal fun setOnClickListener(onClickListener: (View) -> Unit) {
        this.onClickListener = onClickListener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val pid: TextView = itemView.findViewById(R.id.pid)
        val tid: TextView = itemView.findViewById(R.id.tid)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val tag: TextView = itemView.findViewById(R.id.tag)
        val message: TextView = itemView.findViewById(R.id.message)
    }
}