package com.example.siparisuygulamasi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siparisuygulamasi.databinding.SepetCardBinding
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.fragment.SepetFragment
import com.example.siparisuygulamasi.picasso.PicassoUtils
import com.example.siparisuygulamasi.repo.CartEmpty
import com.example.siparisuygulamasi.viewmodel.SepetFragmentViewModel
import kotlinx.coroutines.runBlocking

class SepetAdapter(var mContext: Context,
                   var viewModel:SepetFragmentViewModel,
                   var fragment:SepetFragment,
                   val sepetListesi:ArrayList<Sepet>
                   ) : RecyclerView.Adapter<SepetAdapter.CardDesingHolder>() {

    init {
        Log.e("DebugAdapter", "SepetAdapterInit")
    }

    inner class CardDesingHolder(desing: SepetCardBinding) : RecyclerView.ViewHolder(desing.root){
        var desing : SepetCardBinding
        init {
            this.desing = desing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesingHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val desing = SepetCardBinding.inflate(layoutInflater, parent, false)

        Log.e("DebugAdapter", "SepetAdapter")
        viewModel.sepetRecyclerViewCardsShown = true
        fragment.changeVisibilityCartEmptyAlert(CartEmpty.NOT_EMPTY,false)
        return CardDesingHolder(desing)
    }

    override fun onBindViewHolder(holder: CardDesingHolder, position: Int) {
        val sepet = sepetListesi.get(position)
        val cardDesing = holder.desing
        cardDesing.sepetObject = sepet
        cardDesing.sepetFragment = fragment
        PicassoUtils.yemekResimGoster(sepet.yemek_resim_adi, cardDesing.imageViewItem)

        cardDesing.buttonArttir.setOnClickListener {
            runBlocking {
                viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,sepet.yemek_siparis_adet+1)
            }
            sepet.yemek_siparis_adet += 1
            cardDesing.textViewSepetFiyat.text = "${sepet.yemek_siparis_adet*sepet.yemek_fiyat} ₺"
            cardDesing.textViewAdet.text = sepet.yemek_siparis_adet.toString()
        }

        cardDesing.buttonAzalt.setOnClickListener {
            runBlocking {
                viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,sepet.yemek_siparis_adet-1)
            }
            sepet.yemek_siparis_adet -= 1
            cardDesing.textViewSepetFiyat.text = "${sepet.yemek_siparis_adet*sepet.yemek_fiyat} ₺"
            cardDesing.textViewAdet.text = sepet.yemek_siparis_adet.toString()
            if (sepet.yemek_siparis_adet == 0){
                sepetListesi.removeAt(position)
                notifyDataSetChanged()
                fragment.changeVisibilityCartEmptyAlert(if (sepetListesi.isEmpty()) CartEmpty.EMPTY else CartEmpty.NOT_EMPTY, false)
            }
        }

        cardDesing.cardViewSepettenSil.setOnClickListener {
            runBlocking {
                viewModel.sepetListesi.value = viewModel.chainingRequestSiparisiGuncelle(sepet,0)
            }
            sepet.yemek_siparis_adet = 0
            cardDesing.textViewSepetFiyat.text = "${sepet.yemek_siparis_adet*sepet.yemek_fiyat} ₺"
            cardDesing.textViewAdet.text = sepet.yemek_siparis_adet.toString()
            sepetListesi.removeAt(position)
            notifyDataSetChanged()
            fragment.changeVisibilityCartEmptyAlert(if (sepetListesi.isEmpty()) CartEmpty.EMPTY else CartEmpty.NOT_EMPTY, false)
        }
    }

    override fun getItemCount(): Int {
        sepetListesi?.let {
            Log.e("DebugAdapter", "SepetAdapter-getItemCount ${it.size}")
            return it.size }
        Log.e("DebugAdapter", "SepetAdapter-getItemCount Zero")
        return 0
    }

}