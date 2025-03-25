package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.adapters.TaskAdapter
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.FragmentTodoBinding

class TodoFragment : GenericFragment<FragmentTodoBinding>(FragmentTodoBinding::inflate) {
    private lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getTasks())
    }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)

        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.setHasFixedSize(true)
        binding.rvTasks.adapter = taskAdapter
    }

    private fun getTasks() = listOf<Task>(
        Task(
            id = "1",
            description = "Estudar Kotlin para o desenvolvimento Android",
            title = "Estudar Kotlin",
            TaskStatus.TODO
        ),
        Task(
            id = "2",
            description = "Participar de treinamentos para ajudar na operação",
            title = "Participação de treinamentos mobile",
            TaskStatus.TODO
        ),
        Task(
            id = "3",
            description = "Redirecionador para o aplicativo Whatsapp dentro do app",
            title = "Implementar redirecionador",
            TaskStatus.TODO
        ),
    )

}