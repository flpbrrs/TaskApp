package br.com.flpbrrs.taskapp.data.model

import android.os.Parcelable
import br.com.flpbrrs.taskapp.utils.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var title: String = "",
    var status: TaskStatus = TaskStatus.TODO
) : Parcelable {
    init {
        if (id.isEmpty()) {
            this.id = FirebaseHelper.getDatabase().push().key!!
        }
    }
}
