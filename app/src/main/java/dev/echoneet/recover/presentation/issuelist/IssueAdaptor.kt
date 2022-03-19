package dev.echoneet.recover.presentation.issuelist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.echoneet.recover.R
import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.databinding.IssueItemBinding

class IssueAdaptor(private val context: Context, private val dataList: ArrayList<Issue>) :
    RecyclerView.Adapter<IssueAdaptor.IssueViewHolder>(), IssueItemTouchHelperListener {
    private var _dismissListener: IssueItemTouchHelperListener? = null

    public fun setOnItemDismissListener(listener: IssueItemTouchHelperListener) {
        _dismissListener = listener
    }

    class IssueViewHolder(
        private val context: Context,
        private val binding: IssueItemBinding,
        private val itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(issue: Issue) {
            binding.issueTitle.text = issue.title
            binding.issueDescription.text = issue.description
            binding.issueCode.text = issue.code
            binding.chip.text = issue.status

            when (issue.status) {
                "Created" -> binding.chip.chipBackgroundColor =
                    ContextCompat.getColorStateList(context, R.color.purple_200)
                "Cancelled" -> binding.chip.chipBackgroundColor =
                    ContextCompat.getColorStateList(context, R.color.teal_200)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issue_item, parent, false)
        val binding: IssueItemBinding = IssueItemBinding.bind(view)
        return IssueViewHolder(context, binding, view)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun updateData(issueList: List<Issue>) {
        dataList.clear()
        dataList.addAll(issueList)
        notifyDataSetChanged()
    }

    override fun onItemDismiss(position: Int) {
        _dismissListener?.onItemDismiss(dataList[position].id)
    }
}