package com.example.coffeenix.fragments.client

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeenix.activities.Cliente.update.ClientUpdateActivity
import com.example.coffeenix.activities.Login
import com.example.coffeenix.databinding.FragmentClientProfileBinding
import com.example.coffeenix.models.User
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientProfileFragment : Fragment() {

    private var _binding:FragmentClientProfileBinding? = null
    private val binding get() = _binding!!
    var sharedPref: SharedPref? = null
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientProfileBinding.inflate(inflater, container, false)

        sharedPref = SharedPref(requireActivity())
        getUserFromSession()
        binding.ProfileTextViewName.text = user?.name.toString()
        binding.ProfileTextViewEmail.text = user?.email.toString()
        binding.ProfileTextViewPhone.text = user?.phone.toString()

        if (!user?.image.isNullOrBlank()) {
            Picasso.get().load(user?.image).into(binding.profileImageView);
        }

        binding.profileBtnCloseSession.setOnClickListener {
            logout()
        }

        binding.profileBtnEdit.setOnClickListener {
            goToUpdateUser()
        }



        return binding.root
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private fun goToUpdateUser(){
        val i = Intent(requireContext(), ClientUpdateActivity::class.java)
        startActivity(i)
    }

    private fun logout() {
        sharedPref?.remove("user")
        sharedPref?.remove("rol")
        val i = Intent(requireContext(), Login::class.java)
        startActivity(i)
    }
}