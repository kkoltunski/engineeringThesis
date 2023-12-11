package com.example.project.ui.screens.common.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.data.CommentData
import com.example.project.data.GradeData
import com.example.project.data.RegionData
import com.example.project.data.RockData
import com.example.project.data.RouteData
import com.example.project.data.currentsession.Ascent
import com.example.project.ui.theme.ProjectTheme

@Composable
fun <T> Table(
    list: List<T>,
    onItemClicked: (Int) -> Unit = {},
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
                        onClick = { onItemClicked(item.id) },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is RockData -> {
                    RockItem(
                        rock = item,
                        onClick = { onItemClicked(item.id) },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is RouteData -> {
                    RouteItem(
                        route = item,
                        onClick = { onItemClicked(item.id) },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is GradeData -> {
                    GradeItem(
                        grade = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
                is CommentData -> {
                    CommentItem(
                        comment = item,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
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