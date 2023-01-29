package com.example.demo.config

import com.example.`book-svc`.config.GeoJsonDeserializer
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import kotlin.test.assertEquals

class GeoJsonDeserializerTest {
    @Test
    @Throws(JsonProcessingException::class)
    fun testDeserialize() {
        // Create an instance of the GeoJsonDeserializer class
        val deserializer = GeoJsonDeserializer()

        // Create a Jackson ObjectMapper instance
        val mapper = ObjectMapper()

        // Register the GeoJsonDeserializer with the ObjectMapper
        val module = SimpleModule()
        module.addDeserializer(GeoJsonPoint::class.java, deserializer)
        mapper.registerModule(module)

        // Deserialize the JSON string using the ObjectMapper
        val point: GeoJsonPoint = mapper.readValue(
            TEST_GEOJSON_POINT_JSON,
            GeoJsonPoint::class.java
        )

        // Verify that the deserialized GeoJsonPoint object has the expected x and y coordinates
        assertEquals(TEST_POINT_X, point.x)
        assertEquals(TEST_POINT_Y, point.y)
    }

    companion object {
        private const val TEST_POINT_X = 1.0
        private const val TEST_POINT_Y = 2.0
        private const val TEST_GEOJSON_POINT_JSON = "{\"type\":\"Point\",\"coordinates\":[1.0,2.0]}"
    }
}
