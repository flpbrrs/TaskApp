package br.com.flpbrrs.taskapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.FragmentFormTaskBinding
import br.com.flpbrrs.taskapp.utils.FirebaseHelper
import br.com.flpbrrs.taskapp.utils.dismissBottomSheet
import br.com.flpbrrs.taskapp.utils.showBottomSheet

class FormTaskFragment :
    GenericFragment<FragmentFormTaskBinding>(FragmentFormTaskBinding::inflate) {

    private lateinit var task: Task
    private var taskStatus: TaskStatus = TaskStatus.TODO
    private var isANewTask: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_TASK, Task::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_TASK)
        }

        if (receivedTask != null) {
            task = receivedTask
            isANewTask = false
            taskStatus = task.status
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        if (!isANewTask) {
            binding.titleInput.setText(task.title)
            binding.descriptionInput.setText(task.description)
            binding.title.setText(R.string.form_task_edit_title)

            when (task.status) {
                TaskStatus.TODO -> binding.rbTodo.isChecked = true
                TaskStatus.DOING -> binding.rbDoing.isChecked = true
                TaskStatus.DONE -> binding.rbDone.isChecked = true
            }
        }
    }

    private fun initListeners() = with(binding) {
        radioGroup.setOnCheckedChangeListener { _, id ->
            taskStatus = when (id) {
                R.id.rbTodo -> TaskStatus.TODO
                R.id.rbDoing -> TaskStatus.DOING
                else -> TaskStatus.DONE
            }
        }
        btnSave.setOnClickListener { validateData() }
        btnCancel.setOnClickListener { dismissBottomSheet() }
    }

    private fun validateData() = with(binding) {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val updatedTask = if (isANewTask) {
                createTask(title, description, taskStatus)
            } else {
                updateTask(title, description, taskStatus)
            }
            saveTask(updatedTask)
        } else {
            showBottomSheet(message = getString(R.string.form_task_invalid_data))
        }
    }

    private fun saveTask(novaTask: Task) {
        FirebaseHelper.getAuth().currentUser?.let {
            FirebaseHelper.getDatabase()
                .child("tasks")
                .child(it.uid)
                .child(novaTask.id)
                .setValue(novaTask).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        dismissBottomSheet()
                    } else {
                        showBottomSheet(message = getString(R.string.generic_message_error))
                    }
                }
        }
    }

    private fun updateTask(title: String, description: String, status: TaskStatus): Task {
        return task.copy(
            title = title,
            description = description,
            status = status
        )
    }

    private fun createTask(title: String, description: String, status: TaskStatus) = Task().apply {
        this.title = title
        this.description = description
        this.status = status
    }

    companion object {
        private const val ARG_TASK = "arg_task"

        fun newInstance(task: Task?): FormTaskFragment {
            val fragment = FormTaskFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_TASK, task)
            fragment.arguments = bundle
            return fragment
        }
    }
}
