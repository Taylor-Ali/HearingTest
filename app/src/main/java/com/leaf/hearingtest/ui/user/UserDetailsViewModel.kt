package com.leaf.hearingtest.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class UserDetailsViewModel : ViewModel() {
    /**
     * firstName
     * creates a [MutableLiveData] [String] data captured by input filed binding.
     */
    var firstName = MutableLiveData<String>()
    /**
     * lastname
     * [MutableLiveData] [String] data captured by input filed binding.
     */
    var lastname = MutableLiveData<String>()

    /**
     * emailAddress
     * [MutableLiveData] [String] data captured by input filed binding.
     */
    var emailAddress = MutableLiveData<String>()

    /**
     * dateOfBirth
     * [MutableLiveData] [String] data captured by input filed binding.
     */
    var dateOfBirth = MutableLiveData<String>()

    /**
     * getUserDetails
     * creates a [UserModel] from form data capture by the input fields.
     * @return [UserModel]
     */
    fun getUserDetails(): UserModel {
        return UserModel(
            firstName = firstName.value.orEmpty(),
            lastName = lastname.value.orEmpty(),
            emailAddress = emailAddress.value.orEmpty(),
            dateOfBirth = dateOfBirth.value.orEmpty()
        )
    }
}