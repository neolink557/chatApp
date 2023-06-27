package com.example.chatapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.models.User
import com.example.chatapp.domain.GetCurrentUserUseCase
import com.example.chatapp.domain.GetDatabaseReferenceUseCase
import com.example.chatapp.utils.USERS_PATHSTRING
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _contactsList = MutableLiveData<List<User>>()
    val contactsList: LiveData<List<User>> = _contactsList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getContactsList() {
        val reference = getDatabaseReferenceUseCase(USERS_PATHSTRING)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uid = getCurrentUserUseCase()?.uid
                val list = mutableListOf<User>()

                for (data in snapshot.children) {
                    val user = data.getValue(User::class.java)
                    if (user?.userId != uid) {
                        user?.let {
                            list.add(user)
                        }
                    }
                }

                _contactsList.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                _errorMessage.postValue(error.message)
            }
        })
    }


}
