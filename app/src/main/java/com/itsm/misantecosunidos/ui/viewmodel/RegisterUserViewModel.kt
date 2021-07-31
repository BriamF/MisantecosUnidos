package com.itsm.misantecosunidos.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.domain.*
import com.itsm.misantecosunidos.ui.view.Register
import kotlinx.coroutines.launch

class RegisterUserViewModel: ViewModel(){

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

    fun registerNewUser(user: UserModel){
        if (validateNewUser(user)){
            statusMessage.value = Event("Usuario existente, intenta ingresando otro correo...")
        } else if (imgUrl!=null){
            user.img = imgUrl.toString()
            val addUsersUseCase = AddUserUseCase(user)
            addUsersUseCase()
            statusMessage.value = Event("Usuario guardado")
            statusImageText.value = "Seleccionar Imagen"
        } else if(user.name == "" || user.email == "" || user.password == "") {
            statusMessage.value = Event("Ingresa los valores de todos los campos!")
        } else {
            statusMessage.value = Event("Es necesario subir una imagen antes de registrar un usuario!")
        }
    }

    private fun validateNewUser(user: UserModel): Boolean {
        val getUserIdUserUseCase = GetUserIdUseCase(user.email)
        val getBusinessIdUseCase = GetBusinessIdUseCase(user.email)

        return getUserIdUserUseCase() != null || getBusinessIdUseCase() != null
    }


    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("User")

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
                    FirebaseStorage.getInstance().getReference().child("Users")
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