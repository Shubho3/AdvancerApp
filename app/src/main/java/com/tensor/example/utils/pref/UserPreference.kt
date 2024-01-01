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
package com.tensor.example.utils.pref

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.google.gson.Gson
import com.tensor.example.data.fireatstore.model.User
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Shared preference delegated property for string values. Use this class to store and retrive
 * string value from shared preference just like a normal variable.
 * Usage:
 * ```
 * // Lazy variable for shared preference
 * val lazySharedPreferences = lazy { getSharedPreferences(...) }
 * // Delegated property
 * var userId by StringPreference(lazySharedPreferences, key, defaultValue)
 *
 * // Set value
 * userId = "user101"
 * // Get value
 * if (userId == "user101")
 *     showUserGrantedDialog()
 * else
 *     showUserDeniedDialog()
 * ```
 */
class UserPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<User, String?> {

    @WorkerThread
    override fun getValue(thisRef: User, property: KProperty<*>): String? {
        val gson = Gson()
        return preferences.value.getString("USER", "")
    }

    override fun setValue(thisRef: User, property: KProperty<*>, value: String?) {
        val gson = Gson()
        val json = gson.toJson(value)
        preferences.value.edit { putString("USER", json) }
    }
}
