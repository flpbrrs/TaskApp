package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.data.model.Task
import br.com.flpbrrs.taskapp.data.model.TaskStatus
import br.com.flpbrrs.taskapp.databinding.FragmentFormTaskBinding
import br.com.flpbrrs.taskapp.utils.dismissBottomSheet
import br.com.flpbrrs.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FormTaskFragment :
    GenericFragment<FragmentFormTaskBinding>(FragmentFormTaskBinding::inflate) {

    private lateinit var task: Task
    private lateinit var taskStatus: TaskStatus
    private var isANewTask: Boolean = true

    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = Firebase.database.reference
        auth = Firebase.auth
        initListeners()
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
            if (isANewTask) task = createTask(title, description, taskStatus)
            saveTask(task)
        } else {
            showBottomSheet(message = getString(R.string.form_task_invalid_data))
        }
    }

    private fun saveTask(task: Task) {
        auth.currentUser?.let {
            databaseRef
                .child("tasks")
                .child(it.uid)
                .child(task.id)
                .setValue(task).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        dismissBottomSheet()
                    } else {
                        showBottomSheet(message = getString(R.string.generic_message_error))
                    }
                }
        }
    }

    private fun createTask(title: String, description: String, status: TaskStatus) = Task().apply {
        this.id = databaseRef.database.reference.push().key!!
        this.title = title
        this.description = description
        this.status = status
    }
}
