package com.example.sampleassignment.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: Address?,
    val company: Company?
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo?
) {
    val fullAddress: String
        get() = listOfNotNull(
            street.takeIf { it.isNotBlank() },
            suite.takeIf { it.isNotBlank() },
            city.takeIf { it.isNotBlank() },
            zipcode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

data class Geo(
    val lat: String,
    val lng: String
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
