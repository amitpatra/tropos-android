package com.thoughtbot.tropos.ui

import android.content.Context
import android.location.Location
import com.nhaarman.mockito_kotlin.isA
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.thoughtbot.tropos.BuildConfig
import com.thoughtbot.tropos.data.Condition
import com.thoughtbot.tropos.data.Icon
import com.thoughtbot.tropos.data.Unit
import com.thoughtbot.tropos.data.Unit.IMPERIAL
import com.thoughtbot.tropos.data.Weather
import com.thoughtbot.tropos.data.WeatherDataSource
import com.thoughtbot.tropos.data.WindDirection
import com.thoughtbot.tropos.permissions.Permission
import com.thoughtbot.tropos.testUtils.MockCondition
import com.thoughtbot.tropos.ui.MainPresenter
import com.thoughtbot.tropos.ui.MainView
import com.thoughtbot.tropos.ui.ViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainPresenterTest() {

  lateinit var context: Context
  val view = mock<MainView>()
  val permission = mock<Permission>()
  val weatherDataSource = mock<WeatherDataSource>()
  val mockCondition: Condition = MockCondition.getInstance()
  val mockWeather: Weather = Weather(mockCondition, mockCondition,
      listOf(mockCondition, mockCondition, mockCondition))

  @Before
  fun setup() {
    RuntimeEnvironment.application.let { context = it }
  }

  @Test
  fun testOnResume_hasPermission() {
    val presenter = MainPresenter(view, null, weatherDataSource, permission)
    whenever(view.context).thenReturn(context)
    stubWeather()
    stubPermission(true)

    presenter.onStart()

    verify(view).viewState = isA<ViewState.Loading>()
    verify(view).viewState = isA<ViewState.Weather>()
  }

  @Test
  fun testOnResume_doesNotHavePermission() {
    val presenter = MainPresenter(view, null, weatherDataSource, permission)
    whenever(view.context).thenReturn(context)
    stubWeather()
    stubPermission(false)

    presenter.onStart()

    verify(view).viewState = isA<ViewState.Error>()
  }

  fun stubWeather() {
    whenever(weatherDataSource.fetchWeather()).thenReturn(Observable.just(mockWeather))
  }

  fun stubPermission(hasPermission: Boolean) {
    whenever(permission.hasPermission()).thenReturn(hasPermission)
  }

}
