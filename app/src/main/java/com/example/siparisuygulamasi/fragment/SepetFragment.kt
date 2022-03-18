package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.adapter.AnasayfaMenuAdapter
import com.example.siparisuygulamasi.adapter.SepetAdapter
import com.example.siparisuygulamasi.databinding.FragmentSepetBinding
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.viewmodel.SepetFragmentViewModel

class SepetFragment : Fragment() {

    private lateinit var desing: FragmentSepetBinding
    lateinit var viewModel: SepetFragmentViewModel

 override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
     desing = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet, container, false)

     //TOOLBAR
     desing.toolbarTitle = "Sepet"

     //ADAPTER
     desing.sepetRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

     //OBSERVER
     viewModel.yemekListesi.observe(viewLifecycleOwner){

         val adapter = SepetAdapter(requireContext(),viewModel,this)
         desing.sepetAdapter = adapter
         Log.e("DebugFragment", "Sepet Menu Adapter Updated")

     }

     viewModel.sepetListesi.observe(viewLifecycleOwner){

     }

        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }
}