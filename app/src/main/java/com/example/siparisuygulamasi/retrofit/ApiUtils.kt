package com.example.siparisuygulamasi.retrofit

class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYemekDaoInterface() : YemekDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(YemekDaoInterface::class.java)
        }

        fun getSepetDaoInterface() : SepetDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(SepetDaoInterface::class.java)
        }
    }
}