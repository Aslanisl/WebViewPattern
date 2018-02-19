package aslanisl.mail.ru.webviewpattern.trash

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface TrashDao {
    @Insert(onConflict = REPLACE)
    fun insert(trashModel: TrashModel)
}