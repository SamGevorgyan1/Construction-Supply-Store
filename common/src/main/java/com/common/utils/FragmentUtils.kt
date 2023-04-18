package com.common.utils


import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(id:Int, fragment: Fragment){
    parentFragmentManager.beginTransaction()
        .replace(id, fragment)
        .commit()
}
