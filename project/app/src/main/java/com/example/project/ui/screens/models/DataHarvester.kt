package com.example.project.ui.screens.models

import androidx.lifecycle.ViewModel
import com.example.project.ui.states.GatheringDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class DataHarvester : ViewModel() {
    private val _harvesterUiState = MutableStateFlow(GatheringDataState())
    val harvesterUiState: StateFlow<GatheringDataState> = _harvesterUiState.asStateFlow()

    lateinit var phrase: String

    protected abstract fun getDataFromDataBase()

    suspend fun gatherData(input: String) {
        phrase = input
        setGatheringUiState(gatheringData = true)
        getDataFromDataBase()
        setGatheringUiState(gatheringData = false)
    }

    private fun setGatheringUiState(gatheringData: Boolean) {
        _harvesterUiState.update { currentState ->
            currentState.copy(gatheringData = gatheringData)
        }
    }

    init {
        setGatheringUiState(true)
    }
}