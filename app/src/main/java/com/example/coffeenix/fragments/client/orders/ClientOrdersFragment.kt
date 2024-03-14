package com.example.coffeenix.fragments.client.orders

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.coffeenix.R
import com.example.coffeenix.adapters.clientOrders.ClientOrdersTabsAdapter
import com.example.coffeenix.databinding.FragmentClientOrdersBinding
import com.example.coffeenix.databinding.FragmentClientProfileBinding
import com.example.coffeenix.utils.SharedPref
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class ClientOrdersFragment : Fragment() {
    private var _binding:FragmentClientOrdersBinding? = null
    private val binding get() = _binding!!
    var viewpager: ViewPager2? = null
    var tabLayout:TabLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientOrdersBinding.inflate(inflater, container, false)

        viewpager = binding.viewPagerClient
        tabLayout = binding.tabLayoutClient

        tabLayout?.setSelectedTabIndicatorColor(Color.BLACK)
        tabLayout?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.backgroundColor))
        tabLayout?.tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.black)
        tabLayout?.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout?.isInlineLabel = true

        var numberOfTabs = 4

        val adapter = ClientOrdersTabsAdapter(requireActivity().supportFragmentManager, lifecycle, numberOfTabs )
        viewpager?.adapter = adapter
        viewpager?.isUserInputEnabled = true

        TabLayoutMediator(tabLayout!!, viewpager!!) { tab, position ->

            when(position){
                0 -> {
                    tab.text = "PAGADO"
                }
                1 -> {
                    tab.text = "EN PROCESO"
                }
                2 -> {
                    tab.text = "EN CAMINO"
                }
                3 -> {
                    tab.text = "ENTREGADO"
                }
            }
        }.attach()





        return binding.root
    }
}