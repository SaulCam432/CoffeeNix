package com.example.coffeenix.activities.Cliente.products.details

import android.R.attr.banner
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.databinding.ActivityClientProductDetailBinding
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ClientProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: com.example.coffeenix.databinding.ActivityClientProductDetailBinding
    val TAG = "ProductDetail"
    var product: Product? = null
    val gson = Gson()

    var counter = 1
    var productPrice = 0.0

    var sharedPref: SharedPref? = null
    var selectedProduct = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = gson.fromJson(intent.getStringExtra("product"), Product::class.java)
        sharedPref = SharedPref(this)

        binding.textProductName.text = product?.name
        binding.textProductDescription.text = product?.description
        binding.textProductPrice.text = "$ ${product?.price}"

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(product?.image1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image2, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image3, ScaleTypes.CENTER_CROP))

        binding.imageProductSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)

        binding.imageProductSlider.setImageList(imageList)

        binding.imageviewProductAdd.setOnClickListener { addItem() }
        binding.imageviewProductRemove.setOnClickListener { removeItem() }

        binding.clientProductDetailAddBag.setOnClickListener { addToBag() }

        getProductFromSharedPref()
    }

    private fun addToBag() {
        val index = getIndexOf(product?.id!!) //INDICE DEL PRODUCTO SI EXISTE EN SHARED PREF

        if (index == -1) { //NO HAY PRODUCTO EN SHARED PREF
            if (product?.quantity == 0){
                product?.quantity = 1
            }
            selectedProduct.add(product!!)
        }
        else { // YA EXISTE EL PRODUCTO EN SHARED PREF
            selectedProduct[index].quantity = counter
        }

        sharedPref?.save("order", selectedProduct)
        messageSuccess("Producto agregado")

    }

    private fun getProductFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) {
            val type = object : TypeToken<ArrayList<Product>>() {}.type
            selectedProduct = gson.fromJson(sharedPref?.getData("order"), type)
            val index = getIndexOf(product?.id!!)

            if (index != -1) {
                product?.quantity = selectedProduct[index].quantity
                binding.textProductCounter.text = "${product?.quantity}"
                productPrice = product?.price!! * product?.quantity!!
                binding.textProductPrice.text = "$ ${productPrice}"
                binding.clientProductDetailAddBag.setText("Editar producto")
            }

            for (p in selectedProduct) {
                Log.d("ProductsDetail", "SharedPref: $p")
            }
        }
    }

    //ESRA FUNCION ES PARA VERIFICAR SI EXISTE EN SAHRED PREF UN PRODUCTO Y DE ESA MANERA PODER EDITARLO
    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for(p in selectedProduct){
            if (p.id == idProduct){
                return pos
            }
            pos++
        }

        return -1
    }

    private fun addItem() {
        counter++
        productPrice = product?.price!! * counter
        product?.quantity = counter
        binding.textProductCounter.text = "${product?.quantity}"
        binding.textProductPrice.text = "$ ${productPrice}"
    }

    private fun removeItem() {
        if (counter > 1) {
            counter--
            productPrice = product?.price!! * counter
            product?.quantity = counter
            binding.textProductCounter.text = "${product?.quantity}"
            binding.textProductPrice.text = "$ ${productPrice}$"
        }
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}