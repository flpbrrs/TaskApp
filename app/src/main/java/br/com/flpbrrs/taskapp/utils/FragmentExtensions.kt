package br.com.flpbrrs.taskapp.utils

import androidx.fragment.app.Fragment
import br.com.flpbrrs.taskapp.components.BottomSheetContainerFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun Fragment.showBottomSheetContainerFor(fragment: Fragment) {
    BottomSheetContainerFragment(fragment)
        .show(childFragmentManager, "FormBottomSheetFragment")
}

fun Fragment.dismissBottomSheet() {
    (parentFragment as? BottomSheetDialogFragment)?.dismiss()
}