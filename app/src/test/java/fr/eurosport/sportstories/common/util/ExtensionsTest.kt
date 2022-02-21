package fr.eurosport.sportstories.common.util

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExtensionsTest {

    private val mockContext = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        every { mockContext.getString(fr.eurosport.sportstories.R.string.years) } answers { "years" }
        every { mockContext.getString(fr.eurosport.sportstories.R.string.days) } answers { "days" }
        every { mockContext.getString(fr.eurosport.sportstories.R.string.hours) } answers { "hours" }
        every { mockContext.getString(fr.eurosport.sportstories.R.string.minutes) } answers { "minutes" }
        every { mockContext.getString(fr.eurosport.sportstories.R.string.seconds) } answers { "seconds" }
    }

    @Test
    fun `Convert String to timestamp, return Long`() {
        Assert.assertEquals(1588197999470, "1588197999.47".parseTimestamp())
        Assert.assertEquals(1588171753103, "1588171753.103".parseTimestamp())
        Assert.assertEquals(1588110350850, "1588110350.85".parseTimestamp())
    }

    @Test
    fun `Convert Timestamp Duration to Text, return String`() {

        val timeText = mockContext.convertSecondsToTimeString(before = 1588171753103, now = 1588197999470)
        Assert.assertEquals("7 hours 17 minutes", timeText)

        val timeText2 = mockContext.convertSecondsToTimeString(before = 1588110350850, now = 1588224705843)
        Assert.assertEquals("1 days 7 hours", timeText2)
    }
}