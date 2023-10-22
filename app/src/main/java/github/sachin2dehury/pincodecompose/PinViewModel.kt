package github.sachin2dehury.pincodecompose

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(private val pinRepository: PinRepository) : ViewModel() {

    private val viewModelIOScope = viewModelScope + Dispatchers.IO

    private val _pinData = MutableStateFlow<ResultState>(ResultState.Empty)
    val pinData = _pinData.asStateFlow()

    fun getPinData(pin: String) = viewModelIOScope.launch {
        _pinData.value = ResultState.Loading
        if (pin.length < 6 || !pin.isDigitsOnly()) {
            _pinData.value = ResultState.Error("Enter a valid Pincode!")
            return@launch
        }
        val response = pinRepository.getPinData(pin)
        _pinData.value = if (response.isSuccessful && response.code() == 200) {
            ResultState.Success(response.body())
        } else {
            ResultState.Error()
        }
    }
}

sealed interface ResultState {
    data class Success(val data: PinResponse?) : ResultState
    data class Error(val data: String? = null) : ResultState
    data object Loading : ResultState
    data object Empty : ResultState
}
