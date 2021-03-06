package com.example.dailydose.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailydose.R


class CoronaFragment : Fragment() {
    private lateinit var coronaViewModel : CoronaViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coronaViewModel = ViewModelProvider(this).get(CoronaViewModel::class.java)
        coronaViewModel.countryCases.observe(this, { country ->
            updateCountryCases(country)
        })
        coronaViewModel.stateCases.observe(this, { state ->
            updateStateCases(state)
        })
        root = inflater.inflate(R.layout.fragment_corona, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        countrySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if(pos != 0) coronaViewModel.getCountryCases(parent.getItemAtPosition(pos) as String)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val stateSpinner = view.findViewById<Spinner>(R.id.stateSpinner)
        stateSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if(pos != 0) coronaViewModel.getStateCases(parent.getItemAtPosition(pos) as String)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun updateCountryCases(country: CaseModel) {
        val activeCases = root.findViewById<TextView>(R.id.active_country_count)
        val deaths = root.findViewById<TextView>(R.id.deaths_country_count)
        val recovered = root.findViewById<TextView>(R.id.recovered_country_count)

        activeCases.text = country.activeTotal.toString()
        deaths.text = country.deathsTotal.toString()
        recovered.text = country.recoveredTotal.toString()
    }

    private fun updateStateCases(state: CaseModel?) {
        val activeCases = root.findViewById<TextView>(R.id.active_state_count)
        val deaths = root.findViewById<TextView>(R.id.deaths_state_count)
        val recovered = root.findViewById<TextView>(R.id.recovered_state_count)

        activeCases.text = state?.activeTotal.toString()
        deaths.text = state?.deathsTotal.toString()
        recovered.text = state?.recoveredTotal.toString()
    }
}