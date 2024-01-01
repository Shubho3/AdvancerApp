package com.tensor.example.ui.home.friendsList


import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tensor.example.R
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.databinding.FragmentFrendsBinding
import com.tensor.example.ui.base.BaseFragment
import com.tensor.example.utils.pref.SharedPrf
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class FriendsListFragment : BaseFragment<FragmentFrendsBinding, FriendsViewModel>(), View.OnClickListener, FriendsAdapter.AdapterClickListener {
    override val viewModel: FriendsViewModel by viewModels()
    private lateinit var firestore: FirebaseFirestore
    private var RoomId: String = "101"
    private var postArrayList: ArrayList<User> = arrayListOf()
    private lateinit var messageadapter1: FriendsAdapter
    private lateinit var sharedPrf: SharedPrf
    private lateinit var user: User
    override fun getLayoutResId(): Int = R.layout.fragment_frends
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_btn -> {
            }

        }
    }

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        sharedPrf = SharedPrf(requireActivity())
        user = sharedPrf.getUser("me")
        firestore = Firebase.firestore
        messageadapter1 = FriendsAdapter(postArrayList, this)
        binding.chatRecycleView.adapter = messageadapter1
        getData()
    }

    override fun setupViewModel() {

    }

    private fun getData() {
        firestore.collection("Users").orderBy("date")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireActivity(), error.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (value != null) {
                        if (!value.isEmpty) {  //değer varsa
                            val documents = value.documents
                            postArrayList.clear()
                            for (i in documents) {
                                val post = i.toObject(User::class.java)
                                if (user.id != post?.id) postArrayList.add(post!!)
                            }
                            messageadapter1.notifyDataSetChanged()
                        }

                    }
                }
            }


    }

    override fun onItemClick(model: User, int: Int) {
        sharedPrf.setUser(model, "friend")
        Navigation.findNavController(binding.root).navigate(R.id.action_friendsListFragment_to_chatFragment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDates(date1String: String, date2String: String): Int {
        val dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm:ss a zzz")

        // Parse date strings to LocalDateTime
        val date1 = LocalDateTime.parse(date1String, dateFormat)
        val date2 = LocalDateTime.parse(date2String, dateFormat)

        // Compare dates
        return when {
            date1.isBefore(date2) -> 1
            date2.isBefore(date1) -> 2
            else -> 0 // Dates are equal
        }
    }
}
