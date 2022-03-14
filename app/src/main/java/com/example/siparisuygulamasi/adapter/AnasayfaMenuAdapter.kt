package com.example.siparisuygulamasi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siparisuygulamasi.databinding.AnasayfaMenuCardBinding
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.fragment.AnasayfaFragment
import com.example.siparisuygulamasi.viewmodel.AnasayfaFragmentViewModel

class AnasayfaMenuAdapter(var mContext:Context,
                          var yemekListesi:List<Yemek>,
                          var viewModel:AnasayfaFragmentViewModel,
                          var fragment:AnasayfaFragment) : RecyclerView.Adapter<AnasayfaMenuAdapter.CardDesingHolder>() {
    init {
        Log.e("DebugAdapter", "MenuAdapterInit")
    }

    inner class CardDesingHolder(desing:AnasayfaMenuCardBinding) : RecyclerView.ViewHolder(desing.root){
        var desing : AnasayfaMenuCardBinding
        init {
            this.desing = desing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesingHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val desing = AnasayfaMenuCardBinding.inflate(layoutInflater, parent, false)

        Log.e("DebugAdapter", "MenuAdapter")
        /*
        for (yemek in yemekListesi){
            Log.e("DebugAdapter", "Yemek: ${yemek.yemek_adi}")
        }
        */

        return CardDesingHolder(desing)
    }

    override fun onBindViewHolder(holder: CardDesingHolder, position: Int) {
        val yemek = yemekListesi.get(position)
        val cardDesign = holder.desing
        cardDesign.yemekObject = yemek
        cardDesign.anasayfaFragment = fragment
        viewModel.cardMenuResimGoster(yemekListesi[position].yemek_resim_adi,cardDesign)
    }

    override fun getItemCount(): Int {
        return yemekListesi.size
    }
}