package com.example.project.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.data.RegionData
import com.example.project.data.RockData
import com.example.project.data.RouteData
import com.example.project.data.currentsession.Ascent
import com.example.project.ui.theme.ProjectTheme

@Composable
fun <T> Table(
    list: List<T>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
    ) {
        for(item in list) {
            when(item) {
                is Ascent -> {
                    AscentItem(
                        ascent = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is RegionData -> {
                    RegionItem(
                        region = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is RockData -> {
                    RockItem(
                        rock = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is RouteData -> {
                    RouteItem(
                        route = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

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
                text = rock.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Text(
                text = rock.regionName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun RouteItem(
    route: RouteData,
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
                Text(
                    text = route.rockName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.5f)
                )
                Text(
                    text = route.regionName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TablePreview() {
    ProjectTheme {
        val ascent1 = Ascent()
        ascent1.route.name = "Rysa prawdziwych mężczyzn (i jednej dziewczyny)"
        ascent1.route.grade = "VI.5+"
        ascent1.style = "OS"
        ascent1.date = "2023-11-27"
        val ascent2 = Ascent()
        ascent2.route.name = "Superdirettissima Żabiego Konia"
        ascent2.route.grade = "VI+"
        ascent2.style = "RP"
        ascent2.date = "2023-11-27"
        val ascent3 = Ascent()
        ascent3.route.name = "Direttissima Rozwalistej"
        ascent3.route.grade = "VI.5+"
        ascent3.style = "FL"
        ascent3.date = "2023-11-27"

        val list = listOf(ascent1, ascent2, ascent3)

        Table(list)
    }
}