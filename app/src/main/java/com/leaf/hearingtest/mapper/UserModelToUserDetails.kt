package com.leaf.hearingtest.mapper

import com.leaf.hearingtest.ui.user.UserModel
import com.leaf.repository.model.UserDetails

class UserModelToUserDetails : Mapper<UserModel, UserDetails> {
    override fun mapFromModel(type: UserDetails): UserModel {
        return UserModel(
            firstName = type.firstName,
            lastName = type.lastName,
            emailAddress = type.emailAddress,
            dateOfBirth = type.dateOfBirth
        )
    }

    override fun mapToModel(type: UserModel): UserDetails {
        return UserDetails(
            firstName = type.firstName,
            lastName = type.lastName,
            emailAddress = type.emailAddress,
            dateOfBirth = type.dateOfBirth
        )
    }
}