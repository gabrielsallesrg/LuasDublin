package com.gsrg.luasdublin.database.forecast

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gsrg.luasdublin.core.models.Forecast
import com.gsrg.luasdublin.database.LuasDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ForecastDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var dao: ForecastDao
    private lateinit var database: LuasDatabase

    @Before
    fun createDB() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, LuasDatabase::class.java
        ).build()
        dao = database.forecastDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        database.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertOneItemInDB() = runBlocking {
        val forecast = Forecast(destination = "Unknown", dueMinutes = "35")
        dao.insertAll(listOf(forecast))
        val forecastList = dao.selectAll()!!
        Assert.assertEquals(1, forecastList.size)
    }


    @Test
    fun insertOneItemInDBAndThenRemove() = runBlocking {
        val forecast = Forecast(destination = "Unknown", dueMinutes = "35")
        dao.insertAll(listOf(forecast))
        dao.clearTable()
        val forecastList = dao.selectAll()!!
        Assert.assertEquals(0, forecastList.size)
    }

    @Test
    fun insertMultipleItemsInDB() = runBlocking {
        val forecast0 = Forecast("Unknown", "1")
        val forecast1 = Forecast("Calabria", "2")
        val forecast2 = Forecast("UpTown", "3")
        val forecast3 = Forecast("Funk you up", "4")
        val forecast4 = Forecast("Thriller Night", "5")

        dao.insertAll(listOf(forecast0, forecast1, forecast2, forecast3, forecast4))
        var forecastList = dao.selectAll()!!

        Assert.assertEquals(5, forecastList.size)
        Assert.assertEquals(forecast0.destination, forecastList[0].destination)
        Assert.assertEquals(forecast1.dueMinutes, forecastList[1].dueMinutes)
        Assert.assertEquals(forecast2, forecastList[2])
        Assert.assertEquals(forecast3.destination, forecastList[3].destination)
        Assert.assertEquals(forecast4.dueMinutes, forecastList[4].dueMinutes)

        dao.clearTable()
        forecastList = dao.selectAll()!!
        Assert.assertEquals(0, forecastList.size)
    }
}