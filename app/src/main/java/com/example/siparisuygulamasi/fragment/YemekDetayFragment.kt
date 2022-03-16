package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.databinding.FragmentYemekDetayBinding
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.picasso.PicassoUtils
import com.example.siparisuygulamasi.viewmodel.YemekDetayFragmentViewModel

class YemekDetayFragment : Fragment() {

    private lateinit var desing:FragmentYemekDetayBinding
    lateinit var viewModel:YemekDetayFragmentViewModel
    lateinit var yemekNesnesi:Yemek
    var yemekAdet = 0
    var adetDegisiklik = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        desing = DataBindingUtil.inflate(inflater, R.layout.fragment_yemek_detay, container, false)
        desing.yemekDetayFragment = this

        //TOOLBAR
        desing.toolbarTitle = "Ürün Detayı"
        (activity as AppCompatActivity).setSupportActionBar(desing.detayToolbar)

        //OBSERVER
        viewModel.yemekListesi.observe(viewLifecycleOwner){

        }

        viewModel.sepetListesi.observe(viewLifecycleOwner){
            yemekAdet = viewModel.yemekSiparisAdediniHesapla(yemekNesnesi.yemek_adi)
            ekraniGuncelle()
        }

        //ARGS
        val bundle:YemekDetayFragmentArgs by navArgs()
        yemekNesnesi = bundle.yemekNesnesi
        yemekAdet = bundle.sepetYemekAdet


        //DetayImageLoad
        PicassoUtils.yemekResimGoster(yemekNesnesi.yemek_resim_adi,desing.detayImageViewUrun)

        ekraniGuncelle()
        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel : YemekDetayFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
    }

    fun buttonArttir():String{
        yemekAdet += 1
        ekraniGuncelle()
        adetDegisiklik = true
        return yemekAdet.toString()
    }

    fun buttonAzalt():String{
        if (yemekAdet > 0) yemekAdet -= 1
        else yemekAdet = 0

        ekraniGuncelle()
        adetDegisiklik = true
        return yemekAdet.toString()
    }

    fun ekraniGuncelle(){
        desing.detayTextViewAdet.text = "${yemekAdet.toString()} Adet"
        desing.detayTextViewToplamFiyat.text = "${(yemekAdet*yemekNesnesi.yemek_fiyat).toString()} ₺"
    }

    fun siparisiGuncelle(){
        if (adetDegisiklik){
            viewModel.sepettenYemekCikar(yemekNesnesi.yemek_adi)
            viewModel.siparisOlustur(yemekNesnesi,yemekAdet)
            Toast.makeText(requireContext(), "Sipariş Güncellendi.", Toast.LENGTH_SHORT).show()
            adetDegisiklik = false
        }

    }
}