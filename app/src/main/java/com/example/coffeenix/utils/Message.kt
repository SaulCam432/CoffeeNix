package com.example.coffeenix.utils

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.coffeenix.R

fun Toast.showMessage(message: String, activity: Activity, type: String) {
    //SE IDENTIFICA LA PANTALLA DEL MENSAJE
    val layout = activity.layoutInflater.inflate (
        R.layout.custom_message,
        activity.findViewById(R.id.backg)
    )

    //SE ESPECIFICA EL MENSAJE
    val textView = layout.findViewById<TextView>(R.id.tVMessage)
    textView.text = message

    if (type == "success"){
        //SE ESPECIFICA EL ICONO
        val icon = layout.findViewById<ImageView>(R.id.iVMessage)
        icon.setImageResource(R.drawable.ic_success)

        //SE ESPECIFICA EL COLOR DE FONDO
        val colorBG = layout.findViewById<LinearLayout>(R.id.backg)
    colorBG.setBackgroundResource(R.drawable.message_success)

    } else if (type == "error"){
        //SE ESPECIFICA EL ICONO
        val icon = layout.findViewById<ImageView>(R.id.iVMessage)
        icon.setImageResource(R.drawable.ic_error)

        //SE ESPECIFICA EL COLOR DE FONDO
        val colorBG = layout.findViewById<LinearLayout>(R.id.backg)
        colorBG.setBackgroundResource(R.drawable.message_error)
    }

    //SE DEFINE LA POSICION Y DURACION DEL MENSAJE
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}

fun Toast.showError(message: String, activity: Activity) {
    val layout = activity.layoutInflater.inflate(
        R.layout.custom_message,
        activity.findViewById(R.id.backg)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.tVMessage)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}
