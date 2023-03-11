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
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

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
               Manifest.permission.CAMERA
           ).withListener(object :MultiplePermissionsListener{
               override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                   p0?.let {
                       if (p0.areAllPermissionsGranted()){
//                           open camera
                           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                           startActivityForResult(intent, CAMERA)
                       }
                   }
               }
               override fun onPermissionRationaleShouldBeShown(
                   p0: MutableList<PermissionRequest>?,
                   p1: PermissionToken?
               ) {
                   // show alert dialog
                   showDialogForPermissions()
               }
           }
           ).onSameThread().check()
           dialog.dismiss()
       }
        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                    // Intent to get photo from gallery
                    val galleryIntent = Intent(Intent.ACTION_PICK,
                         MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    startActivityForResult(galleryIntent, GALLERY)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showDialogForPermissions()
                }


            }).onSameThread().check()
            dialog.dismiss()
        }

        dialog.show()

    }
    private fun showDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("Permissions Required")
            .setPositiveButton("Go To SETTINGS")
            {_,_ -> try{
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package",packageName,null)
                intent.data = uri
                startActivity(intent)
            }catch (e: ActivityNotFoundException){
                e.printStackTrace()
            }

            }.setNegativeButton("Cancel"){
                dialog,_ -> dialog.dismiss()
            }.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA){
                data?.extras?.let {
                    val image : Bitmap = data.extras!!.get("data") as Bitmap
                    binding.ivDishImage.setImageBitmap(image)
                    binding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_baseline_edit))
                }

            }

            if (requestCode == GALLERY){
                data?.extras.let {
                    val selectedImageUir = data?.data

                    binding.ivDishImage.setImageURI(selectedImageUir)
                    binding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_baseline_edit))

                }
            }
        }
    }

    companion object{
        private const val CAMERA = 1
        private const val GALLERY = 1
    }

}