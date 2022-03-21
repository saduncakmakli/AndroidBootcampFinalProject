package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.adapter.SepetAdapter
import com.example.siparisuygulamasi.databinding.FragmentSepetBinding
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.viewmodel.SepetFragmentViewModel
import com.example.siparisuygulamasi.repo.CartEmpty
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class SepetFragment : Fragment() {

    private lateinit var desing: FragmentSepetBinding
    lateinit var viewModel: SepetFragmentViewModel
    lateinit var adapter: SepetAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet, container, false)
        ActiveData.sepetAdapterActive = false
        viewModel.sepetRecyclerViewCardsShown = false

        //TOOLBAR
        desing.toolbarTitle = "Sepetim"

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(desing.sepetToolbar)
        (activity as AppCompatActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        //OBSERVER
        viewModel.yemekListesi.observe(viewLifecycleOwner){

        }

        viewModel.sepetListesi.observe(viewLifecycleOwner){
            //ADAPTER
            loadRecyclerView()
        }

        //RW LAYOUT MANAGER
        desing.sepetRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        //LOTTIE AND CART İS EMPTY CONTROL
        //Lottie oto-gone timer.
        var TimerIsStarted = true
        val timer = object: CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                if (!TimerIsStarted){
                    changeVisibilityCartEmptyAlert(if (viewModel.sepetRecyclerViewCardsShown == true) CartEmpty.NOT_EMPTY else CartEmpty.EMPTY, true)
                }
                Log.e("DebugSepetFragment", "Lottie and RecyclerviewControl Timer tick")
                TimerIsStarted = false
            }
            override fun onFinish() {}
        }

        //Timer and Coroutine Job Starters
        timer.start()


        return desing.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("DebugSepetFragment", "Tollbarda bir tuşa basıldı.")
        if (item.itemId == android.R.id.home) {
            Log.e("DebugSepetFragment", "Geri Tuşuna Basıldı.")
            //getParentFragmentManager().popBackStack() //Hataya sebep oluyor
            val direction = SepetFragmentDirections.actionSepetFragmentToAnasayfaFragment()
            Navigation.findNavController(requireView()).navigate(direction)
            return true
        }
        else return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SepetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun changeVisibilityCartEmptyAlert(cardEmpty: CartEmpty, tryReLoadRecyclerView:Boolean){
        if (cardEmpty == CartEmpty.NOT_EMPTY) {
            desing.buttonSepeteUrunEkle.visibility = View.GONE
            desing.textViewSepetBos.visibility = View.GONE
        }else if(cardEmpty == CartEmpty.EMPTY){
            desing.buttonSepeteUrunEkle.visibility = View.VISIBLE
            desing.textViewSepetBos.visibility = View.VISIBLE

            if (tryReLoadRecyclerView){
                //Recycler view'e veri hatalı gelirse tekrar yüklemek için
                loadRecyclerView()
            }
        }
    }

    fun loadRecyclerView(){
        viewModel.yemekListesi.value?.let { yemekListesi ->
            viewModel.sepetListesi.value?.let { sepetListesi ->
                if (!ActiveData.sepetAdapterActive && !sepetListesi.isNullOrEmpty() && !yemekListesi.isNullOrEmpty()){
                    val filtreliSepet = viewModel.duzenlenmisSepetListesi(sepetListesi, yemekListesi)
                    if (!filtreliSepet.isNullOrEmpty()) {
                        viewModel.sepetRepo.sepetiBas(filtreliSepet, "DebugSepet")
                        val arrayList = ArrayList<Sepet>(filtreliSepet)
                        adapter = SepetAdapter(requireContext(), viewModel, this, arrayList)
                        desing.sepetAdapter = adapter
                        Log.e("DebugSepetFragment", "Sepet Menu Adapter Updated")
                        ActiveData.sepetAdapterActive = true
                    }
                }
            }
        }
    }


}