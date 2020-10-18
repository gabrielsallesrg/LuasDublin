package com.gsrg.luasdublin.database.updatetime

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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
class UpdateTimeDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var dao: UpdateTimeDao
    private lateinit var database: LuasDatabase

    @Before
    fun createDB() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, LuasDatabase::class.java
        ).build()
        dao = database.updateTimeDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        database.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertItemInDB() = runBlocking {
        val updateTime = com.gsrg.luasdublin.core.models.UpdateTime(date = 5000)
        dao.insert(updateTime)
        val dbValue = dao.select()!!
        Assert.assertEquals(updateTime.date, dbValue.date)
        Assert.assertEquals(updateTime, dbValue)
    }


    @Test
    fun insertItemInDBAndThenRemove() = runBlocking {
        val updateTime = com.gsrg.luasdublin.core.models.UpdateTime(date = 5000)
        dao.insert(updateTime)
        dao.clearTable()
        val dbValue = dao.select()
        Assert.assertEquals(null, dbValue)
    }

    @Test
    fun insertMultipleItemsInDB() = runBlocking {
        val updateTime1 = com.gsrg.luasdublin.core.models.UpdateTime(date = 5000)
        val updateTime2 = com.gsrg.luasdublin.core.models.UpdateTime(date = 6000)
        dao.insert(updateTime1)
        dao.insert(updateTime2)
        val dbValue = dao.select()!!
        Assert.assertEquals(updateTime1.date, dbValue.date)
        Assert.assertEquals(updateTime1, dbValue)
    }
}