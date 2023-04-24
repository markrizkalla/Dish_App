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
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.markrizkalla.dishapp.application.DishApp
import com.markrizkalla.dishapp.databinding.DialogCustomListBinding
import com.markrizkalla.dishapp.model.entities.Dish
import com.markrizkalla.dishapp.utils.Constants
import com.markrizkalla.dishapp.view.adapters.CustomListItemAdapter
import com.markrizkalla.dishapp.viewmodel.DishViewModel
import com.markrizkalla.dishapp.viewmodel.DishViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOError
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding
    private var mImagePath : String = ""
    private lateinit var mCustomListDialog : Dialog

    private var mDishDetails :Dish? = null

    private val dishViewModel :DishViewModel by  viewModels { DishViewModelFactory((application as DishApp).repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpActionBar()

        if (intent.hasExtra(Constants.EXTRA_DISH_DETAILS)){

            mDishDetails = intent.getParcelableExtra(Constants.EXTRA_DISH_DETAILS)
        }


        binding.ivAddDishImage.setOnClickListener {
            customImageSelectionDialog()
        }




        binding.etType.setOnClickListener{
            customItemDialog("Dish Type",Constants.dishTypes(), Constants.DISH_TYPE)
        }
        binding.etCategory.setOnClickListener {
            customItemDialog("Dish Category",Constants.dishCategories(),Constants.DISH_CATEGORY)
        }
        binding.etCookingTime.setOnClickListener{
            customItemDialog("Select Time" , Constants.dishCookTime(),Constants.DISH_COOKING_TIME)
        }

        binding.btnAddDish.setOnClickListener {
            // trim empty spaces
            val title = binding.etTitle.text.toString().trim{ it <= ' '}
            val type = binding.etType.text.toString().trim { it <= ' ' }
            val category = binding.etCategory.text.toString().trim { it <= ' ' }
            val ingredients = binding.etIngredients.text.toString().trim { it <= ' ' }
            val cookingTimeInMinutes = binding.etCookingTime.text.toString().trim { it <= ' ' }
            val cookingDirection = binding.etDirectionToCook.text.toString().trim { it <= ' ' }

            when{
                TextUtils.isEmpty(title) -> {
                    Toast.makeText(this,"Add Title",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(type) -> {
                    Toast.makeText(this,"Add Type",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(category) -> {
                    Toast.makeText(this,"Add Category",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(ingredients) -> {
                    Toast.makeText(this,"Add Ingredients",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(cookingTimeInMinutes) -> {
                    Toast.makeText(this,"Add Cooking Time",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(cookingDirection) -> {
                    Toast.makeText(this,"Add Cooking Direction",Toast.LENGTH_SHORT).show()
                }

                else ->{
                    val dishDetails = Dish(mImagePath,Constants.DISH_IMAGE_SOURCE_LOCAL,
                        title,type,category,ingredients,cookingTimeInMinutes,cookingDirection,false)

                    dishViewModel.insert(dishDetails)
                    finish()
                }


            }
        }

    }

    private fun customImageSelectionDialog(){
         val dialog   = Dialog(this)
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

    fun selectedListItem(item: String, selection: String){
        when(selection){
            Constants.DISH_TYPE -> {
                mCustomListDialog.dismiss()
                binding.etType.setText(item)
            }
            Constants.DISH_CATEGORY -> {
                mCustomListDialog.dismiss()
                binding.etCategory.setText(item)
            }
            Constants.DISH_COOKING_TIME -> {
            mCustomListDialog.dismiss()
            binding.etCookingTime.setText(item)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA){
                data?.extras?.let {
                    val image : Bitmap = data.extras!!.get("data") as Bitmap
                    Glide.with(this).load(image).centerCrop().into(binding.ivDishImage)

                    mImagePath = saveImageToInternalStorage(image)


                   // binding.ivDishImage.setImageBitmap(image)
                    binding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_baseline_edit))
                }

            }

            if (requestCode == GALLERY){
                data?.let {
                    val selectedImageUir = data.data

                    Glide.with(this).load(selectedImageUir).centerCrop()
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL).listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed( e: GlideException?, model: Any?, target: Target<Drawable>?,
                                isFirstResource: Boolean): Boolean {
                                return false;
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?,
                                isFirstResource: Boolean): Boolean {
                                resource?.let {
                                    val bitmap:Bitmap = resource.toBitmap()
                                    mImagePath = saveImageToInternalStorage(bitmap)
                                }
                                return false
                            }

                        }).into(binding.ivDishImage)

                   // binding.ivDishImage.setImageURI(selectedImageUir)
                    binding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_baseline_edit))

                }
            }
        }else if (resultCode == Activity.RESULT_CANCELED){
            Log.e("cancelled","cancelled")
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap):String{
        val wrapper = ContextWrapper(applicationContext)
//        Image only accessible by our application
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        // create image

        try {
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }

        return file.absolutePath
    }

    private fun customItemDialog(title: String, itemsList:List<String>,selection: String){
         mCustomListDialog = Dialog(this)
        val binding:DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text = title

        binding.rvList.layoutManager = LinearLayoutManager(this)
        val adapter = CustomListItemAdapter(this,itemsList,selection)
        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }


    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarAddDishActivity)

        if (mDishDetails != null && mDishDetails!!.id != 0){
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_editDish)
            }
        }else{
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_addDish)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    companion object{
        private const val CAMERA = 1
        private const val GALLERY = 2

        private const val IMAGE_DIRECTORY="DishImages"
    }

}