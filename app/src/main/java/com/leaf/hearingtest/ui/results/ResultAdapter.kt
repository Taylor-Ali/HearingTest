package com.leaf.hearingtest.ui.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leaf.hearingtest.R
import com.leaf.hearingtest.databinding.ListItemResultBinding


class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private val results = ArrayList<Result>()

    fun setItems(items: List<Result>) {
        this.results.clear()
        this.results.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }


    inner class ViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemResultBinding.bind(itemView)
        fun update(result: Result) {
            binding.let {
                it.firstName.text = "${result.firstName} ${result.lastName}"
                it.emailAddress.text = result.emailAddress
                it.score.text = result.score.toString()
            }
        }
    }
}
