package com.example.mod21_final

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film(val title: String,
                val poster: Int,                  // id's of picture resources
                val description: String) : Parcelable{  }
