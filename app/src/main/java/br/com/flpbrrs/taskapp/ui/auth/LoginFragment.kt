package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentLoginBinding
import br.com.flpbrrs.taskapp.ui.GenericFragment
import br.com.flpbrrs.taskapp.utils.showBottomSheet

class LoginFragment : GenericFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            val modelIsValid = validateData()

            if(modelIsValid)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateData():Boolean {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()) {
            showBottomSheet(message = getString(R.string.credenciais_login_invalidas))
            return false
        }
        return true
    }
}