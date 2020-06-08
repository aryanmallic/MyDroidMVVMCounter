package com.example.mvvmex.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Akhtar on 08/06/20
 */
@Entity(tableName = "todo")
@Parcelize
data class TodoTable(@PrimaryKey(autoGenerate = true) val id: Long,
                     @ColumnInfo(name = "title") val title: String,
                     @ColumnInfo(name = "content") val content: String) : Parcelable