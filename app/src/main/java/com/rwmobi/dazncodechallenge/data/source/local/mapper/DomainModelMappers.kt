/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.data.source.local.mapper

import com.rwmobi.dazncodechallenge.data.source.local.model.EventDbEntity
import com.rwmobi.dazncodechallenge.data.source.local.model.ScheduleDbEntity
import com.rwmobi.dazncodechallenge.domain.model.Event
import com.rwmobi.dazncodechallenge.domain.model.Schedule

fun EventDbEntity.asEvent(): Event {
    return Event(
        eventId = this.eventId,
        title = this.title,
        subtitle = this.subtitle,
        date = this.date,
        imageUrl = this.imageUrl,
        videoUrl = this.videoUrl,
    )
}

fun List<EventDbEntity>.asEvent(): List<Event> {
    return map {
        it.asEvent()
    }
}

fun ScheduleDbEntity.asSchedule(): Schedule {
    return Schedule(
        scheduleId = this.scheduleId,
        title = this.title,
        subtitle = this.subtitle,
        date = this.date,
        imageUrl = this.imageUrl,
    )
}

fun List<ScheduleDbEntity>.asSchedule(): List<Schedule> {
    return map {
        it.asSchedule()
    }
}
