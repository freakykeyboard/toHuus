package tech.berndlorenzen.application.repository

import Blind
import com.mongodb.ConnectionString
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class BlindMongoDbRepository : BlindRepository {

    private var database: CoroutineDatabase
    private var collection: CoroutineCollection<Blind>

    init {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false")
        }
        val client =
            if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient("mongodb://localhost/").coroutine
        this.database = client.getDatabase(connectionString?.database ?: "toHuus")
        this.collection = this.database.getCollection()
    }

    override suspend fun all(userId: String): List<Blind> {
        return collection.find(Blind::userId eq userId).toList()
    }

    override suspend fun read(id: String): Blind? {
        return collection.findOne(Blind::id eq id)
    }

    override suspend fun create(name: String, userId: String): Blind {
        val newBlind = Blind(id = ObjectId().toString(), name = name, userId = userId)
        collection.insertOne(newBlind)
        return newBlind
    }

    override suspend fun update(blind: Blind) {
        collection.updateOne(Blind::id eq blind.id, blind)
    }

    override suspend fun delete(id: String) {
        collection.deleteOne(Blind::id eq id)
    }
}