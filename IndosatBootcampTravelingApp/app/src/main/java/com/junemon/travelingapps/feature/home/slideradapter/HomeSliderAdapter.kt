package com.junemon.travelingapps.feature.home.slideradapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ian.app.helper.interfaces.LoadImageResult
import com.ian.app.helper.interfaces.ViewHelperResult
import com.junemon.travelingapps.R
import com.junemon.travelingapps.presentation.model.PlaceCachePresentation
import kotlinx.android.synthetic.main.item_slider.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Ian Damping on 05,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeSliderAdapter(private val data: List<PlaceCachePresentation>) : PagerAdapter(), KoinComponent {
    private val viewHelper: ViewHelperResult by inject()
    private val loadImageHelper: LoadImageResult by inject()
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val views = viewHelper.run { container.inflates(R.layout.item_slider) }
        loadImageHelper.run { views.ivSliderImage.loadWithGlide(data[position].placePicture) }
        views.tvPlaceName.text = data[position].placeName
        views.tvPlaceAddress.text = data[position].placeAddres
        views.ivSliderImage?.setOnClickListener {

            // it.findNavController().navigate(MovieFragmentDirections.actionHomeFragmentToDetailMovieFragment(data[position].id!!))
        }
        container.addView(views)
        return views
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = data.size
}