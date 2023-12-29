package com.tensor.example.ui.home.friendsList

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tensor.example.R
import com.tensor.example.data.fireatstore.use_case.UseCases
import com.tensor.example.data.repository.UserRepository
import com.tensor.example.di.IoDispatcher
import com.tensor.example.di.MainDispatcher
import com.tensor.example.ui.base.BaseViewModel
import com.tensor.example.utils.ResourceHelper
import com.tensor.example.utils.extension.onError
import com.tensor.example.utils.extension.onException
import com.tensor.example.utils.extension.onSuccess
import com.tensor.example.utils.result.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val useCases: UseCases,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher
) :
    BaseViewModel() {
    private val _showShimmer = MutableLiveData<Boolean>()
    val showShimmer: LiveData<Boolean>
        get() = _showShimmer

    var msg = MutableLiveData<String>()


    data class FormMessage(
        var msg: String = "",
        var isMsgError: Boolean = false,
        @StringRes val formIsValid: Int = 0
    )

    val formValidationLiveData = MutableLiveData<FormMessage>()

    fun loadMoreChat() {
        useCases.getUsers.invoke()
    }

}
