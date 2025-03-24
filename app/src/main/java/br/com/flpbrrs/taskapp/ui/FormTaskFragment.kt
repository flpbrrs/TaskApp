package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentFormTaskBinding
import br.com.flpbrrs.taskapp.utils.dismissBottomSheet

class FormTaskFragment : GenericFragment<FragmentFormTaskBinding>(FragmentFormTaskBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            validateData()
        }

        binding.btnCancel.setOnClickListener {
            dismissBottomSheet()
        }
    }

    private fun validateData(): Boolean {
        val title = binding.titleInput.text.toString().trim()
        val description = binding.descriptionInput.toString().trim()

        if(title.isEmpty() || description.isEmpty()) {
            Toast.makeText(
                requireContext(),
                R.string.form_task_invalid_data,
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }
}
