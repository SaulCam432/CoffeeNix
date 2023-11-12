package com.example.coffeenix.activities.Cliente.products.details

import android.R.attr.banner
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.coffeenix.databinding.ActivityClientProductDetailBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson


class ClientProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientProductDetailBinding
    val TAG = "ProductDetail"
    var product: Product? = null
    val gson = Gson()

    var counter = 1
    var productPrice = 0.0

    var sharedPref: SharedPref? = null
    var selectProduct = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = gson.fromJson(intent.getStringExtra("product"), Product::class.java)
        sharedPref = SharedPref(this)

        binding.textProductName.text = product?.name
        binding.textProductDescription.text = product?.description
        binding.textProductPrice.text = "${product?.price}$"

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(product?.image1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image2, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image3, ScaleTypes.CENTER_CROP))

        binding.imageProductSlider.setImageList(imageList)


    }


    private fun addItem() {
        counter++
        productPrice = product?.price!! * counter
        product?.quantity = counter
        binding.textProductCounter.text = "${product?.quantity}"
        binding.textProductPrice.text = "${productPrice}$"
    }

    private fun removeItem() {
        if (counter > 1) {
            counter--
            productPrice = product?.price!! * counter
            product?.quantity = counter
            binding.textProductCounter.text = "${product?.quantity}"
            binding.textProductPrice.text = "${productPrice}$"
        }
    }
}