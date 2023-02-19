package service

import Blind
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun getAllBlinds(): List<Blind> {
    return jsonClient.get(Blind.path).body()
}

suspend fun getBlind(id: String): Blind {
    return jsonClient.get("${Blind.path}/$id").body()
}

suspend fun move(blind: Blind) {
    jsonClient.put("${Blind.path}/${blind.id}") {
        contentType(ContentType.Application.Json)
        setBody(blind)
    }
}

suspend fun addBlind(blind: Blind): Blind {
    return jsonClient.post(Blind.path) {
        contentType(ContentType.Application.Json)
        setBody(blind)
    }.body()
}

suspend fun deleteBlind(blind: Blind) {
    jsonClient.delete("${Blind.path}/${blind.id}") {

    }
}

suspend fun changeBlindName(blind: Blind) {
    jsonClient.put("${Blind.path}/${blind.id}") {
        contentType(ContentType.Application.Json)
        setBody(blind)
    }
}