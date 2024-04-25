/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.rwmobi.dazncodechallenge.R
import com.rwmobi.dazncodechallenge.ui.navigation.AppNavItem
import com.rwmobi.dazncodechallenge.ui.test.DaznCodeChallengeTestRule
import com.rwmobi.dazncodechallenge.ui.test.withRole

internal class MainActivityTestRobot(
    private val composeTestRule: DaznCodeChallengeTestRule,
) {
    init {
        printSemanticTree()
        assertNavigationBarIsDisplayed()
        assertNavigationItemsAreDisplayed()
    }

    fun printSemanticTree() {
        composeTestRule.onRoot().printToLog("SemanticTree")
    }

    fun assertNavigationBarIsDisplayed() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = activity.getString(R.string.content_description_navigation_bar)).assertIsDisplayed()
        }
    }

    fun assertNavigationRailIsDisplayed() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = activity.getString(R.string.content_description_navigation_rail)).assertIsDisplayed()
        }
    }

    fun assertNavigationItemsAreDisplayed() {
        with(composeTestRule) {
            for (navigationItem in AppNavItem.navBarItems) {
                onNode(
                    matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(navigationItem.titleResId))),
                ).assertIsDisplayed()
            }
        }
    }

    fun assertEventsTabIsSelected() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.events))),
            ).assertIsSelected()

            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.schedule))),
            ).assertIsNotSelected()
        }
    }

    fun assertScheduleTabIsSelected() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.events))),
            ).assertIsNotSelected()

            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.schedule))),
            ).assertIsSelected()
        }
    }

    fun tapNavigationEvents() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.events))),
            ).performClick()
        }
    }

    fun tapNavigationSchedule() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab).and(hasContentDescription(value = activity.getString(R.string.schedule))),
            ).performClick()
        }
    }
}
