package me.darthwithap.todoapp

import androidx.fragment.app.DialogFragment

class AddCategoryFragment: DialogFragment() {

}

interface CategoryDialogFragment {
    fun onDialogPositiveClick(dialogFragment: DialogFragment)
    fun onDialogNegativeClick(dialogFragment: DialogFragment)
}