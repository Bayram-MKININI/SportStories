package fr.eurosport.sportstories.feature_media.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.SELECTED_MEDIA
import fr.eurosport.sportstories.common.util.ErrorType.NETWORK_ERROR
import fr.eurosport.sportstories.common.util.Resource.Error
import fr.eurosport.sportstories.common.util.Resource.Loading
import fr.eurosport.sportstories.common.util.Resource.Success
import fr.eurosport.sportstories.common.util.UIEvent
import fr.eurosport.sportstories.common.util.ViewState
import fr.eurosport.sportstories.common.util.ViewState.DataState
import fr.eurosport.sportstories.common.util.ViewState.LoadingState
import fr.eurosport.sportstories.feature_media.domain.use_cases.GetMedia
import fr.eurosport.sportstories.feature_media.presentation.mapper.MediaMapper
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMedia: GetMedia,
    private val mediaMapper: MediaMapper
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<ViewState<List<MediaElementUI>>>(LoadingState())
    val stateFlow = _stateFlow.asStateFlow()

    val selectedMedia = savedStateHandle.getStateFlow<MediaElementUI?>(
        key = SELECTED_MEDIA,
        initialValue = null
    )

    fun setSelectedMedia(mediaElementUI: MediaElementUI) {
        savedStateHandle[SELECTED_MEDIA] = mediaElementUI
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        callGetMediasWebservice()
    }

    private fun callGetMediasWebservice() {
        viewModelScope.launch {
            getMedia().onEach { result ->

                when (result) {
                    is Loading -> _stateFlow.value = if (result.data?.isNotEmpty() == true) {
                        DataState(result.data.map {
                            mediaMapper.map(it)
                        })
                    } else {
                        LoadingState()
                    }

                    is Success -> _stateFlow.value = DataState(result.data?.map {
                        mediaMapper.map(it)
                    })

                    is Error -> {
                        when (result.dataError) {
                            NETWORK_ERROR -> {
                                _eventFlow.emit(
                                    UIEvent.ShowError(
                                        errorType = NETWORK_ERROR,
                                        errorStrRes = R.string.error_no_network
                                    )
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}