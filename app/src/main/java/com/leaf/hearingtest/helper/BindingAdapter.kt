package com.leaf.hearingtest.helper

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber

@BindingAdapter("emailInputCheck")
fun emailInputCheck(textInputLayout: TextInputLayout, email: String?) {
    Timber.i("BindingAdapter:${email}")
    if (email == null) {
        return
    }
    if (!email.validateEmail()) {
        textInputLayout.error = "Email format is not correct."
        return
    }
    textInputLayout.error = ""
}


@BindingAdapter("dateInputCheck")
fun dateInputCheck(textInputLayout: TextInputLayout, date: String?) {
    Timber.i("BindingAdapter:${date}")
    if (date == null) {
        return
    }
    if (!date.validateDate()) {
        textInputLayout.error = "Date entered is invalid."
        return
    }
    textInputLayout.error = ""
}


private fun String.validateDate(): Boolean {
    return this.matches(Regex("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$"))
}

private fun String.validateEmail(): Boolean {
    return this.matches(Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
}