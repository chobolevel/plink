package com.plink.core.infrastructure.support

import com.github.f4b6a3.tsid.Tsid
import com.github.f4b6a3.tsid.TsidCreator
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.generator.BeforeExecutionGenerator
import org.hibernate.generator.EventType
import java.util.EnumSet

class TsidIdentifierGenerator : BeforeExecutionGenerator {

    private var prefix: String = ""

    override fun generate(
        session: SharedSessionContractImplementor?,
        owner: Any?,
        currentValue: Any?,
        eventType: EventType?
    ): String {
        return if (prefix.isEmpty()) {
            TsidCreator.getTsid().toString()
        } else {
            "$prefix${Tsid.fast()}"
        }
    }

    override fun getEventTypes(): EnumSet<EventType> {
        return EnumSet.of(EventType.INSERT)
    }
}
