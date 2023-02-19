package tech.berndlorenzen.application.repository

import Wallbox
import com.mongodb.ConnectionString
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class WallboxMongoDbRepository : WallboxRepository {
    private var collection: CoroutineCollection<Wallbox>

    init {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client =
            if (connectionString != null) KMongo.createClient(connectionString).coroutine
            else KMongo.createClient("mongodb://localhost/").coroutine
        this.collection = client.getDatabase(connectionString?.database ?: "toHuus")
            .getCollection()
    }

    override suspend fun all(userId: String): List<Wallbox> {
        return collection.find(Wallbox::userId eq userId).toList()
    }

    override suspend fun read(id: String): Wallbox? {
        return collection.findOne(Wallbox::id eq id)
    }

    override suspend fun newWallbox(name: String, userId: String): Wallbox {
        val newWallbox = Wallbox(id = ObjectId().toString(), name = name, userId = userId)
        collection.insertOne(newWallbox)
        return newWallbox
    }

    override suspend fun update(wallbox: Wallbox) {
        collection.updateOne(Wallbox::id eq wallbox.id, wallbox)
    }

    override suspend fun delete(id: String) {
        collection.deleteOne(Wallbox::id eq id)
    }


}