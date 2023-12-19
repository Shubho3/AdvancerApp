package com.tensor.example.ui.chat

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tensor.example.R
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
class ChatViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher
) :
    BaseViewModel() {
    private val _showShimmer = MutableLiveData<Boolean>()
    val showShimmer: LiveData<Boolean>
        get() = _showShimmer

    var msg = MutableLiveData<String>()

    fun isFormValid(): Boolean {
        when {
            msg.value?.trim().isNullOrEmpty() -> formValidationLiveData.value =
                FormMessage(
                    msg = resourceHelper.getString(R.string.error_msg),
                    isMsgError = true
                )

            else -> {
                formValidationLiveData.postValue(FormMessage(formIsValid = R.string.form_is_valid))
                return true
            }
        }
        return false
    }

    data class FormMessage(
        var msg: String = "",
        var isMsgError: Boolean = false,
        @StringRes val formIsValid: Int = 0
    )

    val formValidationLiveData = MutableLiveData<FormMessage>()

    fun loadMoreChat() {
        viewModelScope.launch(ioDispatcher) {

        }
    }

}
