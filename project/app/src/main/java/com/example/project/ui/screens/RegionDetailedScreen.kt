package com.example.project.ui.screens

import RegionViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.currentsession.CurrentSessionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RegionDetailedScreen(
    regionViewModel: RegionViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val regionUiState by regionViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(regionUiState.gatheringData) {
            LaunchedEffect(key1 = null) {
                withContext(Dispatchers.IO) {
                    regionViewModel.gatherData(CurrentSessionData.selectedDetailedId)
                }
            }
        } else {
            TopologySection(
                topologies = regionViewModel.topologies.toList()
            )
        }
    }
}

@Composable
fun TopologySection(
    topologies: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    )
    {
        for(topology in topologies) {
            Icon(
                painter = painterResource(id = getTopologyId(topology)),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

fun getTopologyId(topologyName: String) : Int {
    return when(topologyName){
        "arete" -> R.drawable.ic_design_topology_arete
        "corner" -> R.drawable.ic_design_topology_corner
        "overhang" -> R.drawable.ic_design_topology_overhang
        "roof" -> R.drawable.ic_design_topology_roof
        "slab" -> R.drawable.ic_design_topology_slab
        "vertical" -> R.drawable.ic_design_topology_vertical
        else -> 0
    }
}