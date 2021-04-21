package com.example.dailydose.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.dailydose.R
import kotlin.concurrent.thread


class CoronaFragment : Fragment() {
    val coronaCountry = arrayListOf<String>()
    private lateinit var coronaViewModel : CoronaViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coronaViewModel = ViewModelProvider(this).get(CoronaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_corona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        super.onViewCreated(view, savedInstanceState)
        val casesSelection = view.findViewById(R.id.cases_selection) as RadioGroup

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        casesSelection.setOnCheckedChangeListener { group, checkedId ->
            var type = 0
            if (checkedId == R.id.total_cases) {
                type = 0
            } else if (checkedId == R.id.current_cases) {
                type = 1
            }
            updateCountryCases(view, type)
            updateDistrictCases(view, type)
            updateStateCases(view, type)
        }
    }

    private fun updateCountryCases(view: View, type: Int) {
        view.findViewById<TextView>(R.id.active_country_count)
        view.findViewById<TextView>(R.id.deaths_country_count)
        view.findViewById<TextView>(R.id.recovered_country_count)

    }

    private fun updateStateCases(view: View, type: Int) {
        view.findViewById<TextView>(R.id.active_state_count)
        view.findViewById<TextView>(R.id.deaths_state_count)
        view.findViewById<TextView>(R.id.recovered_state_count)

    }

    private fun updateDistrictCases(view: View, type: Int) {
        view.findViewById<TextView>(R.id.active_district_count)
        view.findViewById<TextView>(R.id.deaths_district_count)
        view.findViewById<TextView>(R.id.recovered_district_count)
    }

}