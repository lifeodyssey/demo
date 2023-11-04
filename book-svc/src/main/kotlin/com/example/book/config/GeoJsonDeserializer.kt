package com.example.book.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class GeoJsonDeserializer : JsonDeserializer<GeoJsonPoint>() {
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): GeoJsonPoint {
        val tree: JsonNode = jp.codec.readTree(jp)
        val type: String = tree.get(JSON_KEY_GEO_JSON_TYPE).asText()

        if (!GEO_JSON_TYPE_POINT.equals(type, ignoreCase = true)) {
            throw RuntimeException(String.format("No logic present to deserialize %s ", tree.asText()))
        }

        val coordsNode: JsonNode = tree.get(JSON_KEY_GEO_JSON_COORDS)

        val x = coordsNode.get(0).asDouble()
        val y = coordsNode.get(1).asDouble()
        return GeoJsonPoint(x, y)
    }

    companion object {
        private const val GEO_JSON_TYPE_POINT = "Point"
        private const val JSON_KEY_GEO_JSON_TYPE = "type"
        private const val JSON_KEY_GEO_JSON_COORDS = "coordinates"
    }
}
