package mx.tecnm.tepic.ladm_u4_tarea1_contentproviders

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val siPermiso = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG), siPermiso)
        }

        btnLlamadasRecibidas.setOnClickListener {
            ObtenerLlamadasRecibidas()
        }
        btnLlamadasRealizadas.setOnClickListener {
            ObtenerLlamadasRealizadas()
        }
    }

    private fun ObtenerLlamadasRealizadas() {
        var cursorRealizadas = contentResolver.query(Uri.parse("content://call_log/calls"), null, null, null, null)
        var resultado = ""
        if(cursorRealizadas!!.moveToFirst()){
            var tipo = cursorRealizadas.getColumnIndex(CallLog.Calls.TYPE)
            var numero = cursorRealizadas.getColumnIndex(CallLog.Calls.NUMBER)
            var nombre = cursorRealizadas.getColumnIndex(CallLog.Calls.CACHED_NAME)
            do {
                if(cursorRealizadas.getInt(tipo) == 2) {
                    resultado += "Tipo: " + cursorRealizadas.getString(tipo) +
                            "\n Numero: " + cursorRealizadas.getString(numero) + " " +
                            "\n Nombre: " + cursorRealizadas.getString(nombre) + "\n-----------------\n"
                }
            }while (cursorRealizadas.moveToNext())
        }else{
            resultado = "No hay llamadas realizadas"
        }
        txtHistorialRealizadas.setText(resultado)
    }

    fun ObtenerLlamadasRecibidas(){
        var cursorRecibidas = contentResolver.query(Uri.parse("content://call_log/calls"), null, null, null, null)
        var resultado = ""
        if(cursorRecibidas!!.moveToFirst()){
            var tipo = cursorRecibidas.getColumnIndex(CallLog.Calls.TYPE)
            var numero = cursorRecibidas.getColumnIndex(CallLog.Calls.NUMBER)
            var duracion = cursorRecibidas.getColumnIndex(CallLog.Calls.DURATION)
            do {
                if(cursorRecibidas.getInt(tipo) == 1) {
                    resultado += "Tipo: " + cursorRecibidas.getString(tipo) +
                            "\n Numero: " + cursorRecibidas.getString(numero) + " " +
                            "\n Duración: " + cursorRecibidas.getString(duracion) + "\n-----------------\n"
                }
            }while (cursorRecibidas.moveToNext())
        }else{
            resultado = "No hay llamadas recibidas"
        }
        txtHistorialRecibidas.setText(resultado)
    }
}