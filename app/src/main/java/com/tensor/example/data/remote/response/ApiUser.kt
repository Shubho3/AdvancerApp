/*
* Copyright 2023 TensorIotExample
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.tensor.example.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * User API response.
 */
data class ApiUser(
    @SerializedName("name")
    val name: Name = Name(),

    @SerializedName("location")
    val location: Location = Location(),

    @SerializedName("picture")
    val picture: Picture = Picture(),

    @SerializedName("login")
    val login: Login = Login()
) {
    data class Name(
        @SerializedName("title")
        val title: String = "",

        @SerializedName("first")
        val first: String = "",

        @SerializedName("last")
        val last: String = ""
    ) {
        override fun toString(): String = "$title $first $last"
    }

    data class Location(
        @SerializedName("street")
        val street: Street = Street(),

        @SerializedName("city")
        val city: String = "",

        @SerializedName("state")
        val state: String = "",

        @SerializedName("country")
        val country: String = "",

        @SerializedName("postcode")
        val postcode: String = "",

        @SerializedName("coordinates")
        val coordinates: Coordinates = Coordinates(),

        @SerializedName("timezone")
        val timezone: Timezone = Timezone()
    ) {
        fun address(): String {
            return "$street, $city, $postcode, $state, $country"
        }
    }

    data class Coordinates(
        @SerializedName("latitude")
        val latitude: String = "",

        @SerializedName("longitude")
        val longitude: String = ""
    )

    data class Timezone(
        @SerializedName("offset")
        val offset: String = "",

        @SerializedName("description")
        val description: String = ""
    )

    data class Street(
        @SerializedName("number")
        val number: Int = 0,

        @SerializedName("name")
        val name: String = ""
    ) {
        override fun toString(): String = "$number, $name"
    }

    data class Picture(
        @SerializedName("large")
        val large: String = "",

        @SerializedName("medium")
        val medium: String = "",

        @SerializedName("thumbnail")
        val thumbnail: String = ""
    )

    data class Login(
        @SerializedName("uuid")
        val uuid: String = ""
    )
}
