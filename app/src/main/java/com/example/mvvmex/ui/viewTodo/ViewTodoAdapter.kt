package com.example.mvvmex.ui.viewTodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmex.R
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.utils.Constants
import kotlinx.android.synthetic.main.todo_item.view.*

/**
 * Created by Akhtar on 08/06/20
 */

class ViewTodoAdapter(val mListener: ViewTodoAdapterListener) : RecyclerView.Adapter<ViewTodoAdapter.MyViewHolder>() {

    private var mList: List<TodoTable> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    fun updateList(mList: List<TodoTable>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    /**
     * RecycleView touch event callbacks
     * */
    interface ViewTodoAdapterListener {
        fun onClicked(todoTable: TodoTable, eventType: String)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mListener.onClicked(mList[adapterPosition],Constants.EVENT_EDIT)
            }
            itemView.item_todo_ivDelete.setOnClickListener{
                mListener.onClicked(mList[adapterPosition],Constants.EVENT_DELETE)
            }
        }

        fun bind(todoTable: TodoTable) {
            itemView.item_todo_tvName.text=todoTable.title
            itemView.item_todo_tvContent.text=todoTable.content
        }

    }


}