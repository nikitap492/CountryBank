package com.cbank.utils

import spock.lang.Specification

class RandomUtilsUnitTest extends Specification {


    def "GenerateAccountNum"() {
        expect:
        RandomUtils.generateAccountNum().length() == 16
        where:
        i << (1..100)
    }
}
