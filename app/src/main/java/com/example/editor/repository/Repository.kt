package app.dreamBig.bookapp.repository

import com.example.editor.model.User
import official.app1000green.a1000green.network.RetrofitClient

object Repository {



    suspend fun getUsersList( ): User {
        return RetrofitClient.invoke().getUsers()
    }

}