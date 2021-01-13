package com.example.myworld.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myworld.R
import com.example.myworld.databinding.FragmentEditUserProfileBinding
import com.example.myworld.viewmodel.EditUserProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditUserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditUserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editUserProfileViewModel: EditUserProfileViewModel
    private lateinit var binding: FragmentEditUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        editUserProfileViewModel = activity?.run {
            ViewModelProvider(this)[EditUserProfileViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_user_profile, container, false
        )
        binding.editUserProfileViewModel = editUserProfileViewModel//attach your viewModel to xml
        binding.lifecycleOwner = viewLifecycleOwner

//        editUserProfileViewModel.getImageList()
//            .observe(viewLifecycleOwner, Observer<List<String>> { listOfImage ->
//                Log.d("Profile Image Found: ", "${listOfImage.size}")
//            })
        editUserProfileViewModel.gender.observe(viewLifecycleOwner, Observer {
            Log.d("gender:", it.toString())
        })

        editUserProfileViewModel.dateOfBirth.observe(viewLifecycleOwner, Observer {
            Log.d("DateOfBirth:", it.toString())
        })
        return binding.root
    }

    override fun onStart() {
        //Setup User Profile Image

        //Sending User Back to Profile Fragment
        binding.backNavigationButtonEditProfile.setOnClickListener {
            val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
            ft.replace(R.id.container, ProfileFragment(), "EditProfileFragment")
            ft.commit()
        }
        super.onStart()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditUserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditUserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}