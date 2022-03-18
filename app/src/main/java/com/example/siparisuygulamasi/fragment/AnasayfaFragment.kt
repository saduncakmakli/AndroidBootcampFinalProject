package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.adapter.AnasayfaMenuAdapter
import com.example.siparisuygulamasi.databinding.FragmentAnasayfaBinding
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.viewmodel.AnasayfaFragmentViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AnasayfaFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var desing:FragmentAnasayfaBinding
    lateinit var viewModel:AnasayfaFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        desing = DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa, container, false)
        desing.anasayfaFragment = this

        //TOOLBAR
        desing.toolbarTitle = "Menü"
        (activity as AppCompatActivity).setSupportActionBar(desing.toolbarAnasayfa)

        //ADAPTER
        desing.rvMenu.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        //OBSERVER
        viewModel.yemekListesi.observe(viewLifecycleOwner) {
            Log.e("DebugFragment", "AnasayfaFragment yemekListesi obverse method")
            viewModel.yemekleriBas()

            if (!ActiveData.menuAdapterActive) {
                val adapter = AnasayfaMenuAdapter(requireContext(),viewModel,this)
                desing.menuAdapter = adapter
                Log.e("DebugFragment", "Anasayfa Menu Adapter Updated")
                ActiveData.menuAdapterActive = true
            }

        }

        viewModel.sepetListesi.observe(viewLifecycleOwner){
            Log.e("DebugFragment", "AnasayfaFragment sepetListesi obverse method")
            viewModel.sepetiBas()
            ekraniGuncelle()

        }

        firstStart() //Lottie animation, ve karşılama mesajı için
        //Veri tabanından güncelleme
        //guncelle() //Sayfayı girişte guncelleme için
        startRegularlyApiRequest() //Sayfayı hem girişte hemde düzenli olarak güncellenmek için

        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel : AnasayfaFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_anasayfa, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onPause() {
        super.onPause()
        cancelRegularlyApiRequest()
    }

    fun detaySayfasınaGec(yemekNesnesi: Yemek, v:View){
        ActiveData.menuAdapterActive = false
        val direction = AnasayfaFragmentDirections.actionAnasayfaFragmentToYemekDetayFragment(yemekNesnesi,viewModel.yemekSiparisAdediniHesapla(yemekNesnesi.yemek_adi))
        Navigation.findNavController(v).navigate(direction)
    }

    fun sepetSayfasınaGec(v:View){
        ActiveData.menuAdapterActive = false
        val direction = AnasayfaFragmentDirections.actionAnasayfaFragmentToSepetFragment()
        Navigation.findNavController(v).navigate(direction)
    }

    fun guncelle(){
        viewModel.yemekleriGuncelle()
        viewModel.sepetiGuncelle()
        ekraniGuncelle()
    }

    fun ekraniGuncelle(){
        Log.e("Debug", "Anasayfa Ekran Guncellendi Sepet Ücreti:${viewModel.toplamSepetUcretiniHesapla()}, Gönderim Ücreti:${viewModel.sepetinGonderimUcretiniHesapla()}")
        desing.textViewSepetUcreti.text = viewModel.toplamSepetUcretiniYazdır()
        desing.textViewGonderimUcreti.text = viewModel.sepetinGonderimUcretiniYazdır()
        desing.textViewToplamUcret.text = viewModel.toplamUcretiYazdır()
    }

    //Oto API Request for update Data regularly
    private var apiRequestJob: Job? = null

    fun startRegularlyApiRequest() {
        apiRequestJob = lifecycleScope.launch {
            while(true) {
                Log.e("DebugFragment", "Anasayfa -> apiRequestJob is running.")
                guncelle()
                delay(10_000)
            }
        }
    }

    fun cancelRegularlyApiRequest() {
        apiRequestJob?.cancel()
    }

    fun firstStart(){
        if (ActiveData.sepetFragmentHasNeverBeenShownYet) {

            ActiveData.sepetFragmentHasNeverBeenShownYet = false
            desing.animationView.playAnimation()
            desing.constraintLayoutLottie.visibility = View.VISIBLE
            desing.constraintLayoutProfile.visibility = View.GONE

            //Welcome message oto-gone timer.
            val dp = requireContext().resources.displayMetrics.density+0.5f
            var welcomeMessageTimerIsStarted = true
            val timerWelcomeMessage = object: CountDownTimer(4000, 2000) {
                override fun onTick(millisUntilFinished: Long) {
                    if ((desing.textViewWelcome.visibility == View.VISIBLE) && !welcomeMessageTimerIsStarted){
                        desing.textViewWelcome.visibility = View.GONE
                        desing.constraintLayoutProfile.setPadding(5,5,5,5)
                        desing.imageViewProfile.layoutParams.width = (38 * dp).toInt()
                        desing.imageViewProfile.layoutParams.height = (38 * dp).toInt()
                        Log.e("DebugFragment", "Welcome message is gone.")
                    }
                    Log.e("DebugFragment", "Timer tick")
                    welcomeMessageTimerIsStarted = false
                }
                override fun onFinish() {}
            }

            //Lottie oto-gone timer.
            var lottieTimerIsStarted = true
            val timerLottieHider = object: CountDownTimer(4000, 2000) {
                override fun onTick(millisUntilFinished: Long) {
                    if (!lottieTimerIsStarted){
                        desing.animationView.pauseAnimation()
                        desing.constraintLayoutLottie.visibility = View.GONE
                        desing.constraintLayoutProfile.visibility = View.VISIBLE
                        timerWelcomeMessage.start()
                    }
                    Log.e("DebugFragment", "Lottie Timer tick")
                    lottieTimerIsStarted = false
                }
                override fun onFinish() {}
            }

            //Timer and Coroutine Job Starters
            timerLottieHider.start()

        }else{
            desing.textViewWelcome.visibility = View.GONE
            desing.cardViewProfil.backgroundTintList = (requireContext().resources.getColorStateList(com.google.android.material.R.color.material_dynamic_neutral95))
        }
    }


}