package br.com.flpbrrs.taskapp.utils

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import br.com.flpbrrs.taskapp.R
import br.com.flpbrrs.taskapp.components.BottomSheetContainerFragment
import br.com.flpbrrs.taskapp.databinding.ComponentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
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

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    buttonLabel: Int? = null,
    message: String,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext())
    val binding: ComponentBottomSheetBinding = ComponentBottomSheetBinding.inflate(
        layoutInflater, null, false
    )

    binding.title.text = getText(titleDialog ?: R.string.bottom_sheet_component_title)
    binding.okButton.text = getText(buttonLabel ?: R.string.bottom_sheet_component_confirm_button)
    binding.message.text = message
    binding.okButton.setOnClickListener{
        onClick()
        bottomSheetDialog.dismiss()
    }
    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()
}

fun Fragment.openPopupFor(
    anchor: View,
    menu: Int,
    menuOptions: (item: MenuItem) -> Unit
) {
    PopupMenu(requireContext(), anchor).apply {
        menuInflater.inflate(menu, this.menu)
        setOnMenuItemClickListener {
            menuOptions(it)
            true
        }
        show()
    }
}