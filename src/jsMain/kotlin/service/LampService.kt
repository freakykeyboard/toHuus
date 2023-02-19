package service

import Lamp
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


suspend fun getAllLamps(): List<Lamp> {
    return jsonClient.get(Lamp.path).body()

}

suspend fun addLamp(lamp: Lamp): Lamp {
    return jsonClient.post(Lamp.path) {
        contentType(ContentType.Application.Json)
        setBody(lamp)
    }.body()

}

suspend fun getLamp(id: String): Lamp {
    return jsonClient.get("${Lamp.path}/$id").body()
}

suspend fun updateLamp(lamp: Lamp) {
    jsonClient.put("${Lamp.path}/${lamp.id}") {
        contentType(ContentType.Application.Json)
        setBody(lamp)
    }

}

suspend fun deleteLamp(lamp: Lamp) {
    jsonClient.delete("${Lamp.path}/${lamp.id}") {

    }
}