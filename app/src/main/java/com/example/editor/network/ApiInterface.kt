package official.app1000green.a1000green.network

import com.example.editor.model.User
import retrofit2.http.*

interface ApiInterface {


    @GET("users")
    suspend fun getUsers(): User


}