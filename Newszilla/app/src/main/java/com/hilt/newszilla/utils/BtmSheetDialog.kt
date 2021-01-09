package com.hilt.newszilla.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hilt.newszilla.R
import com.hilt.newszilla.ui.home.HomeActivity
import kotlinx.android.synthetic.main.source_btm_sheet.view.*
import javax.inject.Inject


class BtmSheetDialog @Inject constructor(val mySharedPrefrences: MySharedPrefrences) :
    BottomSheetDialogFragment() {


    lateinit var selectedSource: String
    lateinit var selectedCountry: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.source_btm_sheet, container, false)

        val btnValidateSignature = v.btn_apply


        val hashMap = HashMap<String, String>()
        hashMap.put("Google News", "google-news-in")
        hashMap.put("The Hindu", "the-hindu")
        hashMap.put("The Times of India", "the-times-of-india")
        hashMap.put("ABC News", "abc-news")
        hashMap.put("BBC News", "bbc-news")
        hashMap.put("CNN", "cnn")
        hashMap.put("ESPN", "espn")


        val arr = arrayListOf<String>(
            "Google News",
            "The Hindu",
            "The Times of India",
            "ABC News",
            "BBC News",
            "CNN",
            "ESPN"
        )


        val arrValues = arrayListOf<String>(
            "google-news-in",
            "the-hindu",
            "the-times-of-india",
            "abc-news",
            "bbc-news",
            "cnn",
            "espn"
        )
        val arrCountriesValues = arrayListOf<String>(
            "in",
            "us",
            "fr",
            "nz",
            "jp",
            "au",
            "ch",
            "cn"
        )


        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arr)

        v.spinner_sources.adapter = adapter
        v.spinner_sources.setSelection(arrValues.indexOf(mySharedPrefrences.getNewsSource()))
        v.spinner_country.setSelection(arrCountriesValues.indexOf(mySharedPrefrences.getCountry()))
        v.spinner_sources.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSource = hashMap.get(arr[position]).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSource = "the-hindu"

            }


        }


        val hashMapCountry = HashMap<String, String>()
        hashMapCountry.put("India", "in")
        hashMapCountry.put("USA", "us")
        hashMapCountry.put("France", "fr")
        hashMapCountry.put("New Zealand", "nz")
        hashMapCountry.put("Japan", "jp")
        hashMapCountry.put("Australia", "au")
        hashMapCountry.put("Switzarland", "ch")
        hashMapCountry.put("China", "cn")


        val arrCountries = arrayListOf<String>(
            "India",
            "USA",
            "France",
            "New Zealand",
            "Japan",
            "Australia",
            "Switzarland",
            "China"
        )


        val adapterCountry =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                arrCountries
            )

        v.spinner_country.adapter = adapterCountry
        v.spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCountry = hashMapCountry.get(arrCountries[position]).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCountry = "in"

            }


        }

















        v.btn_apply.setOnClickListener {
            mySharedPrefrences.selectedNewsSource(selectedSource)
            mySharedPrefrences.selectedNewsCountry(selectedCountry)
            Toast.makeText(context, "Restart the app for changes to be applied", Toast.LENGTH_LONG)
                .show()

            dismiss()

        }







        return v

    }


}