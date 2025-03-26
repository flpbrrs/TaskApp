package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.adapters.ViewPagerAdapter
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.databinding.FragmentHomeBinding
import br.com.flpbrrs.taskapp.utils.showBottomSheet
import br.com.flpbrrs.taskapp.utils.showBottomSheetContainerFor
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : GenericFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initTabs()
        initListeners()
    }

    private fun initListeners() = with(binding) {
        fabAddTask.setOnClickListener {
            showBottomSheetContainerFor(FormTaskFragment())
        }

        btnLogout.setOnClickListener {
            showBottomSheet(
                buttonLabel = R.string.btn_confirm,
                message = getString(R.string.logout_message),
                onClick = {
                    auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            )
        }
    }

    private fun initTabs() {
        val pagerAdapter = ViewPagerAdapter(requireActivity()).apply {
            addFragment(TodoFragment(), R.string.tab_title_todo)
            addFragment(DoingFragment(), R.string.tab_title_doing)
            addFragment(DoneFragment(), R.string.tab_title_done)
        }

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = pagerAdapter.itemCount
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(pagerAdapter.getTitle(position))
        }.attach()
    }
}