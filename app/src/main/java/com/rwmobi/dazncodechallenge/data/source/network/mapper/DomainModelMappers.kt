/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.data.source.network.mapper

import com.rwmobi.dazncodechallenge.data.source.network.dto.EventNetworkDto
import com.rwmobi.dazncodechallenge.data.source.network.dto.ScheduleNetworkDto
import com.rwmobi.dazncodechallenge.domain.model.Event
import com.rwmobi.dazncodechallenge.domain.model.Schedule

fun EventNetworkDto.asEvent(): Event {
    return Event(
        eventId = eventId,
        title = title,
        subtitle = subtitle,
        date = date,
        imageUrl = imageUrl,
        videoUrl = videoUrl,
    )
}

fun List<EventNetworkDto>.asEvent(): List<Event> {
    return map {
        it.asEvent()
    }
}

fun ScheduleNetworkDto.asSchedule(): Schedule {
    return Schedule(
        scheduleId = scheduleId,
        title = title,
        subtitle = subtitle,
        date = date,
        imageUrl = imageUrl,
    )
}

fun List<ScheduleNetworkDto>.asSchedule(): List<Schedule> {
    return map {
        it.asSchedule()
    }
}
