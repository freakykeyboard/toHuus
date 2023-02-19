package tech.berndlorenzen.application.repository

import Blind

interface BlindRepository {
    suspend fun all(userId: String): List<Blind>
    suspend fun read(id: String): Blind?
    suspend fun create(name: String, userId: String): Blind
    suspend fun update(blind: Blind)
    suspend fun delete(id: String)

}