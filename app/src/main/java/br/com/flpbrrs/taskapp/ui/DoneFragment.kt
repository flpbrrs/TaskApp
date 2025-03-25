package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.flpbrrs.taskapp.components.GenericAdapter
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.ComponentTaskItemBinding
import br.com.flpbrrs.taskapp.databinding.FragmentDoneBinding

class DoneFragment : GenericFragment<FragmentDoneBinding>(FragmentDoneBinding::inflate) {
    private lateinit var taskAdapter: GenericAdapter<Task>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getTasks())
    }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = GenericAdapter(
            items = taskList,
            inflater = ComponentTaskItemBinding::inflate
        ) { task, binding ->
            with(binding as ComponentTaskItemBinding) {
                cardTitle.text = task.title
                cardDescription.text = task.description
            }
        }

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun getTasks() = listOf(
        Task(
            id = "4",
            description = "Desenvolvimento de um aplicativo de gestão de tarefas",
            title = "Iniciar projeto de TaskApp",
            TaskStatus.DONE
        )
    )

}