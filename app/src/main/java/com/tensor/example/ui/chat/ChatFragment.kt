package com.tensor.example.ui.chat


import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.tensor.example.utils.pref.SharedPrf
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(), View.OnClickListener {
    override val viewModel: ChatViewModel by viewModels()

    private lateinit var firestore: FirebaseFirestore
    private var friend_id: String = "0"
    private var RoomId: String = "0"
    private var postArrayList: ArrayList<Message> = arrayListOf()
    private lateinit var messageadapter1: MessageAdapter
    private lateinit var sharedPrf: SharedPrf
    private lateinit var user: User
    private lateinit var friend: User
    override fun getLayoutResId(): Int = R.layout.fragment_chat
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_btn -> {
                viewModel.isFormValid()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        var args = getArguments()
        sharedPrf = SharedPrf(requireActivity())
        user = sharedPrf.getUser("me")
        friend = sharedPrf.getUser("friend")
        friend_id = args?.getString("user").toString()
        Log.e("TAG", "initialize: " + friend.toString())
        Log.e("TAG", "initialize: " + user.toString())
        var d = user.date?.let { friend.date?.let { it1 -> compareDates(it.toDate(), it1.toDate()) } }
        if (d == 2) RoomId = friend.id.toString() + "_to_" + user.id.toString()
        else RoomId = user.id.toString() + "_to_" + friend.id.toString()

        firestore = Firebase.firestore
        messageadapter1 = user.id?.let { MessageAdapter(postArrayList, it) }!!
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
                      //  this@ChatFragment.hideKeyBoard()
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
        postMap.put("userid", user.id!!)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDates(date1String: Date, date2String: Date): Int {
        /*  val dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm:ss a zzz")

          // Parse date strings to LocalDateTime
          val date1 = LocalDateTime.parse(date1String, dateFormat)
          val date2 = LocalDateTime.parse(date2String, dateFormat)

      */    // Compare dates
        return when {
            date1String.before(date2String) -> 1
            date2String.before(date1String) -> 2
            else -> 0 // Dates are equal
        }
    }
}
