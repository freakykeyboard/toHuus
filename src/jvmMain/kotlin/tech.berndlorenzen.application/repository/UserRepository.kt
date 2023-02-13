package tech.berndlorenzen.application.repository

import User

interface UserRepository {
    suspend fun add(username:String,password: String):User?
    suspend fun read(id:String):User?
    suspend fun checkCredentials(name:String,password:String):User?
    suspend fun update(user:User)
    suspend fun delete(id:String)
}