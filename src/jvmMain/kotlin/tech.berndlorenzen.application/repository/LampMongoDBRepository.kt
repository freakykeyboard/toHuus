package tech.berndlorenzen.application.repository

import Lamp
import com.mongodb.ConnectionString
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class LampMongoDBRepository : LampRepository {
    private var database: CoroutineDatabase
    private var collection: CoroutineCollection<Lamp>

    init {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client =
            if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient("mongodb://localhost/").coroutine
        this.database = client.getDatabase(connectionString?.database ?: "toHuus")
        this.collection = this.database.getCollection()
    }

    override suspend fun all(userId: String): List<Lamp> {
        return collection.find(Lamp::userId eq userId).toList()
    }

    override suspend fun newLamp(name: String, userId: String) {
        val newLamp = Lamp(id = ObjectId().toString(), name = name)
        newLamp.userId = userId
        collection.insertOne(newLamp)
    }

    override suspend fun read(id: String): Lamp? {
        return collection.findOne(Lamp::id eq id)
    }

    override suspend fun switch(lamp: Lamp) {
        collection.updateOne(Lamp::id eq lamp.id, lamp)

    }

    override suspend fun rename(lamp: Lamp) {
        collection.updateOne(Lamp::id eq lamp.id, lamp)
    }

    override suspend fun delete(id: String) {
        collection.deleteOne(Lamp::id eq id)
    }
}