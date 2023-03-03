package tech.berndlorenzen.application.repository

import SmartHome
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class SmartHomeMongoDbRepository : SmartHomeRepository{
    private var collection: CoroutineCollection<SmartHome>
    private var database: CoroutineDatabase

    init {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client =
            if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient("mongodb://localhost/").coroutine
        this.database = client.getDatabase(connectionString?.database ?: "toHuus")
        this.collection = this.database.getCollection()
    }
    override suspend fun add(userId:String) {
        collection.insertOne(SmartHome(userId = userId))
    }

    override suspend fun read(id: String): SmartHome? {
        return collection.findOne(SmartHome::userId eq id)
    }

    override suspend fun update(smartHome: SmartHome) {
         collection.updateOne(SmartHome::userId eq smartHome.userId,smartHome)
    }

    override suspend fun delete(id: String) {
        collection.deleteOne(SmartHome::userId eq id)
    }
}