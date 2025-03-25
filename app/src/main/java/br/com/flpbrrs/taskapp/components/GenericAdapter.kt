package br.com.flpbrrs.taskapp.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T>(
    private val items: List<T>,
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding,
    private val bind: (item: T, binding: ViewBinding) -> Unit
) : RecyclerView.Adapter<GenericAdapter<T>.BaseViewHolder>() {
    inner class BaseViewHolder(
        val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = inflater(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(items[position], holder.binding)
    }
}