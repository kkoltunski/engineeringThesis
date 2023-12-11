package com.example.project.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.project.R

@Composable
fun TopologySection(
    topology: List<String>,
    modifier: Modifier = Modifier
) {
    ExpandableSection(
        modifier = Modifier,
        title = stringResource(id = R.string.topology),
        isExpandedByDefault = true
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        )
        {
            for(topology in topology) {
                Icon(
                    painter = painterResource(id = getTopologyId(topology)),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
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