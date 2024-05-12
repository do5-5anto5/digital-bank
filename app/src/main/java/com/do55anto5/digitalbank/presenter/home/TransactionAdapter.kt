package com.do55anto5.digitalbank.presenter.home

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.databinding.TransactionItemBinding
import com.do55anto5.digitalbank.util.GetMask

class TransactionAdapter(
    private val context: Context,
    private val selectedTransaction: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK) {

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
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)

        transaction.operation?.let {
            holder.binding.txtTransactionDescription.text =
                TransactionOperation.getOperation(it)

            holder.binding.txtTransactionType.text = TransactionType.getType(it).toString()

            holder.binding.txtTransactionType.backgroundTintList =
                if(transaction.type == TransactionType.CASH_IN) {
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_cash_in))
            } else {
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_cash_out))
            }

        }

        holder.binding.txtTransactionValue.text = GetMask.getFormattedValue(transaction.amount)
        holder.binding.txtTransactionDate.text =
            GetMask.getFormattedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)

        holder.itemView.setOnClickListener {
            selectedTransaction(transaction)
        }
    }

    inner class ViewHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}