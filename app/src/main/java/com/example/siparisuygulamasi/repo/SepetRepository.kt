package com.example.siparisuygulamasi.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siparisuygulamasi.entity.CRUDCevap
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.SepetCevap
import com.example.siparisuygulamasi.retrofit.ApiUtils
import com.example.siparisuygulamasi.retrofit.SepetDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetRepository (val kullanici_adi: String) {
    var sepetListesi:MutableLiveData<List<Sepet>>
    var sepetDataAccessObject:SepetDaoInterface

    init {
        sepetDataAccessObject = ApiUtils.getSepetDaoInterface()
        sepetListesi = MutableLiveData()
    }

    fun sepeteYemekEkle(yemek_adi:String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int){
        sepetDataAccessObject.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val success = response.body().success
                val mesaj = response.body().message
                Log.e("DebugRepo", "Sepete Yemek Ekle Api success -> $success Api message: $mesaj ")
            }
            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepete Yemek Ekle Api Failure")
            }
        })
    }

    fun sepetiVeriTabanindanGuncelle(){
        sepetDataAccessObject.sepettekiYemekleriGetir(kullanici_adi).enqueue(object : Callback<SepetCevap>{
            override fun onResponse(call: Call<SepetCevap>?, response: Response<SepetCevap>) {
                val success = response.body().success
                val liste = response.body().sepet_yemekler
                sepetListesi.value = liste
                Log.e("DebugRepo", "Sepetteki Yemekleri Getir Api success -> $success")
            }
            override fun onFailure(call: Call<SepetCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepetteki Yemekleri Getir Api Failure")
            }
        })
    }

    fun sepettenYemekSil(sepet_yemek_id:Int){
        sepetDataAccessObject.sepettenYemekSil(sepet_yemek_id,kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val success = response.body().success
                val mesaj = response.body().message
                Log.e("DebugRepo", "Sepetten Yemek Sil Api success -> $success Api message: $mesaj ")
            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepetten Yemek Sil Api Failure")
            }
        })
    }



}