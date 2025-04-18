package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.databinding.FragmentSplashBinding
import br.com.flpbrrs.taskapp.utils.FirebaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : GenericFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(
                if (FirebaseHelper.isAuthenticated()) {
                    R.id.action_splashFragment_to_homeFragment
                } else {
                    R.id.action_splashFragment_to_loginFragment
                }
            )
        }
    }
}