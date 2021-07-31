package com.itsm.misantecosunidos.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.domain.AddBusinessUseCase
import com.itsm.misantecosunidos.ui.view.Register

class RegisterBusinessViewModel: ViewModel() {
    private val statusImageText = MutableLiveData<String>()
    val textImage: LiveData<String>
        get() = statusImageText

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private var imgUrl: String? = null

    fun registerMain(context : Context){
        val intent = Intent(context, Register::class.java)
        context.startActivity(intent)
    }

    fun registerNewBusiness(business: BusinessModel){
        if (imgUrl!=null){
            business.img = imgUrl.toString()
            val addBusinessUseCase = AddBusinessUseCase(business)
            addBusinessUseCase()
            statusMessage.value = Event("Negocio guardado")
            statusImageText.value = "Seleccionar Imagen"
        } else if(business.name == "" || business.email == "" || business.password == "" || business.address == "" || business.phoneNumber == "") {
            statusMessage.value = Event("Ingresa los valores de todos los campos!")
        } else {
            statusMessage.value = Event("Es necesario subir una imagen antes de registrar un negocio!")
        }
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
                    FirebaseStorage.getInstance().getReference().child("Business")
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