package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.databinding.FragmentFormTaskBinding
import br.com.flpbrrs.taskapp.utils.dismissBottomSheet
import br.com.flpbrrs.taskapp.utils.showBottomSheet

class FormTaskFragment : GenericFragment<FragmentFormTaskBinding>(FragmentFormTaskBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnSave.setOnClickListener { validateData() }
        btnCancel.setOnClickListener { dismissBottomSheet() }
    }

    private fun validateData(): Boolean = with(binding) {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()

        return if(title.isNotEmpty() && description.isNotEmpty()) true
        else {
            showBottomSheet(message = getString(R.string.form_task_invalid_data))
            false
        }
    }
}
