package br.com.flpbrrs.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: String,
    val description: String,
    val title: String,
    val status: TaskStatus
) : Parcelable
