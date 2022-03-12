package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.databinding.FragmentGirisBinding
import com.example.siparisuygulamasi.viewmodel.GirisFragmentViewModel

class GirisFragment : Fragment() {

    private lateinit var design:FragmentGirisBinding
    lateinit var  viewModel:GirisFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_giris, container, false)
        design.girisFragment = this
        design.toolbarTitle = getString(R.string.app_name)
        design.toolbarSubTitle = "Kullanıcı Girişi"

        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : GirisFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }
}