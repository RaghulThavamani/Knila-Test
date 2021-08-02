package com.example.editor.UI

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.editor.databinding.AddUsersFragmentBinding

import android.content.Intent
import android.provider.MediaStore

import android.app.Activity.RESULT_OK
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import com.example.editor.*
import com.example.editor.ViewModel.AddUsersViewModel
import com.example.editor.abstarctClass.UserData
import com.example.editor.model.Data
import io.realm.Realm.getApplicationContext
import java.io.IOException


class AddUsersFragment : Fragment() {

    private lateinit var viewModel: AddUsersViewModel
    private lateinit var viewBind: AddUsersFragmentBinding
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    var image : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AddUsersViewModel::class.java)
        viewBind = AddUsersFragmentBinding.inflate(layoutInflater)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBind.profilePic.setOnClickListener {
           chooseImage()
        }

        viewBind.save.setOnClickListener {
            if (UserData.isIsEdit()) {
                requireContext().toast("Edit not Implemented!")
            } else {
                saveToRealm()
            }

        }

        val user = UserData.getUser()
        if (user != null && !UserData.isIsEdit()) {
            viewBind.addUserLayout.visibility = View.GONE
            viewBind.viewUserLayout.visibility = View.VISIBLE
            viewBind.topBarTitle.text = "Profile"
            viewBind.name.text = "${user.first_name} ${user.last_name}"
            viewBind.idTv.text = user.id.toString()
            viewBind.emailTv.text = user.email
            if (user.id == 999) {
                val imageBytes: ByteArray = Base64.decode(user.avatar, 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                viewBind.profilePic.getGlideFunctionBitmap(image)
            } else {
                user.avatar?.let { viewBind.profilePic.getGlideFunction(it) }
            }

        }
        else if (UserData.isIsEdit()) {
            viewBind.topBarTitle.text = "Edit User"
            viewBind.addUserLayout.visibility = View.VISIBLE
            viewBind.viewUserLayout.visibility = View.GONE
            viewBind.firstName.setText(user.first_name)
            viewBind.lastName.setText(user.last_name)
            viewBind.email.setText(user.email)
            if (user.id == 999) {
                val imageBytes: ByteArray = Base64.decode(user.avatar, 0)
                val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                viewBind.profilePic.getGlideFunctionBitmap(image)
            } else {
                user.avatar?.let { viewBind.profilePic.getGlideFunction(it) }
            }

        }
        else {
            viewBind.topBarTitle.text = "Add User"
            viewBind.addUserLayout.visibility = View.VISIBLE
            viewBind.viewUserLayout.visibility = View.GONE
        }

    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    getApplicationContext()?.contentResolver,
                    filePath
                )
                viewBind.profilePic.setImageBitmap(bitmap)
                image = ""
                image = bitMapToString(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveToRealm() {
        if (TextUtils.isEmpty(viewBind.firstName.text.toString())) {
            requireContext().toast("Enter First Name")
            return
        }

        if (TextUtils.isEmpty(viewBind.lastName.text.toString())) {
            requireContext().toast("Enter Last Name")
            return
        }

        if (TextUtils.isEmpty(viewBind.email.text.toString())) {
            requireContext().toast("Enter Email")
            return
        }

        if (TextUtils.isEmpty(image)) {
            requireContext().toast("Add Image")
            return
        }

        val user = Data()
        user.first_name = viewBind.firstName.text.toString()
        user.last_name = viewBind.lastName.text.toString()
        user.email = viewBind.email.text.toString()
        user.avatar = image
        user.id = 999

        setUserData(user)


        viewBind.firstName.setText("")
        viewBind.lastName.setText("")
        viewBind.email.setText("")
        viewBind.profilePic.setImageBitmap(null)
        filePath = null
        image = ""

        requireContext().toast("User Added Successfully!")
    }

}