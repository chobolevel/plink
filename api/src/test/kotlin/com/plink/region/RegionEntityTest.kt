package com.plink.region

import com.plink.core.region.entity.Region
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Region entity unit test")
class RegionEntityTest {

    private val dummyRegion: Region = DummyRegion.toEntity()

    @Test
    fun `삭제 테스트`() {
        // given & when
        dummyRegion.delete()

        // then
        assertThat(dummyRegion.isDeleted).isTrue
    }

    @Test
    fun `부모 지역 할당 테스트`() {
        // given
        val dummyParentRegion: Region = DummyRegion.toParentRegion()

        // when
        dummyRegion.assignParent(region = dummyParentRegion)

        // then
        assertThat(dummyRegion.parent).isEqualTo(dummyParentRegion)
    }
}
