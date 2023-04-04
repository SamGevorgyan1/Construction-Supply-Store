package com.common.basecommon

import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment


abstract class BaseCommonFragment : Fragment() {

    fun replaceFragment(id:Int,fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(id, fragment)
            .commit()
    }

    fun checkEdtText(editTexts: Array<AppCompatEditText>):Boolean{
        var allEditTextsFilled = true

        editTexts.forEach { editText ->
            if (editText.text.isNullOrEmpty()) {
                allEditTextsFilled = false
                return@forEach
            }
        }
        return allEditTextsFilled

    }
}