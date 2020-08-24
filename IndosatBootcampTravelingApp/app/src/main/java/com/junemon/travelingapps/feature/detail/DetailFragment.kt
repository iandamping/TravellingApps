package com.junemon.travelingapps.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.junemon.core.presentation.util.interfaces.ImageHelper
import com.junemon.core.presentation.util.interfaces.IntentHelper
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.travelingapps.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
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

    @Inject
    lateinit var gson: Gson

    private val passedData by lazy {
        gson.fromJson(
            DetailFragmentArgs.fromBundle(requireArguments()).detailData,
            PlaceCachePresentation::class.java
        )
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            detailData = passedData
            initView(passedData)
        }
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
    }

    private fun FragmentDetailBinding.initView(data: PlaceCachePresentation) {
        loadImageHelper.run { ivDetailMovieImages.loadWithGlide(data.placePicture) }
        ivShare.setOnClickListener {
            intentHelper.run {
                lifecycleScope.launch {
                    this@DetailFragment.intentShareImageAndText(
                        data.placeName,
                        data.placeDetail,
                        data.placePicture
                    )
                }

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