package fr.eurosport.sportstories.feature_media.presentation.mapper

import android.content.Context
import android.content.res.Resources
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.feature_media.fixtures.MediaElementFixtures
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.StoryUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.VideoUI
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MediaMapperTest {

    private val context: Context = mockk()
    private val mockedResources: Resources by lazy {
        mockk {
            every { context.getString(R.string.seconds) } returns "seconds"
            every { context.getString(R.string.minutes) } returns "minutes"
            every { context.getString(R.string.hours) } returns "hours"
            every { context.getString(R.string.days) } returns "days"
            every { context.getString(R.string.years) } returns "years"
        }
    }
    private val tested = MediaMapper(context)

    @Test
    fun `given tested when getting Story then it returns the right StoryUI`() {
        // given
        every { context.resources } returns mockedResources
        val input = MediaElementFixtures.buildMedia().stories

        // when
        val result = input.map {
            tested.map(it)
        }

        // then
        assertEquals(result.size, 3)
        assertTrue(result[0] is StoryUI)
        (result[0] as StoryUI).apply {
            val origin = input[0]
            assertEquals(id, origin.id)
            assertEquals(title, origin.title)
            assertEquals(date, "3 years 180 days")
            assertEquals(category, origin.sport.name)
            assertEquals(thumbUrl, origin.imageUrl)
            assertEquals(author, origin.author)
            assertEquals(teaser, origin.teaser)
            assertFalse(isIconVisible)
        }
    }

    @Test
    fun `given tested when getting Video then it returns the right VideoUI`() {
        // given
        every { context.resources } returns mockedResources
        val input = MediaElementFixtures.buildMedia().videos

        // when
        val result = input.map {
            every {
                context.getString(
                    R.string.video_description,
                    it.viewCount
                )
            } returns "${it.viewCount} views"
            tested.map(it)
        }

        // then
        assertEquals(result.size, 3)
        assertTrue(result[0] is VideoUI)
        (result[0] as VideoUI).apply {
            val origin = input[0]
            assertEquals(id, origin.id)
            assertEquals(title, origin.title)
            assertEquals(date, "")
            assertEquals(category, origin.sport.name)
            assertEquals(thumbUrl, origin.thumbUrl)
            assertEquals(videoUrl, origin.videoUrl)
            assertEquals(viewCount, "12333 views")
            assertTrue(isIconVisible)
        }
    }
}