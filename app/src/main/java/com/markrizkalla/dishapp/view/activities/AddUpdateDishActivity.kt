package com.markrizkalla.dishapp.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.databinding.ActivityAddUpdateDishBinding
import com.markrizkalla.dishapp.databinding.DialogCustomImageSelectionBinding

class AddUpdateDishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateDishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivAddDishImage.setOnClickListener {
            customImageSelectionDialog()
        }
    }

    private fun customImageSelectionDialog(){
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

       binding.tvCamera.setOnClickListener {
           Toast.makeText(this,"Camera clicked",Toast.LENGTH_LONG).show()
           dialog.dismiss()
       }
        binding.tvGallery.setOnClickListener {
           Toast.makeText(this,"Gallery clicked",Toast.LENGTH_LONG).show()
            dialog.dismiss()
       }


        dialog.show()


    }
}