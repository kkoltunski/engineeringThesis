package com.example.project.ui.screens.models

import androidx.lifecycle.ViewModel
import com.example.project.ui.states.GatheringDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class DataHarvester : ViewModel() {
    private val _uiState = MutableStateFlow(GatheringDataState())
    val uiState: StateFlow<GatheringDataState> = _uiState.asStateFlow()

    lateinit var phrase: String

    protected abstract fun getDataFromDataBase()

    suspend fun gatherData(input: String) {
        phrase = input
        setSearchUiState(gatheringData = true)
        getDataFromDataBase()
        setSearchUiState(gatheringData = false)
    }

    private fun setSearchUiState(gatheringData: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(gatheringData = gatheringData)
        }
    }

    init {
        setSearchUiState(true)
    }
}