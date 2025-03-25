package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentLoginBinding
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.utils.showBottomSheet

class LoginFragment : GenericFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        loginButton.setOnClickListener {
            val modelIsValid = validateData()

            if (modelIsValid)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateData(): Boolean = with(binding) {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        return if (email.isNotEmpty() && password.isNotEmpty()) true
        else {
            showBottomSheet(message = getString(R.string.credenciais_login_invalidas))
            return false
        }
    }

}