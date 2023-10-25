package fr.eurosport.sportstories.feature_media.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.eurosport.sportstories.common.util.DataError
import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement
import fr.eurosport.sportstories.feature_media.domain.use_cases.GetMedia
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val getMedia: GetMedia
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(MediaVMState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getMedia().onEach { result ->

                var mediaElementsList: List<MediaElement> = listOf()

                result.data?.let { mediaList ->
                    mediaElementsList = mediaList
                }

                when (result) {
                    is Resource.Success -> {
                        _stateFlow.value = MediaVMState(mediaElements = mediaElementsList)

                    }
                    is Resource.Loading -> {
                        _stateFlow.value = MediaVMState(mediaElements = mediaElementsList)
                    }
                    is Resource.Error -> {
                        _stateFlow.value = MediaVMState(mediaElements = mediaElementsList)
                        _eventFlow.emit(UIEvent.ShowSnackBar(result.dataError))
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val dataError: DataError) : UIEvent()
    }
}