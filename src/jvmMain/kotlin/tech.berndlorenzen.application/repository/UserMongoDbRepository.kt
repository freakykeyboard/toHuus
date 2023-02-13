package tech.berndlorenzen.application.repository

import User
import com.mongodb.ConnectionString
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class UserMongoDBRepository : UserRepository {
    private var collection: CoroutineCollection<User>
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

    override suspend fun add(username: String, password: String): User? {
        val user = User(username = username, password = password)
        user.password = user.password.hashCode().toString()
        user.userId = ObjectId().toString()
        collection.insertOne(user)
        return collection.findOne(User::username eq user.username)


    }

    override suspend fun read(id: String): User? {
        return collection.findOne(User::userId eq id)
    }

    override suspend fun checkCredentials(name: String, password: String): User? {
        return collection.findOne(User::username eq name, User::password eq password)
    }

    override suspend fun update( user: User) {
        collection.updateOne(User::userId eq user.userId,user)

    }

    override suspend fun delete(id: String) {
        collection.deleteOne(User::userId eq id)
    }
}