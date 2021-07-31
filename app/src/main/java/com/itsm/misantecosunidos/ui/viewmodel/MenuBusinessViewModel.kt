package com.itsm.misantecosunidos.ui.viewmodel

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itsm.misantecosunidos.data.model.Application.Companion.prefs
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.data.model.ProductProvider
import com.itsm.misantecosunidos.domain.AddProductUseCase
import com.itsm.misantecosunidos.domain.DeleteProductUseCase
import com.itsm.misantecosunidos.domain.GetProductsUseCase
import com.itsm.misantecosunidos.ui.view.Login
import kotlinx.coroutines.launch

class MenuBusinessViewModel: ViewModel() {

    init {
        val getProductsUseCase = GetProductsUseCase(prefs.getEmail())

        viewModelScope.launch {
            val result = getProductsUseCase()

            if(!result.isNullOrEmpty()){
                productList.postValue(result!!)
                ProductProvider.productsList.observeForever {
                    productList.postValue(it)
                }
            }
        }


    }

    fun logOut(context: Context) {
        statusMessage.value = Event("¡Hasta luego!")
        prefs.clearPreferences()
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
    }

    private var imgUrl: String? = null

    private val statusImageText = MutableLiveData<String>()
    val textImage: LiveData<String>
        get() = statusImageText

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private var productList = MutableLiveData<List<ProductModel>>()
    val listProduct: LiveData<List<ProductModel>>
        get() = productList

    fun saveNewProduct(product: ProductModel){
        if (imgUrl!=null){
            product.img = imgUrl.toString()
            val addProductUseCase = AddProductUseCase(prefs.getEmail(), product)
            addProductUseCase()
            statusMessage.value = Event("Producto guardado")
            statusImageText.value = "Seleccionar Imagen"
        } else if(product.name == "" || product.price == null) {
            statusMessage.value = Event("Ingresa los valores de todos los campos!")
        } else {
            statusMessage.value = Event("Es necesario subir una imagen antes de registrar un producto!")
        }
    }

    fun deleteProduct(context: Context, nameProduct: String) {
        AlertDialog.Builder(context).apply{
            setTitle("Eliminar producto: ${nameProduct}")
            setMessage("¿Estás seguro de eliminar el producto de forma permanente?")
            setPositiveButton("Si"){ _: DialogInterface, _: Int ->
                val deleteProductUseCase = DeleteProductUseCase(prefs.getEmail(), nameProduct)
                deleteProductUseCase()
            }
            setNegativeButton("No",null)
        }.show()
    }

    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("Product")

    fun subirImagen(activity: Activity){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        activity.startActivityForResult(intent, File)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == File) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("Products")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                println(file_name)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        this.imgUrl = java.lang.String.valueOf(uri)
                        Log.d("Mensaje", "Se subió correctamente")
                        statusMessage.value = Event("Imagen subida correctamente!")
                        statusImageText.value = "Imagen seleccionada"
                    }
                }
            }
        }
    }
}