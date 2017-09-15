package com.thoughtbot.tropos.viewmodels

import android.content.Context
import android.location.Location
import com.thoughtbot.tropos.BuildConfig
import com.thoughtbot.tropos.data.Icon.PARTLY_CLOUDY_DAY
import com.thoughtbot.tropos.data.Condition
import com.thoughtbot.tropos.data.Unit
import com.thoughtbot.tropos.data.Unit.IMPERIAL
import com.thoughtbot.tropos.data.WindDirection
import com.thoughtbot.tropos.testUtils.MockCondition
import com.thoughtbot.tropos.testUtils.MockGeocoder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*
import kotlin.test.assertEquals

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class WeatherToolbarViewModelTest() {

  lateinit var context: Context
  val mockCondition: Condition = MockCondition.getInstance()

  @Before
  fun setup() {
    RuntimeEnvironment.application.let { context = it }
  }

  @Test
  @Config(shadows = arrayOf(MockGeocoder::class))
  fun testTitle() {
    val viewModel = WeatherToolbarViewModel(context, mockCondition)
    val expected = "San Francisco"
    val actual = viewModel.title()

    assertEquals(expected, actual)
  }

  @Test
  fun testSubtitle() {
    val viewModel = WeatherToolbarViewModel(context, mockCondition)
    val expected = "Updated at 4:16 PM"
    val actual = viewModel.subtitle()

    assertEquals(expected, actual)
  }
}
