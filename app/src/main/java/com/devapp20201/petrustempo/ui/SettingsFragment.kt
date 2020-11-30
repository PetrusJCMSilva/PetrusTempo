package com.devapp20201.petrustempo.ui


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup


import com.devapp20201.petrustempo.R

class SettingsFragment : Fragment() {

    private lateinit var prefs: SharedPreferences

    private lateinit var rgTemp : RadioGroup
    private lateinit var rgLg : RadioGroup

    private var temperatureUnit = ""
    private var language = ""

    private lateinit var rbC: RadioButton
    private lateinit var rbF: RadioButton

    private lateinit var rbEN: RadioButton
    private lateinit var rbPT: RadioButton

    private lateinit var btnSave: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       return  inflater.inflate(R.layout.fragment_settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = view.context.getSharedPreferences("tempo_prefs", 0)

        btnSave = view.findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            onSaveClicked(it)
        }

        rbC = view.findViewById(R.id.radioC)
        rbF = view.findViewById(R.id.radioF)

        rbEN = view.findViewById(R.id.radioEnglish)
        rbPT = view.findViewById(R.id.radioPortuguese)

        temperatureUnit = prefs?.getString("temperature_unit", "C").toString()
        language = prefs?.getString("language", "EN").toString()

        when (temperatureUnit){
            "C" -> rbC.isChecked = true
            "F" -> rbF.isChecked = true
        }

        when (language){
            "PT" -> rbPT.isChecked = true
            "EN" -> rbEN.isChecked = true
        }

        rgLg = view.findViewById(R.id.radioLanguage)
        rgTemp = view.findViewById(R.id.radioTemp)

        rgTemp.setOnCheckedChangeListener{ view, id->
            val radioButton = view.findViewById<RadioButton>(id)
            if (radioButton.isChecked){
                when(radioButton.id){
                    R.id.radioC -> temperatureUnit = "C"
                    R.id.radioF -> temperatureUnit = "F"
                }
            }
        }
        rgLg.setOnCheckedChangeListener{ view, id->
            val radioButton = view.findViewById<RadioButton>(id)
            if (radioButton.isChecked){
                when(radioButton.id){
                    R.id.radioPortuguese -> language = "PT"
                    R.id.radioEnglish -> language = "EN"
                }
            }
        }

    }

    fun onSaveClicked(View: View){
        val editor = prefs?.edit()
        editor?.apply{
            putString("temperature_unit", temperatureUnit)
            putString("language", language)
            apply()
        }

    }


}