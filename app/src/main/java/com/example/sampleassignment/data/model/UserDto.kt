package com.example.sampleassignment.data.model

import com.example.sampleassignment.domain.model.Address
import com.example.sampleassignment.domain.model.Company
import com.example.sampleassignment.domain.model.Geo
import com.example.sampleassignment.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("company") val company: CompanyDto?
)

data class AddressDto(
    @SerializedName("street") val street: String?,
    @SerializedName("suite") val suite: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("zipcode") val zipcode: String?,
    @SerializedName("geo") val geo: GeoDto?
)

data class GeoDto(
    @SerializedName("lat") val lat: String?,
    @SerializedName("lng") val lng: String?
)

data class CompanyDto(
    @SerializedName("name") val name: String?,
    @SerializedName("catchPhrase") val catchPhrase: String?,
    @SerializedName("bs") val bs: String?
)

fun UserDto.toDomain(): User {
    return User(
        id = id ?: 0,
        name = name.orEmpty(),
        username = username.orEmpty(),
        email = email.orEmpty(),
        phone = phone.orEmpty(),
        website = website.orEmpty(),
        address = address?.toDomain(),
        company = company?.toDomain()
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        street = street.orEmpty(),
        suite = suite.orEmpty(),
        city = city.orEmpty(),
        zipcode = zipcode.orEmpty(),
        geo = geo?.toDomain()
    )
}

fun GeoDto.toDomain(): Geo {
    return Geo(
        lat = lat.orEmpty(),
        lng = lng.orEmpty()
    )
}

fun CompanyDto.toDomain(): Company {
    return Company(
        name = name.orEmpty(),
        catchPhrase = catchPhrase.orEmpty(),
        bs = bs.orEmpty()
    )
}
