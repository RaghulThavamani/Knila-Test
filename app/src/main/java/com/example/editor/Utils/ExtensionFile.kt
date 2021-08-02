package com.example.editor


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.realm.Realm
import io.realm.RealmAsyncTask
import io.realm.RealmResults
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import com.example.editor.model.Data
import java.io.ByteArrayOutputStream


private var realmAsyncTask: RealmAsyncTask? = null
lateinit var realm: Realm

fun ImageView.getGlideFunction(url: String){
    val reqOpt = RequestOptions
        .fitCenterTransform()
        .dontAnimate().dontTransform()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
        .priority(Priority.IMMEDIATE)
        .encodeFormat(Bitmap.CompressFormat.PNG)
        .format(DecodeFormat.DEFAULT)
    // Glide is use to load image
    // from url in your imageview.
    // Glide is use to load image
    // from url in your imageview.
    Glide.with(this.context)
        .applyDefaultRequestOptions(reqOpt)
        .load(url)
        .fitCenter()
        .into(this)
}
fun ImageView.getGlideFunctionBitmap(url: Bitmap?){
    val reqOpt = RequestOptions
        .fitCenterTransform()
        .dontAnimate().dontTransform()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
        .priority(Priority.IMMEDIATE)
        .encodeFormat(Bitmap.CompressFormat.PNG)
        .format(DecodeFormat.DEFAULT)
    // Glide is use to load image
    // from url in your imageview.
    // Glide is use to load image
    // from url in your imageview.
    Glide.with(this.context)
        .applyDefaultRequestOptions(reqOpt)
        .load(url)
        .fitCenter()
        .into(this)
}

fun setUserData(users : Data){
    realm = Realm.getDefaultInstance()
    try {
        realm.executeTransaction { realm ->
            val user : Data = realm.createObject(Data::class.java)
            user.id = users.id
            user.email = users.email
            user.first_name = users.first_name
            user.last_name = users.last_name
            user.avatar = users.avatar

        }
    }catch (ex : Exception){
        ex.printStackTrace()
    }
}

fun getUsersData(): RealmResults<Data> {
    return Realm.getDefaultInstance().where(Data::class.java).findAll()
}

fun removeUsersData() {
    realm = Realm.getDefaultInstance()
    try {
        realm.executeTransaction { realm ->
            val result: RealmResults<Data>? = realm.where(Data::class.java).findAll()
            result?.deleteAllFromRealm()
        }
    }catch (ex : Exception){
        ex.printStackTrace()
    }

}

fun getImageUri(context: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null)
    return Uri.parse(path)
}

fun bitMapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun Context.toast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}





