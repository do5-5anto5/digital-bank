package com.do55anto5.digitalbank.presenter.features.transfer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.TransferUserItemBinding
import com.squareup.picasso.Picasso

class TransferUserAdapter(
    private val selectedUser: (User) -> Unit
) : ListAdapter<User, TransferUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransferUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        with(holder) {

            loadUserImage(this, user)

            itemView.setOnClickListener {
                selectedUser(user)
            }

            binding.txtUserName.text = user.name
        }

    }

    private fun loadUserImage(holder: ViewHolder, user: User) {

        if (user.image.isNotEmpty()) {
            Picasso
                .get()
                .load(user.image)
                .fit().centerCrop()
                .into(holder.binding.userImage)
        } else {
            holder.binding.userImage.setImageResource(R.drawable.ic_user_place_holder)
        }

    }

    inner class ViewHolder(val binding: TransferUserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}