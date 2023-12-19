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
package com.tensor.example.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tensor.example.data.fireatstore.model.Response
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.data.fireatstore.repository.UserResponse
import com.tensor.example.data.fireatstore.use_case.UseCases
import com.tensor.example.ui.base.BaseViewModel
import com.tensor.example.utils.ResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val resourceHelper: ResourceHelper,
                                           private val useCases: UseCases

) : BaseViewModel() {

    var getuserRes = MutableLiveData<UserResponse>()

    fun getSingleUser(user : String) = viewModelScope.launch {
        Timber.tag(":").e("getUser: %s", user)
        Timber.tag(":").e("getuserRes: %s",   getuserRes.value)

        getuserRes.value = useCases.getSingleUser(user)

    }


}
