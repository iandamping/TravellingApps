package com.junemon.travelingapps.feature.home.slideradapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.junemon.core.presentation.layoutInflater
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.CustomLoadingBinding
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.databinding.ItemSliderBinding
import kotlinx.android.synthetic.main.item_slider.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 05,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeSliderAdapter @Inject constructor(
    // private val data: List<PlaceCachePresentation>,
    private val viewHelper: ViewHelper,
    private val loadImageHelper: LoadImageHelper
) : PagerAdapter() {
    private var data:List<PlaceCachePresentation> = mutableListOf()
    private var _binding: ItemSliderBinding? = null
    private val binding get() = _binding!!



    fun addData(passedData: List<PlaceCachePresentation>){
        data = passedData.toMutableList()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        _binding = ItemSliderBinding.inflate(container.context.layoutInflater)

        loadImageHelper.run { binding.ivSliderImage.loadWithGlide(data[position].placePicture) }
        binding.tvPlaceName.text = data[position].placeName
        binding.tvPlaceAddress.text = data[position].placeAddres
        binding.ivSliderImage.setOnClickListener {
            // it.findNavController().navigate(MovieFragmentDirections.actionHomeFragmentToDetailMovieFragment(data[position].id!!))
        }
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        _binding = null
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = data.size ?: 0
}