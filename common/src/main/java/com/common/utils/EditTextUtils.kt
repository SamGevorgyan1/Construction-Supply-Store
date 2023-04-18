package com.common.utils

import androidx.appcompat.widget.AppCompatEditText

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