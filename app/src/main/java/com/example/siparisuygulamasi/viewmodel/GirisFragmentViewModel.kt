package com.example.siparisuygulamasi.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.fragment.GirisFragmentDirections

class GirisFragmentViewModel : ViewModel() {

    fun login(v: View, user:String){
        ActiveData.kullanici_adi = user
        val direction = GirisFragmentDirections.actionGirisFragmentToAnasayfaFragment()
        Navigation.findNavController(v).navigate(direction)
    }
}