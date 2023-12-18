package com.example.project.ui.screens.common.table

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import com.example.project.data.CommentData
import com.example.project.data.GradeData
import com.example.project.data.RegionData
import com.example.project.data.RockData
import com.example.project.data.RouteData
import com.example.project.data.currentsession.Ascent

@Composable
fun AscentItem(
    ascent: Ascent,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = ascent.route.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 3.dp)
            ) {
                Text(
                    text = ascent.route.grade,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.33f)
                )
                Text(
                    text = ascent.date,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.33f)
                )
                Text(
                    text = ascent.style,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.33f)
                )
            }
        }
    }
}

@Composable
fun RegionItem(
    region: RegionData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable{
                onClick()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = region.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun RockItem(
    rock: RockData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable{
                onClick()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = rock.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            if(rock.regionName.isNotNull()) {
                Text(
                    text = rock.regionName!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun RouteItem(
    route: RouteData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable{
                onClick()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = route.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 3.dp)
            ) {
                if(route.rockName.isNotNull()){
                    Text(
                        text = route.rockName!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.5f)
                    )
                }
                if(route.regionName.isNotNull()){
                    Text(
                        text = route.regionName!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.5f)
                    )
                }
                if(route.grade.isNotNull()) {
                    Text(
                        text = route.grade!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.5f)
                    )
                }
                if(route.boltsNumber.isNotNull()) {
                    Text(
                        text = "bolts: " + if(route.boltsNumber == 0) "-" else route.boltsNumber.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun GradeItem(
    grade: GradeData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${grade.name}: ",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = grade.value.toString())
        }
    }
}

@Composable
fun CommentItem(
    comment: CommentData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = comment.date,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.3f)
            )
            Text(
                text = comment.value,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.7f)
            )
        }
    }
}