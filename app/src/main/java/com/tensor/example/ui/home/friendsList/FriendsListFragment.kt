package com.tensor.example.ui.home.friendsList


import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tensor.example.R
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.databinding.FragmentChatBinding
import com.tensor.example.ui.base.BaseFragment
import com.tensor.example.utils.extension.hideKeyBoard
import com.tensor.example.utils.extension.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsListFragment : BaseFragment<FragmentChatBinding, FriendsViewModel>(), View.OnClickListener {
    override val viewModel: FriendsViewModel by viewModels()

    private lateinit var firestore: FirebaseFirestore
    private var RoomId: String = "101"
    private var postArrayList: ArrayList<User> = arrayListOf()
    private lateinit var messageadapter1: FriendsAdapter
    override fun getLayoutResId(): Int = R.layout.fragment_chat
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_btn -> {
            }
        }
    }

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        firestore = Firebase.firestore
        messageadapter1 = FriendsAdapter(postArrayList, "2")
        binding.chatRecycleView.adapter = messageadapter1
      //  binding.chatRecycleView.scrollToPosition(postArrayList.size - 1)
        getData()
        //auth = Firebase.auth
    }

    override fun setupViewModel() {

    }

    private fun getData() {
        firestore.collection(RoomId).orderBy("date").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireActivity(), error.localizedMessage, Toast.LENGTH_LONG).show()

            } else {
                if (value != null) {
                    if (!value.isEmpty) {  //değer varsa
                        val documents = value.documents
                        postArrayList.clear()
                        for (i in documents) {
                            val yourmessage = i.get("your_message") as String
                            val userid = i.get("userid").toString()
                            val post = User(yourmessage, userid)
                            println(yourmessage)
                            println(userid)
                            postArrayList.add(post)
                        }
                        messageadapter1.notifyDataSetChanged()
                        binding.chatRecycleView.scrollToPosition(postArrayList.size - 1)


                    }

                }
            }
        }


    }
}
