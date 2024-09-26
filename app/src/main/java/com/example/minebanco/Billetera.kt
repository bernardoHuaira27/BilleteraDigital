package com.example.minebanco

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Billetera : AppCompatActivity() {


    private var saldo = 0.0
    private var accion = ""
    private val operaciones = mutableListOf<String>()
    private lateinit var textSaldo: TextView
    private lateinit var textOperaciones: TextView
    private lateinit var inputCantidad: EditText
    private lateinit var btnAceptar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_billetera)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Vista
        textSaldo = findViewById(R.id.tvSaldo)
        textOperaciones = findViewById(R.id.tvOperaciones)
        inputCantidad = findViewById(R.id.etCantidad)
        btnAceptar = findViewById(R.id.btnAceptar)

        //Botones
        val btnMostrarSaldo = findViewById<Button>(R.id.btnMostrarSaldo)
        val btnIngresarDinero = findViewById<Button>(R.id.btnIngresarDinero)
        val btnRetirarDinero = findViewById<Button>(R.id.btnRetirarDinero)
        val btnMostrarOperaciones = findViewById<Button>(R.id.btnMostrarOperaciones)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnMostrarSaldo.setOnClickListener {
            mostrarSaldo()
        }

        btnIngresarDinero.setOnClickListener {
            accion = "ingresar"
            mostrarInput()
        }

        btnRetirarDinero.setOnClickListener {
            accion = "retirar"
            mostrarInput()
        }

        btnMostrarOperaciones.setOnClickListener {
            mostrarOperaciones()
        }

        btnSalir.setOnClickListener {
            finish()
        }

        btnAceptar.setOnClickListener {
            realizarAccion()
        }
    }

    private fun mostrarSaldo() {
        textSaldo.text = "Saldo actual: $${String.format("%.2f", saldo)}"
    }

    private fun mostrarInput() {
        inputCantidad.visibility = View.VISIBLE
        btnAceptar.visibility = View.VISIBLE
        inputCantidad.text.clear()
    }

    private fun realizarAccion() {
        val cantidad = inputCantidad.text.toString().toDoubleOrNull()
        if (cantidad == null || cantidad <= 0) {
            Toast.makeText(this, "Ingrese una cantidad válida.", Toast.LENGTH_SHORT).show()
            return
        }

        when (accion) {
            "ingresar" -> {
                saldo += cantidad
                operaciones.add("Ingresaste $$cantidad")
                mostrarMensaje("Se ingresó $$cantidad")
            }
            "retirar" -> {
                if (cantidad > saldo) {
                    Toast.makeText(this, "Saldo insuficiente.", Toast.LENGTH_SHORT).show()
                } else {
                    saldo -= cantidad
                    operaciones.add("Retiraste $$cantidad")
                    mostrarMensaje("Se retiró $$cantidad")
                }
            }
        }

        mostrarSaldo()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarOperaciones() {
        if (operaciones.isEmpty()) {
            textOperaciones.text = "No hay operaciones registradas."
        } else {
            textOperaciones.text = operaciones.joinToString("\n")
        }
        Log.d("Billetera", "Operaciones mostradas: $textOperaciones.text")
    }

}
