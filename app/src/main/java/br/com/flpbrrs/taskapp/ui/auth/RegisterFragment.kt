package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.databinding.FragmentRegisterBinding
import br.com.flpbrrs.taskapp.ui.GenericFragment
import br.com.flpbrrs.taskapp.utils.initToolbar

class RegisterFragment : GenericFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.fragmentToolbar)
    }
}