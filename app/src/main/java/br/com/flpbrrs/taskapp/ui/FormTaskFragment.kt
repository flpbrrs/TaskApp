package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentFormTaskBinding
import br.com.flpbrrs.taskapp.utils.dismissBottomSheet

class FormTaskFragment : GenericFragment<FragmentFormTaskBinding>(FragmentFormTaskBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnCancel.setOnClickListener {
            dismissBottomSheet()
        }
    }
}
