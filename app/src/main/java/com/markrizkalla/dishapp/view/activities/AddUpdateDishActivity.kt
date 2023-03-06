package com.markrizkalla.dishapp.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.databinding.ActivityAddUpdateDishBinding
import com.markrizkalla.dishapp.databinding.DialogCustomImageSelectionBinding
import android.Manifest
import android.app.AlertDialog
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

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

           Dexter.withContext(this).withPermissions(
               Manifest.permission.READ_EXTERNAL_STORAGE,
               Manifest.permission.WRITE_EXTERNAL_STORAGE,
               Manifest.permission.CAMERA
           ).withListener(object :MultiplePermissionsListener{
               override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                   if (p0!!.areAllPermissionsGranted()){
                       Toast.makeText(this@AddUpdateDishActivity,"You have camera permission",Toast.LENGTH_SHORT).show()
                   }
               }
               override fun onPermissionRationaleShouldBeShown(
                   p0: MutableList<PermissionRequest>?,
                   p1: PermissionToken?
               ) {
                   // show alert dialog
               }
           }
           ).onSameThread().check()
           dialog.dismiss()
       }
        binding.tvGallery.setOnClickListener {
           Toast.makeText(this,"Gallery clicked",Toast.LENGTH_LONG).show()
            dialog.dismiss()
       }

        dialog.show()

    }
}