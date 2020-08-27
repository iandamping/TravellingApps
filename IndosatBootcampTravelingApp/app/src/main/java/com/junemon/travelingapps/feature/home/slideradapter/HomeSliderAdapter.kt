package com.junemon.travelingapps.feature.home.slideradapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.google.gson.Gson
import com.junemon.core.presentation.layoutInflater
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.databinding.ItemSliderBinding
import com.junemon.travelingapps.feature.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 05,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeSliderAdapter(
    private val listener: HomeSliderListener,
    private val loadImageHelper: LoadImageHelper
) : PagerAdapter() {
    private var data: MutableSet<PlaceCachePresentation> = mutableSetOf()
    private var _binding: ItemSliderBinding? = null
    private val binding get() = _binding!!

    fun addData(passedData: List<PlaceCachePresentation>) {
        data.clear()
        passedData.forEach {
            data.add(it)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        _binding = ItemSliderBinding.inflate(container.context.layoutInflater)

        loadImageHelper.run { binding.ivSliderImage.loadWithGlide(data.toList()[position].placePicture) }
        binding.tvPlaceName.text = data.toList()[position].placeName
        binding.tvPlaceAddress.text = data.toList()[position].placeAddres

        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewCompat.setTransitionName(binding.ivSliderImage, data.toList()[position].placePicture)
            }
            Build.VERSION.SDK_INT > 24 -> {
                binding.ivSliderImage.transitionName =  data.toList()[position].placePicture
            }
        }

        binding.ivSliderImage.setOnClickListener {
            listener.onClickListener(it, data.toList()[position])
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

    override fun getCount() = data.size
}