package br.com.flpbrrs.taskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.databinding.ComponentTaskItemBinding

class TaskAdapter(
    private val taskList: List<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder(ComponentTaskItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val task = taskList[position]

        holder.binding.cardTitle.text = task.title;
        holder.binding.cardDescription.text = task.description
    }

    inner class TaskItemViewHolder(
        val binding: ComponentTaskItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}