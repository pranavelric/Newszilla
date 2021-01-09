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
import com.hilt.newszilla.ui.home.newsFragments.searchNewsFragment.SearchNewsFragment
import kotlinx.android.synthetic.main.source_btm_sheet.view.*
import javax.inject.Inject


class SearchBtmSheetDialog @Inject constructor(val mySharedPrefrences: MySharedPrefrences) :
    BottomSheetDialogFragment() {


    lateinit var selectedLanguage: String
    lateinit var selectedSortBy: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.source_btm_sheet, container, false)




        v.first_filter_text.text = "Language"
        v.second_filter_text.text = "Sort by"
        val btnValidateSignature = v.btn_apply


        val hashMap = HashMap<String, String>()
        hashMap.put("English", "en")
        hashMap.put("Hindi", "hi")
        hashMap.put("French", "fr")
        hashMap.put("Japanese", "jp")

        val arr = arrayListOf<String>(
            "English",
            "Hindi",
            "French",
            "Japanese",
        )


        val arrValues = arrayListOf<String>(
            "en",
            "hi",
            "fr",
            "jp"
        )
        val arrSortbyValues = arrayListOf<String>(
            "publishedAt",
            "relevancy",
            "popularity"
        )


        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arr)

        v.spinner_sources.adapter = adapter
        v.spinner_sources.setSelection(arrValues.indexOf(mySharedPrefrences.getNewsLanguage()))
        v.spinner_country.setSelection(arrSortbyValues.indexOf(mySharedPrefrences.getSortBy()))

        v.spinner_sources.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedLanguage = hashMap.get(arr[position]).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedLanguage = "en"

            }


        }


        val hashMapSortBy = HashMap<String, String>()
        hashMapSortBy.put("Published At", "publishedAt")
        hashMapSortBy.put("Relevancy", "relevancy")
        hashMapSortBy.put("Popularity", "popularity")


        val arrSortBy = arrayListOf<String>(
            "Published At",
            "Relevancy",
            "Popularity"

        )


        val adapterSortBy =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrSortBy)

        v.spinner_country.adapter = adapterSortBy
        v.spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSortBy = hashMapSortBy.get(arrSortBy[position]).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSortBy = "publishedAt"

            }


        }

















        v.btn_apply.setOnClickListener {

            mySharedPrefrences.selectedLanguage(selectedLanguage)
            mySharedPrefrences.selectedNewsSortBy(selectedSortBy)
            Toast.makeText(context, "Restart the app for changes to be applied", Toast.LENGTH_LONG)
                .show()
            dismiss()
        }





        return v

    }


}