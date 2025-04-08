package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericAdapter
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskDiffCallback
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.ComponentTaskItemBinding
import br.com.flpbrrs.taskapp.databinding.FragmentTodoBinding
import br.com.flpbrrs.taskapp.utils.openPopupFor
import br.com.flpbrrs.taskapp.utils.showBottomSheet
import br.com.flpbrrs.taskapp.utils.showBottomSheetContainerFor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TodoFragment : GenericFragment<FragmentTodoBinding>(FragmentTodoBinding::inflate) {
    private lateinit var taskAdapter: GenericAdapter<Task>

    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseRef = Firebase.database.reference
        auth = Firebase.auth

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
                    openPopupFor(it, R.menu.task_item_menu) { item ->
                        when (item.itemId) {
                            R.id.edit -> editTask(task)
                            R.id.delete -> showBottomSheet(
                                onClick = { deleteTask(task) },
                                buttonLabel = R.string.btn_confirm,
                                message = getString(R.string.delete_task_message)
                            )
                        }
                    }
                }

            }
        }

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }

        getTasks()
    }

    private fun getTasks() {
        auth.currentUser?.let {
            databaseRef
                .child("tasks")
                .child(it.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val taskList = mutableListOf<Task>()
                        snapshot.children.forEach { ds ->
                            val task = ds.getValue(Task::class.java) as Task
                            if (task.status === TaskStatus.TODO)
                                taskList.add(task)
                        }
                        updateIndicatorBy(taskList)
                        taskList.reverse()
                        taskAdapter.submitList(taskList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            R.string.generic_message_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    private fun moveTask(task: Task, destination: String) {

    }

    private fun editTask(task: Task) {
        showBottomSheetContainerFor(FormTaskFragment.newInstance(task))
    }

    private fun deleteTask(task: Task) {
        auth.currentUser?.let {
            databaseRef
                .child("tasks")
                .child(it.uid)
                .child(task.id)
                .removeValue()
                .addOnCompleteListener { result ->
                    if (!result.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            R.string.generic_message_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun updateIndicatorBy(list: List<Task>) {
        binding.textInfo.text = if (list.isEmpty()) {
            getString(R.string.list_empty)
        } else {
            ""
        }
        binding.progressBar.isVisible = false
    }
}