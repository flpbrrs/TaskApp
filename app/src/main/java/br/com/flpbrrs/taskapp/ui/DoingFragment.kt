package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.flpbrrs.taskapp.components.GenericAdapter
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskDiffCallback
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.ComponentTaskItemBinding
import br.com.flpbrrs.taskapp.databinding.FragmentDoingBinding

class DoingFragment : GenericFragment<FragmentDoingBinding>(FragmentDoingBinding::inflate) {
    private lateinit var taskAdapter: GenericAdapter<Task>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        taskAdapter = GenericAdapter(
            inflater = ComponentTaskItemBinding::inflate,
            diffCallback = TaskDiffCallback
        ) { task, binding ->
            with(binding as ComponentTaskItemBinding) {
                cardTitle.text = task.title
                cardDescription.text = task.description

                cardOptions.setOnClickListener {
                    Toast.makeText(requireContext(), "TESTE OPTIONS ${task.title}", Toast.LENGTH_SHORT).show()
                }

                cardDescription.setOnClickListener {
                    Toast.makeText(requireContext(), "TESTE DETAILS ${task.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }

        taskAdapter.submitList(getTasks())
    }

    private fun getTasks() = listOf(
        Task(
            id = "1",
            description = "Estudar Kotlin para o desenvolvimento Android",
            title = "Estudar Kotlin",
            TaskStatus.DOING
        ),
        Task(
            id = "2",
            description = "Participar de treinamentos para ajudar na operação",
            title = "Participação de treinamentos mobile",
            TaskStatus.DOING
        )
    )
}