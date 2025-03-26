package br.com.flpbrrs.taskapp.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding,
    private val diffCallback: DiffUtil.ItemCallback<T>,
    private val bind: (item: T, binding: ViewBinding) -> Unit
) : RecyclerView.Adapter<GenericAdapter<T>.BaseViewHolder>() {

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        inflater(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(differ.currentList[position], holder.binding)
    }

    fun submitList(newList: List<T>) {
        differ.submitList(newList)
    }
}