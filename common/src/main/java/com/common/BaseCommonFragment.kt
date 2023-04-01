package com.common

import androidx.fragment.app.Fragment


abstract class BaseCommonFragment : Fragment() {

    fun replaceFragment(id:Int,fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(id, fragment)
            .commit()
    }
}