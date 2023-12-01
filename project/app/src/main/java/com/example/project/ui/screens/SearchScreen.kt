package com.example.project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.project.ui.screens.models.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = searchViewModel.searchedPhrase,
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}