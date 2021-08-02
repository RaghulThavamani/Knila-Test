package com.example.editor.UI

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.editor.*
import com.example.editor.Adapters.ChatListAdapters
import com.example.editor.Utils.Coroutine
import com.example.editor.abstarctClass.UserData
import com.example.editor.databinding.UsersListFragmentBinding
import com.example.editor.model.Data
import java.lang.Exception
import android.graphics.Canvas
import android.R

import android.graphics.drawable.ColorDrawable
import com.example.editor.Utils.GetHomePageChangeListener
import com.example.editor.ViewModel.UsersListViewModel


class UsersListFragment : Fragment() {


    var userOverall = mutableListOf<Data>()
    private lateinit var viewModel: UsersListViewModel
    private lateinit var viewBind : UsersListFragmentBinding
    var chatListAdapters = ChatListAdapters()
    var callback : GetHomePageChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersListViewModel::class.java)
        viewBind = UsersListFragmentBinding.inflate(layoutInflater)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBind.addUsers.setOnClickListener {
            if (context is GetHomePageChangeListener) {
                callback = context as GetHomePageChangeListener
            }
            callback?.changeFragment("ChangeAddUser")
            UserData.setUser(null)
            UserData.setIsEdit(false)
        }

        viewBind.floatingActionButton.setOnClickListener {
            if (context is GetHomePageChangeListener) {
                callback = context as GetHomePageChangeListener
            }
            callback?.changeFragment("ChangeMap")
            UserData.setUser(null)
        }

        setAdapters()
        getUsers()
        setUsers()

        val lay =  object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
           private val background = ColorDrawable(resources.getColor(R.color.darker_gray))
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (context is GetHomePageChangeListener) {
                    callback = context as GetHomePageChangeListener
                }
                callback?.changeFragment("ChangeAddUser")
                UserData.setUser(userOverall[viewHolder.adapterPosition])
                UserData.setIsEdit(true)
            }

           override fun onChildDraw(
               c: Canvas,
               recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               dX: Float,
               dY: Float,
               actionState: Int,
               isCurrentlyActive: Boolean
           ) {
               super.onChildDraw(
                   c,
                   recyclerView,
                   viewHolder,
                   dX,
                   dY,
                   actionState,
                   isCurrentlyActive
               )
               val itemView = viewHolder.itemView

               if (dX > 0) {
                   background.setBounds(
                       itemView.left,
                       itemView.top,
                       itemView.left + dX.toInt(),
                       itemView.bottom
                   )
               } else if (dX < 0) {
                   background.setBounds(
                       itemView.right + dX.toInt(),
                       itemView.top,
                       itemView.right,
                       itemView.bottom
                   )
               } else {
                   background.setBounds(0, 0, 0, 0)
               }

               background.draw(c)
           }
        }
        val itemTouchHelper = ItemTouchHelper(lay)
        itemTouchHelper.attachToRecyclerView(viewBind.recUsers)


    }

    private fun setAdapters() {
        viewBind.recUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBind.recUsers.itemAnimator = DefaultItemAnimator()
        viewBind.recUsers.adapter = chatListAdapters
    }

    private fun getUsers() {
        Coroutine.main {
            try {
                viewModel.getUsers()
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun setUsers() {
        viewModel.users.observe(viewLifecycleOwner, {
                usersList -> if (usersList != null) {
            userOverall.clear()
            userOverall.addAll(usersList.data)
            val users = getUsersData()
            if (users.size != 0) {
                userOverall.addAll(users)
            }
            chatListAdapters.setChatLists(userOverall)
            chatListAdapters.notifyDataSetChanged()
        }
        })

    }

}