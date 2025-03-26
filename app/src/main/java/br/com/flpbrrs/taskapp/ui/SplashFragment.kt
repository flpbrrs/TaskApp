package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : GenericFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            checkAuth()
        }
    }

    private fun checkAuth() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}