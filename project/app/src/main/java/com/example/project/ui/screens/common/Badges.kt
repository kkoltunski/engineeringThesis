package com.example.project.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R
import com.example.project.data.currentsession.PercentageStyles

@Composable
fun Badge(
    title: String,
    description: String,
    size: Int,
    shapeColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(shapeColor)
    ) {
        Text(
            text = title,
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 50.dp)
        )

        Text(
            text = description,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 40.dp)
        )
    }
}

@Composable
fun SpecialBadge(
    percentageStyles: PercentageStyles,
    size: Int,
    shapeColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(shapeColor)
    ) {
        Text(
            text = stringResource(id = R.string.os) + "${percentageStyles.onSight}%",
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 50.dp)
        )

        Text(
            text = stringResource(id = R.string.fl) + "${percentageStyles.flash}%",
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Text(
            text = stringResource(id = R.string.rp) + "${percentageStyles.redPoint}%",
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp)
        )
    }
}