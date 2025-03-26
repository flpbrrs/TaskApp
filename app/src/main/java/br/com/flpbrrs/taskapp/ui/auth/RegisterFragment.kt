package br.com.flpbrrs.taskapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.databinding.FragmentRegisterBinding
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.utils.initToolbar
import br.com.flpbrrs.taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment :
    GenericFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initToolbar(binding.fragmentToolbar)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        registerButton.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val username = binding.usernameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || confirmPassword.isEmpty())
            return showBottomSheet(message = getString(R.string.dados_registros_invalidos))

        if (confirmPassword != password)
            return showBottomSheet(message = getString(R.string.senha_diferentes))

        registerUser(email, password)
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}