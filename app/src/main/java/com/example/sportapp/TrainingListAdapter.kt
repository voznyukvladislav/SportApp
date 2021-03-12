package com.example.sportapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TrainingListAdapter(var values: MutableList<ListItem>, var context: Context?) : RecyclerView.Adapter<TrainingListAdapter.TrainingListViewHolder>() {
    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingListViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.training_list_item, parent, false)
        return TrainingListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrainingListAdapter.TrainingListViewHolder, position: Int) {
        var item: ListItem = values.get(position)
        holder.nameView!!.setText(item.getName());
        holder.coolerView!!.setText(item.getCoolerName());

        holder.nameView!!.setOnClickListener {
            Toast.makeText(context, "Hello " + position.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.deleteButton!!.setOnClickListener {
            values.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, values.size - position)
        }
    }

    class TrainingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameView: TextView? = null
        var coolerView: TextView? = null
        var deleteButton: Button? = null
        init {
            nameView = itemView.findViewById<TextView>(R.id.listItemTextView)
            coolerView = itemView.findViewById<TextView>(R.id.listItemTextView2)
            deleteButton = itemView.findViewById<Button>(R.id.delButton)
        }
    }
}