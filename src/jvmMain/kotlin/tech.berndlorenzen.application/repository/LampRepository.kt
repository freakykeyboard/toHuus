package tech.berndlorenzen.application.repository

import Lamp

interface LampRepository {
    suspend fun all(userId: String): List<Lamp>
    suspend fun newLamp(name: String, userId: String)
    suspend fun read(id: String): Lamp?
    suspend fun switch(lamp: Lamp)
    suspend fun rename(lamp: Lamp)
    suspend fun delete(id: String)
}