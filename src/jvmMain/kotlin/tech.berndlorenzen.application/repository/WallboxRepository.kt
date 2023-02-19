package tech.berndlorenzen.application.repository

import Wallbox

interface WallboxRepository {
    suspend fun all(userId: String): List<Wallbox>
    suspend fun read(id: String): Wallbox?
    suspend fun newWallbox(name: String, userId: String): Wallbox
    suspend fun update(wallbox: Wallbox)
    suspend fun delete(id: String)

}