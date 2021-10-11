/*
 * Copyright (c) 2021. Ryan Wong (hello@ryanwong.co.uk)
 *
 */

package uk.ryanwong.dazn.codechallenge.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uk.ryanwong.dazn.codechallenge.base.BaseRepository
import uk.ryanwong.dazn.codechallenge.data.model.Event
import uk.ryanwong.dazn.codechallenge.data.model.Schedule
import uk.ryanwong.dazn.codechallenge.util.wrapEspressoIdlingResource
import java.io.IOException

class FakeRepository : BaseRepository {

    // Use simple data structures to provide a controlled set of data for testing ViewModel
    private val observableEvents = MutableLiveData<List<Event>>()
    private val observableSchedules = MutableLiveData<List<Schedule>>()

    private var shouldReturnError = false
    private var exceptionMessage = ""

    // Faked remote data
    private var remoteEventList = listOf<Event>()
    private var remoteScheduleList = listOf<Schedule>()

    init {
        observableEvents.postValue(listOf())
        observableSchedules.postValue(listOf())
    }

    override fun observeEvents(): LiveData<List<Event>> {
        wrapEspressoIdlingResource {
            return observableEvents
        }
    }

    override fun observeSchedule(): LiveData<List<Schedule>> {
        wrapEspressoIdlingResource {
            return observableSchedules
        }
    }

    override suspend fun getEvents(): List<Event> {
        wrapEspressoIdlingResource {
            return observableEvents.value!!
        }
    }

    override suspend fun getSchedule(): List<Schedule> {
        wrapEspressoIdlingResource {
            return observableSchedules.value!!
        }
    }

    override suspend fun refreshEvents() {
        wrapEspressoIdlingResource {
            if (shouldReturnError) {
                throw IOException(exceptionMessage)
            }
            observableEvents.postValue(remoteEventList)
        }
    }

    override suspend fun refreshSchedule() {
        wrapEspressoIdlingResource {
            if (shouldReturnError) {
                throw IOException(exceptionMessage)
            }
            observableSchedules.postValue(remoteScheduleList)
        }
    }

    fun setReturnError(value: Boolean, errorMessage: String) {
        shouldReturnError = value
        exceptionMessage = errorMessage
    }

    /***
     * Submit a list of events as a faked remote data source.
     * When refreshEvents() is called, the list will be loaded to the ViewModel
     */
    fun submitEventList(events: List<Event>) {
        remoteEventList = events
    }

    /***
     * Submit a list of events as a faked remote data source.
     * When refreshEvents() is called, the list will be loaded to the ViewModel
     */
    fun submitScheduleList(schedules: List<Schedule>) {
        remoteScheduleList = schedules
    }

}