package fr.eurosport.sportstories.feature_media.domain.use_cases

import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.data.repository.FakeMediaRepository
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement.*
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMediaTest {

    private lateinit var getMedia: GetMedia
    private lateinit var fakeRepository: MediaRepository

    @Before
    fun setUp() {
        fakeRepository = FakeMediaRepository()
        getMedia = GetMedia(fakeRepository)
    }

    @Test
    fun `Request media elements, return Media with 3 stories and 3 videos`() = runTest {

        val result: Resource<List<MediaElement>> = getMedia().first()

        result.data?.let { elementsList ->
            Assert.assertEquals(6, elementsList.size)
        }
    }

    @Test
    fun `Request media elements, return sorted and alternated`() = runTest {

        val result: Resource<List<MediaElement>> = getMedia().first()

        result.data?.let { elementsList ->

            Assert.assertTrue(elementsList[0] is Story)
            Assert.assertEquals(7738025, (elementsList[0] as Story).id)

            Assert.assertTrue(elementsList[1] is Video)
            Assert.assertEquals(777788, (elementsList[1] as Video).id)

            Assert.assertTrue(elementsList[2] is Story)
            Assert.assertEquals(7738009, (elementsList[2] as Story).id)

            Assert.assertTrue(elementsList[3] is Video)
            Assert.assertEquals(777799, (elementsList[3] as Video).id)
        }
    }
}