package br.com.flpbrrs.taskapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import br.com.flpbrrs.taskapp.components.BottomSheetContainerFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun Fragment.initToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
}

fun Fragment.showBottomSheetContainerFor(fragment: Fragment) {
    BottomSheetContainerFragment(fragment)
        .show(childFragmentManager, "FormBottomSheetFragment")
}

fun Fragment.dismissBottomSheet() {
    (parentFragment as? BottomSheetDialogFragment)?.dismiss()
}