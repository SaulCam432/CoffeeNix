package com.example.coffeenix.fragments.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeenix.activities.Cliente.update.ClientUpdateActivity
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.shoppingBag.ClientShoppingBagActivity
import com.example.coffeenix.activities.Login
import com.example.coffeenix.adapters.categories.CategoriesAdapter
import com.example.coffeenix.adapters.roles.RolesAdapter
import com.example.coffeenix.databinding.FragmentClientCategoriesBinding
import com.example.coffeenix.databinding.FragmentClientProfileBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.CategoriesProvider
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientCategoriesFragment : Fragment() {

    private var _binding: FragmentClientCategoriesBinding? = null
    private val binding get() = _binding!!

    var sharedPref: SharedPref? = null
    var adapter: CategoriesAdapter? = null
    var categoriesProvider: CategoriesProvider? = null
    var user: User? = null
    var categories = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientCategoriesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        sharedPref = SharedPref(requireActivity())
        getUserFromSession()
        categoriesProvider = CategoriesProvider(user?.sessionToken!!)

        var toolbar = binding.toolbar.toolbar

        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        toolbar.setTitle(R.string.clientCategoriesToolbarTitle)
        toolbar.setTitleTextAppearance(requireContext(), R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        getCategories()
        return binding.root
    }

    private fun getCategories() {
        categoriesProvider?.getAll()?.enqueue(object: Callback<ArrayList<Category>> {
            override fun onResponse(call: Call<ArrayList<Category>>, response: Response<ArrayList<Category>>) {

                if (response.body() != null) {

                    categories = response.body()!!
                    binding.recyclerViewCategories.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerViewCategories.setHasFixedSize(true)
                    adapter = CategoriesAdapter(requireActivity(), categories)
                    binding.recyclerViewCategories.adapter = adapter
                }

            }

            override fun onFailure(call: Call<ArrayList<Category>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun goToShoppingBag(){
        val i = Intent(requireContext(), ClientShoppingBagActivity::class.java)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_shopping_bag, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_shopping_bag) {
            goToShoppingBag()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

}