package com.example.editor.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.editor.Utils.GetHomePageChangeListener
import com.example.editor.R
import com.example.editor.ViewModel.UserViewModel
import com.example.editor.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(),
    GetHomePageChangeListener {

    private lateinit var authBinding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(authBinding.root)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)



        val navController = findNavController(R.id.nav_host_fragment)
        //navController.popBackStack(R.id.navigation_signup, true)
        navController.navigate(R.id.navigation_userList)



    }

    override fun changeFragment(s: String?) {
        val navController = findNavController(R.id.nav_host_fragment)
        if (s == "ChangeAddUser" ) {
            navController.navigate(R.id.navigation_addUsers)
        }

        if (s == "ChangeMap" ) {
            navController.navigate(R.id.navigation_map)
        }
    }
}