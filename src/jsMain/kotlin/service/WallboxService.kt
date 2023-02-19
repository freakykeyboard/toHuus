package service

import Wallbox
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun getAllWallboxes(): List<Wallbox> {
    return jsonClient.get(Wallbox.path).body()
}

suspend fun addWallbox(wallbox: Wallbox): Wallbox {
    return jsonClient.post(Wallbox.path) {
        contentType(ContentType.Application.Json)
        setBody(wallbox)
    }.body()
}

suspend fun updateWallbox(wallbox: Wallbox) {
    jsonClient.put("${Wallbox.path}/${wallbox.id}") {
        contentType(ContentType.Application.Json)
        setBody(wallbox)
    }
}

suspend fun getWallbox(id: String): Wallbox {
    return jsonClient.get("${Wallbox.path}/$id").body()
}

suspend fun deleteWallbox(wallbox: Wallbox) {
    jsonClient.delete("${Wallbox.path}/${wallbox.id}") {
    }
}