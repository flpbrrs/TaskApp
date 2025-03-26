package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentLoginBinding
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : GenericFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}