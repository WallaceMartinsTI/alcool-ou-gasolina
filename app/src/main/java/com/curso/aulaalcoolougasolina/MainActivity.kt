package com.curso.aulaalcoolougasolina

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var textInputAlcool: TextInputLayout
    private lateinit var editAlcool: TextInputEditText

    private lateinit var textInputGasolina: TextInputLayout
    private lateinit var editGasolina: TextInputEditText

    private lateinit var btnCalcular: Button
    private lateinit var textResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarComponentesInterface()

        btnCalcular.setOnClickListener {
            calcularMelhorPreco()
        }
    }

    private fun calcularMelhorPreco() {
        val precoAlcool = editAlcool.text.toString()
        val precoGasolina = editGasolina.text.toString()

        val resultadoValidacao = validarCampos(precoAlcool, precoGasolina)
        if (resultadoValidacao) {
            /*
            Cálculo de melhor preço
            se (valorAlcool / valorGasolina) >= 0.7 é melhor utilizar gasolina
            se não é melhor utilizar álcool
            */
            val precoAlcoolNumero = precoAlcool.toDouble()
            val precoGasolinaNumero = precoGasolina.toDouble()
            val resultado = precoAlcoolNumero / precoGasolinaNumero

            if(resultado >= 0.7) {
                textResultado.text = "Melhor utilizar Gasolina"
            } else {
                textResultado.text = "Melhor utilizar Álcool"
            }

            // Minha implementação para esconder o teclado após clicar no botão "Calcular"
            // E focar no campo que ocorreu o erro e abrir o teclado automaticamente
            toggleKeyboard("CLOSE")
        }
    }

    private fun toggleKeyboard(option: String = "OPEN") {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if (option == "CLOSE") {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            } else {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }

        }
    }

    private fun validarCampos(pAlcool: String, pGasolina: String): Boolean {
        textInputAlcool.error = null
        textInputGasolina.error = null

        if (pAlcool.isEmpty()) {
            textInputAlcool.error = "Digite o preço do álcool"
            textInputAlcool.requestFocus()
            toggleKeyboard()
            return false
        } else if (pGasolina.isEmpty()) {
            textInputGasolina.error = "Digite o preço da gasolina"
            textInputGasolina.requestFocus()
            toggleKeyboard()
            return false
        }

        return true
    }

    private fun inicializarComponentesInterface() {
        textInputAlcool = findViewById(R.id.text_input_alcool)
        editAlcool = findViewById(R.id.edit_alcool)

        textInputGasolina = findViewById(R.id.text_input_gasolina)
        editGasolina = findViewById(R.id.edit_gasolina)

        btnCalcular = findViewById(R.id.btn_calcular)
        textResultado = findViewById(R.id.text_resultado)
    }
}