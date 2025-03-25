package br.com.flpbrrs.taskapp.ui

import android.os.Bundle
import android.view.View
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.adapters.ViewPagerAdapter
import br.com.flpbrrs.taskapp.components.GenericFragment
import br.com.flpbrrs.taskapp.databinding.FragmentHomeBinding
import br.com.flpbrrs.taskapp.utils.showBottomSheetContainerFor
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : GenericFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        initListeners()
    }

    private fun initListeners() = with(binding) {
        fabAddTask.setOnClickListener {
            showBottomSheetContainerFor(FormTaskFragment())
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