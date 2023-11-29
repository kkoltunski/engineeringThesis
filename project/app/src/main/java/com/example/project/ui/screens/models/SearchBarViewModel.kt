package com.example.project.ui.screens.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.project.ui.screens.common.searchbar.SearchBarState

class SearchBarViewModel : ViewModel() {
    private val _searchBarState: MutableState<SearchBarState> =
        mutableStateOf(SearchBarState.CLOSED)
    val searchBarState: State<SearchBarState> = _searchBarState

    private val _searchTextState: MutableState<String> =
        mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchBarState(newState: SearchBarState) {
        _searchBarState.value = newState
    }

    fun updateSearchBarText(newText: String) {
        _searchTextState.value = newText
    }
}