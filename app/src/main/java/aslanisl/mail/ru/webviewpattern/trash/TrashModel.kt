package aslanisl.mail.ru.webviewpattern.trash

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "trash_model")
class TrashModel(var someString : String) {
        @PrimaryKey(autoGenerate = true)
        private var id: Int = 0
}