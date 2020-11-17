package com.hazelcast.kubernetes;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Test
    void shouldConnectToHazelcastCluster() {
        IMap<String, String> mapToPut = hazelcastInstance.getMap("map");
        mapToPut.put("key", "value");

        IMap<Object, Object> mapToGet = hazelcastInstance.getMap("map");
        assertEquals("value", mapToGet.get("key"));
    }
}