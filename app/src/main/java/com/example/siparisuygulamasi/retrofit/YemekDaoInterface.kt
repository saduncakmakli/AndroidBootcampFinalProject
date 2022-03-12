package com.example.siparisuygulamasi.retrofit

import com.example.siparisuygulamasi.entity.YemekCevap
import retrofit2.Call
import retrofit2.http.GET

interface YemekDaoInterface {
    @GET("yemekler/tumYemekleriGetir.php")
    fun tumYemekleriGetir() : Call<YemekCevap>

}