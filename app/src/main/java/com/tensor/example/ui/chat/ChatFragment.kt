package com.tensor.example.ui.chat


import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tensor.example.R
import com.tensor.example.databinding.FragmentChatBinding
import com.tensor.example.ui.base.BaseFragment
import com.tensor.example.utils.extension.hideKeyBoard
import com.tensor.example.utils.extension.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(), View.OnClickListener {
    override val viewModel: ChatViewModel by viewModels()

    private lateinit var firestore: FirebaseFirestore
    private var RoomId: String = "101"
    private var postArrayList: ArrayList<Message> = arrayListOf()
    private lateinit var messageadapter1: MessageAdapter
    override fun getLayoutResId(): Int = R.layout.fragment_chat
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_btn -> {
                viewModel.isFormValid()
            }
        }
    }

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        firestore = Firebase.firestore
        messageadapter1 = MessageAdapter(postArrayList, "2")
        binding.chatRecycleView.adapter = messageadapter1
        binding.chatRecycleView.scrollToPosition(postArrayList.size - 1)
        getData()
        //auth = Firebase.auth
    }

    override fun setupViewModel() {
        viewModel.formValidationLiveData.observe(this) { message ->
            message.run {
                when {
                    isMsgError -> {
                        showError(msg)
                        binding.yourMessage.requestFocus()
                    }

                    formIsValid != 0 -> {
                        //  this@LoginActivity.hideKeyBoard()
                        //  onLoginClick()
                        this@ChatFragment.hideKeyBoard()
                        viewModel.msg.value?.let { sendMessage(it) }
                    }

                    else -> {
                        this@ChatFragment.hideKeyBoard()
                    }
                }
            }
        }
    }

    private fun sendMessage(msg: String) {
        val postMap = hashMapOf<String, Any>()
        postMap.put("your_message", msg)
        postMap.put("userid", "2")
        postMap.put("date", com.google.firebase.Timestamp.now())
        firestore.collection(RoomId).add(postMap).addOnSuccessListener {
            viewModel.msg.value = ""
        }.addOnFailureListener {
            Toast.makeText(requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
        binding.chatRecycleView.scrollToPosition(postArrayList.size - 1)

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
                            val post = Message(yourmessage, userid)
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
