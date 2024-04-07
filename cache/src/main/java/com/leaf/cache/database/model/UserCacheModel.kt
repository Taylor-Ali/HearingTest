package com.leaf.cache.database.model

/**
 * UserCacheModel
 * Simple user details object used to store information about the test taker.
 * @param firstName [String] first name of user.
 * @param lastName [String] represents last name of user.
 * @param emailAddress [String] represents email address of user.
 * @param dateOfBirth [String] represents date of birth of user
 */
data class UserCacheModel(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val dateOfBirth: String,
)
