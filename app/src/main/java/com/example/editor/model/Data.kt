package com.example.editor.model

import io.realm.RealmObject

open class Data(
    var avatar: String? = null,
    var email: String? = null,
    var first_name: String? = null,
    var id: Int? = null,
    var last_name: String ? = null
) : RealmObject()