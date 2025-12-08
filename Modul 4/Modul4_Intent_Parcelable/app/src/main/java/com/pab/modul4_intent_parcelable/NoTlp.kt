package com.pab.modul4_intent_parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoTlp (
    val no: String?
) : Parcelable