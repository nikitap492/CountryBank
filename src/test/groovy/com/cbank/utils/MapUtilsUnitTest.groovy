package com.cbank.utils

import spock.lang.Specification

import static com.google.common.collect.Maps.immutableEntry

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class MapUtilsUnitTest extends Specification {

    def "test of correct filling map"() {
        given:
        def k1 = "k1"
        def k2 = "k2"
        def v1 = "v1"
        def v2 = "v2"
        when:
        def map = MapUtils.from(immutableEntry(k1, v1), immutableEntry(k2, v2))
        then:
        map.containsKey(k1)
        map.containsKey(k2)
        map.get(k1) == v1
        map.get(k2) == v2
        map.size() == 2
    }
}
