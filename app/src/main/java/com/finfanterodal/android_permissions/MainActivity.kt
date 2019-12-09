package com.finfanterodal.android_permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast

const val MY_PERMISSIONS_REQUEST_READ_CALL = 1
const val MY_PERMISSIONS_CAMERA = 2
const val REQUEST_IMAGE_CAPTURE = 3


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toast = Toast.makeText(applicationContext, " Permission is not granted", 10)


        //Permiso de llamada
        bLlamada.setOnClickListener {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                // Permission is not granted
                toast.setText("No tiene permiso para hacer una llamada.")
                toast.show()
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_READ_CALL
                    )

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                toast.setText("Ya tiene este permiso.")
                toast.show()
                // Permission has already been granted
            }

        }

        //Permiso de cámara
        bCamara.setOnClickListener {
            //  Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                // Permission is not granted
                toast.setText("No tiene este permiso para acceder a la Camara.")
                toast.show()
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        MY_PERMISSIONS_CAMERA
                    )

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                dispatchTakePictureIntent()
                toast.setText("Ya tiene este permiso.")
                toast.show()
                // Permission has already been granted
            }

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            //Permiso de llamada
            MY_PERMISSIONS_REQUEST_READ_CALL -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    longToast(
                        "PERMISO LLAMADA ACEPTADO"
                    )

                } else {

                    longToast(
                        "PERMISO LLAMADA DENEGADO"
                    )
                }
                return
            }
            //Premiso de camara
            MY_PERMISSIONS_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    longToast(
                        "PERMISO CAMARA ACEPTADO"
                    )

                } else {

                    longToast(
                        "PERMISO CAMARA DENEGADO"
                    )
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }

    }

    //Lanzamos la Aplicacióin de la cámara
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    //Al cerrar la camara nos devuelve una imagen, y llama a este método en el que la  recogemos
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }

    }

}


