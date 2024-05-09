package com.do55anto5.digitalbank.presenter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.digitalbank.data.Transaction
import com.do55anto5.digitalbank.databinding.LastTransactionItemBinding
import com.do55anto5.digitalbank.util.GetMask

class LastTransactionsAdapter(
    private val selectedTransaction: () -> Unit
) : ListAdapter<Transaction, LastTransactionsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LastTransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)

        holder.binding.txtTransactionDescription.text = transaction.description
        holder.binding.txtTransactionType.text =
            when (transaction.description) {
                "TRANSFERÊNCIA" -> "T"
                "RECARGA" -> "R"
                "DEPÓSITO" -> "D"
                else -> ""
            }
        holder.binding.txtTransactionValue.text = GetMask.getFormattedValue(transaction.value)
        holder.binding.txtTransactionDate.text =
            GetMask.getFormattedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)

    }

    inner class ViewHolder(val binding: LastTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}