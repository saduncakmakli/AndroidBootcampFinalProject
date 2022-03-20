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
import com.example.siparisuygulamasi.databinding.SepetCardBinding
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.viewmodel.SepetFragmentViewModel
import kotlinx.coroutines.*

class SepetFragment : Fragment() {

    private lateinit var desing: FragmentSepetBinding
    lateinit var viewModel: SepetFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet, container, false)
        //viewModel.sepetiGuncelle()
        ActiveData.sepetAdapterActive = false


        //TOOLBAR
        desing.toolbarTitle = "Sepetim"

        //OBSERVER
        viewModel.yemekListesi.observe(viewLifecycleOwner){

        }

        viewModel.sepetListesi.observe(viewLifecycleOwner){

            viewModel.yemekListesi.value?.let { yemekListesi ->
                it?.let {
                    if (!ActiveData.sepetAdapterActive && it.size > 0){
                        val filtreliSepet = viewModel.duzenlenmisSepetListesi(it,yemekListesi)!!
                        viewModel.sepetRepo.sepetiBas(filtreliSepet,"DebugSepet")
                        val adapter = SepetAdapter(requireContext(),viewModel,this, filtreliSepet)
                        desing.sepetAdapter = adapter
                        Log.e("DebugSepetFragment", "Sepet Menu Adapter Updated")
                        ActiveData.sepetAdapterActive = true
                    }
                }
            }
        }

        //RW LAYOUT MANAGER
        desing.sepetRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun azaltButton(sepet: Sepet)
    {
        runBlocking {
            viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,sepet.yemek_siparis_adet-1)
        }
    }

    fun arttirButton(sepet: Sepet)
    {
        runBlocking {
            viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,sepet.yemek_siparis_adet+1)
        }
    }

    fun silButton(sepet: Sepet)
    {
        runBlocking {
            viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,0)
        }
    }
}