/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rwmobi.dazncodechallenge.domain.model.Schedule
import com.rwmobi.dazncodechallenge.ui.previewparameter.ScheduleProvider
import com.rwmobi.dazncodechallenge.ui.theme.DAZNCodeChallengeTheme
import com.rwmobi.dazncodechallenge.ui.theme.dazn_accent
import com.rwmobi.dazncodechallenge.ui.theme.dazn_divider
import com.rwmobi.dazncodechallenge.ui.theme.getDimension
import com.rwmobi.dazncodechallenge.ui.utils.toRelativeDateTimeString

@Composable
fun ScheduleListItem(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    imageLoader: ImageLoader,
) {
    val dimension = LocalConfiguration.current.getDimension()
    val context = LocalContext.current

    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(ratio = (4f / 3f))
                .background(color = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3f)),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(schedule.imageUrl)
                .crossfade(true)
                .build(),
            fallback = ColorPainter(dazn_divider),
            error = ColorPainter(dazn_divider),
            placeholder = ColorPainter(dazn_divider),
            contentDescription = schedule.title,
            contentScale = ContentScale.Crop,
            imageLoader = imageLoader,
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight = 1f)
                .padding(
                    horizontal = dimension.defaultFullPadding,
                    vertical = dimension.defaultHalfPadding,
                ),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = schedule.title,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = schedule.subtitle,
            )

            Spacer(
                Modifier
                    .weight(weight = 1f)
                    .padding(vertical = dimension.defaultFullPadding),
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = dazn_accent,
                text = schedule.date.toRelativeDateTimeString(context = context),
            )
        }
    }
}

@PreviewFontScale
@Composable
private fun Preview(
    @PreviewParameter(ScheduleProvider::class) schedule: Schedule,
) {
    DAZNCodeChallengeTheme {
        Surface {
            ScheduleListItem(
                modifier = Modifier.fillMaxWidth(),
                schedule = schedule,
                imageLoader = ImageLoader(LocalContext.current),
            )
        }
    }
}
