package com.example.kotlinappdrawable.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wilsonapplication.DriversActivity
import com.example.wilsonapplication.R
import kotlinx.android.synthetic.main.activity_drivers.view.*


class DriverAdapter(): RecyclerView.Adapter<DriverViewHolder>(){
    val drivers = arrayOf("Jackson","Solomon","Simon","John","Jimmy","Janet","Jane","Alex")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.item_driver,parent,false)
        return DriverViewHolder(cell, parent.context)
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
     holder.init(drivers[position])

    }

}

class DriverViewHolder(val view: View, val context: Context):RecyclerView.ViewHolder(view){
    fun init(driverName: String){
        view.drivernameTextView.text = driverName

        view.setOnClickListener {
            val intent = Intent(context, DriversActivity::class.java).apply {
                putExtra("DriverName",driverName)
            }
            startActivity(context,intent,null)
        }
    }
}