package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentLoginBinding
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.utils.FirebaseHelper
import br.com.flpbrrs.taskapp.utils.showBottomSheet

class LoginFragment : GenericFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        loginButton.setOnClickListener {
            validateData()
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateData() = with(binding) {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginUser(email, password)
        } else showBottomSheet(message = getString(R.string.credenciais_login_invalidas))
    }

    private fun loginUser(email: String, password: String) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    showBottomSheet(
                        message = getString(FirebaseHelper.validError(
                            task.exception?.message.toString())
                        )
                    )
                }
            }
    }

}