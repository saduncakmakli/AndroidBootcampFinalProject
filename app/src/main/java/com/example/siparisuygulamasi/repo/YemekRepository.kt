package com.example.siparisuygulamasi.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.entity.YemekCevap
import com.example.siparisuygulamasi.retrofit.ApiUtils
import com.example.siparisuygulamasi.retrofit.YemekDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemekRepository {
    var yemekListesi:MutableLiveData<List<Yemek>>
    var yemekDataAccessObject : YemekDaoInterface

    init {
        yemekDataAccessObject = ApiUtils.getYemekDaoInterface()
        yemekListesi = MutableLiveData()
    }

    fun tumYemekler() : MutableLiveData<List<Yemek>>{
        return yemekListesi
    }

   fun yemekleriVeritabanindanGuncelle ()
    {
        Log.e("DebugRepo", "yemekleriVeritabanindanGuncelle()")
        yemekDataAccessObject.tumYemekleriGetir().enqueue(object : Callback<YemekCevap>{
            override fun onResponse(call: Call<YemekCevap>?, response: Response<YemekCevap>) {
                val success = response.body().success
                val liste = response.body().yemekler
                yemekListesi.value = liste
                Log.e("DebugRepo", "Yemekleri Getir Api success -> $success")
            }
            override fun onFailure(call: Call<YemekCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Yemekleri Getir Api Failure")
            }
        })
    }

    fun yemekleriBas(){ for (yemek in yemekListesi.value!!) Log.e("DebugFragmentVM", "Yemek ID:${yemek.yemek_id} Ad:${yemek.yemek_adi}") }
}