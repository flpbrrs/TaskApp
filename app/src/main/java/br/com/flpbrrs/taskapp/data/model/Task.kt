package br.com.flpbrrs.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var title: String = "",
    var status: TaskStatus = TaskStatus.TODO
) : Parcelable
