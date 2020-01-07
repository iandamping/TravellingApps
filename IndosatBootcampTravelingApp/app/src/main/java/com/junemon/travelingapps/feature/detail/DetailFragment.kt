package com.junemon.travelingapps.feature.detail

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.travelingapps.R
import com.junemon.travelingapps.activity.MainActivity
import com.junemon.travelingapps.databinding.FragmentDetailBinding
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.util.interfaces.ImageHelper
import com.junemon.travelingapps.presentation.util.interfaces.IntentHelper
import com.junemon.travelingapps.presentation.util.interfaces.LoadImageHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragment() {
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var intentHelper: IntentHelper

    @Inject
    lateinit var imageHelper: ImageHelper

    private val passedData by lazy {
        Gson().fromJson(
            DetailFragmentArgs.fromBundle(arguments!!).detailData,
            PlaceCachePresentation::class.java
        )
    }

    override fun onAttach(context: Context) {
        // inject dagger
        (activity as MainActivity).activityComponent.getFeatureComponent()
            .create().inject(this)
        super.onAttach(context)
        setBaseDialog()
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            detailData = passedData
            initView(passedData)
        }
        return binding.root
    }

    private fun FragmentDetailBinding.initView(data: PlaceCachePresentation) {
        apply {
            loadImageHelper.run { ivDetailMovieImages.loadWithGlide(data.placePicture) }
            ivShare.setOnClickListener {
                intentHelper.run {
                    this@DetailFragment.intentShareImageAndText(
                        lifecycleScope,
                        data.placeName,
                        data.placeDetail,
                        data.placePicture
                    )
                }
            }
            ivDownload.setOnClickListener {
                imageHelper.run {
                    this@DetailFragment.saveImage(
                        lifecycleScope,
                        coordinatorParent,
                        data.placePicture
                    )
                }
            }
        }
    }
}