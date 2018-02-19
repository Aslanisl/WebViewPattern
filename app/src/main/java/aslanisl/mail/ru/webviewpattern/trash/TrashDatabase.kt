package aslanisl.mail.ru.webviewpattern.trash

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(TrashModel::class)], version = 1)
abstract class TrashDatabase : RoomDatabase() {

    abstract fun trashDao() : TrashDao
}