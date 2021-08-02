package com.example.editor.Utils

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .name("Users.realm")
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}