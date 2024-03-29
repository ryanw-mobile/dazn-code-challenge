/*
 * Copyright (c) 2021. Ryan Wong (hello@ryanwong.co.uk)
 *
 */

package uk.ryanwong.dazn.codechallenge.data.repository

import androidx.lifecycle.LiveData
import uk.ryanwong.dazn.codechallenge.domain.models.Event
import uk.ryanwong.dazn.codechallenge.domain.models.Schedule

interface Repository {
    // The LiveData implementations are designed to be used by ViewModels
    fun observeEvents(): LiveData<List<Event>>

    fun observeSchedule(): LiveData<List<Schedule>>

    // The static versions are designed to be used for tests
    suspend fun getEvents(): List<Event>

    suspend fun getSchedule(): List<Schedule>

    // The functions exposed to ViewModels
    // They don't have to care about where we pulled the data from
    suspend fun refreshEvents()

    suspend fun refreshSchedule()
}
