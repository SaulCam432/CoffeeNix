package com.example.coffeenix.fragments.client.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeenix.adapters.clientOrders.OrdersAdapter
import com.example.coffeenix.databinding.FragmentClientOrdersStatusBinding
import com.example.coffeenix.models.Order
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.OrdersProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientOrdersStatusFragment : Fragment() {

    private var _binding: FragmentClientOrdersStatusBinding? = null
    private val binding get()= _binding!!

    var ordersProvider: OrdersProvider? = null

    var sharedPref: SharedPref? = null
    var user: User? = null
    var order = ArrayList<Order>()
    var adapter : OrdersAdapter? = null

    var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientOrdersStatusBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        sharedPref = SharedPref(requireActivity())
        getUserFromSession()

        ordersProvider = OrdersProvider(user?.sessionToken!!)

        binding.recyclerViewOrdersList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewOrdersList.setHasFixedSize(true)

        getOrders()

        return binding.root
    }

    private fun getOrders(){
        status = arguments?.getString("status")!!

        ordersProvider?.getOrderByClientAndStatus(user?.id!!, status)?.enqueue(object : Callback<ArrayList<Order>> {
            override fun onResponse(call: Call<ArrayList<Order>>, response: Response<ArrayList<Order>>) {

                if (response.body() != null){

                    order = response.body()!!
                    adapter = OrdersAdapter(requireActivity(), order)
                    binding.recyclerViewOrdersList.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ArrayList<Order>>, t: Throwable) {
                messageError("Error ${t.message}")
            }

        })
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private fun messageSuccess(message: String) {
        Toast(requireContext()).showMessage(message, requireActivity(), "success")
    }

    private fun messageError(message: String){
        Toast(requireContext()).showMessage(message, requireActivity(), "error")
    }
}

