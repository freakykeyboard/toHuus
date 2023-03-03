package tech.berndlorenzen.application.repository

import SmartHome

interface SmartHomeRepository {
    suspend fun add(userId:String)
    suspend fun read(id:String):SmartHome?
    suspend fun update(smartHome:SmartHome)
    suspend fun delete(id:String)
}