package com.gsrg.luasdublin.ui.fragments.forecast

class ForecastViewModelTest {

    //TODO fix
/*
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
        override fun getForecast(calendar: ICalendar): Flow<Result<List<Forecast>>> = flow {
            emit(Result.Success(data = emptyList()))
        }

        override fun getUpdatedTime(): Flow<UpdateTime?> = flow {
            emit(null)
        }

    }
 */
}