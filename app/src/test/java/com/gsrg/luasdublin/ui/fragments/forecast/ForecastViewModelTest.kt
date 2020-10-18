package com.gsrg.luasdublin.ui.fragments.forecast

import com.gsrg.luasdublin.database.ILuasDatabase
import com.gsrg.luasdublin.database.forecast.Forecast
import com.gsrg.luasdublin.database.forecast.ForecastDao
import com.gsrg.luasdublin.database.updatetime.UpdateTime
import com.gsrg.luasdublin.database.updatetime.UpdateTimeDao
import com.gsrg.luasdublin.domain.model.ForecastResponse
import com.gsrg.luasdublin.domain.repository.IForecastRepository
import com.gsrg.luasdublin.utils.ICalendar
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test

class ForecastViewModelTest {

    @Test
    fun testsIfIsAfternoon() {
        var viewModel = createViewModelForCalendar(0, 0)
        Assert.assertEquals(false, viewModel.isAfternoon())

        viewModel = createViewModelForCalendar(11, 59)
        Assert.assertEquals(false, viewModel.isAfternoon())

        viewModel = createViewModelForCalendar(12, 0)
        Assert.assertEquals(false, viewModel.isAfternoon())

        viewModel = createViewModelForCalendar(12, 1)
        Assert.assertEquals(true, viewModel.isAfternoon())

        viewModel = createViewModelForCalendar(13, 0)
        Assert.assertEquals(true, viewModel.isAfternoon())

        viewModel = createViewModelForCalendar(23, 59)
        Assert.assertEquals(true, viewModel.isAfternoon())
    }

    private fun createViewModelForCalendar(hour: Int, minute: Int): ForecastViewModel {
        return ForecastViewModel(
            repository = MockForecastRepository(),
            database = MockLuasDatabase(),
            calendar = MockCalendar(hour = hour, minute = minute)
        )
    }

    /**
     * Mocked classes
     */
    class MockCalendar(
        private val hour: Int = 0,
        private val minute: Int = 0
    ) : ICalendar {

        override fun hour(): Int = hour
        override fun minute(): Int = minute
        override fun time(): Long = 0
        override fun formattedHourAndMinute(time: Long): String = ""
    }

    class MockForecastRepository : IForecastRepository {
        override fun getForecastByStop(stop: String): Observable<ForecastResponse> {
            return Observable.just(ForecastResponse(message = ""))
        }

    }

    class MockLuasDatabase : ILuasDatabase {
        override fun updateTimeDao(): UpdateTimeDao {
            return (object : UpdateTimeDao {
                override suspend fun insert(updateTime: UpdateTime) {}
                override suspend fun select(): UpdateTime? = null
                override suspend fun clearTable() {}

            })
        }

        override fun forecastDao(): ForecastDao {
            return (object : ForecastDao {
                override suspend fun insertAll(forecastList: List<Forecast>) {}
                override suspend fun selectAll(): List<Forecast>? = null
                override suspend fun clearTable() {}

            })
        }
    }
}