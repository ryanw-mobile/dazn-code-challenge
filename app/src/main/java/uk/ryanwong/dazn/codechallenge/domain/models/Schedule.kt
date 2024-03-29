package uk.ryanwong.dazn.codechallenge.domain.models

import java.util.Date

data class Schedule(
    val scheduleId: Int,
    val title: String,
    val subtitle: String,
    val date: Date,
    val imageUrl: String,
)
