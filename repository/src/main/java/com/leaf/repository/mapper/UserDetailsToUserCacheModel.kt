package com.leaf.repository.mapper

import com.leaf.cache.database.model.UserCacheModel
import com.leaf.repository.model.UserDetails

/**
 * UserDetailsToUserCacheModel
 * Mapping class that maps [UserDetails] to the [UserCacheModel] Database model.
 */
class UserDetailsToUserCacheModel:Mapper<UserCacheModel,UserDetails> {
    override fun mapFromModel(type: UserDetails): UserCacheModel {
        return UserCacheModel(
            firstName = type.firstName,
            lastName = type.lastName,
            emailAddress = type.emailAddress,
            dateOfBirth = type.dateOfBirth,
        )
    }

    override fun mapToModel(type: UserCacheModel): UserDetails {
        return UserDetails(
            firstName = type.firstName,
            lastName = type.lastName,
            emailAddress = type.emailAddress,
            dateOfBirth = type.dateOfBirth
        )
    }
}