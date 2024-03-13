package com.example.coffeenix.adapters.clientOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coffeenix.fragments.client.ClientOrdersStatusFragment

class ClientOrdersTabsAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var nTabs: Int
): FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return nTabs
    }
    override fun createFragment(position: Int): Fragment {



        when(position){
            0 -> {
                val bundle = Bundle()
                bundle.putString("status", "PAGADO")
                val clientStatusFragment = ClientOrdersStatusFragment()
                clientStatusFragment.arguments = bundle
                return clientStatusFragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("status", "EN PROCESO")
                val clientStatusFragment = ClientOrdersStatusFragment()
                clientStatusFragment.arguments = bundle
                return clientStatusFragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("status", "EN CAMINO")
                val clientStatusFragment = ClientOrdersStatusFragment()
                clientStatusFragment.arguments = bundle
                return clientStatusFragment
            }
            3 -> {
                val bundle = Bundle()
                bundle.putString("status", "ENTREGADO")
                val clientStatusFragment = ClientOrdersStatusFragment()
                clientStatusFragment.arguments = bundle
                return clientStatusFragment
            }
            else -> return ClientOrdersStatusFragment()
        }
    }
}