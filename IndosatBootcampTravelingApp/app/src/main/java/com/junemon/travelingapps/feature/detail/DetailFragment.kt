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
import com.junemon.travelingapps.R
import com.junemon.travelingapps.databinding.FragmentDetailBinding
import com.junemon.travelingapps.presentation.base.BaseFragment
import com.junemon.travelingapps.presentation.model.PlaceCachePresentation
import kotlin.properties.Delegates

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragment() {
    private val gson = Gson()
    private var isPermisisonGranted by Delegates.notNull<Boolean>()
    private val passedData by lazy { gson.fromJson(DetailFragmentArgs.fromBundle(arguments!!).detailData, PlaceCachePresentation::class.java) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBaseDialog()
        ilegallArgumenCatching {
            checkNotNull(activity)
            permissionHelper.getAllPermission(activity!!) {
                isPermisisonGranted = it
            }
        }
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            detailData = passedData
            initView(passedData)
        }
        return binding.root
    }

    private fun FragmentDetailBinding.initView(data: PlaceCachePresentation) {
        apply {
            loadingImageHelper.run { ivDetailMovieImages.loadWithGlide(data.placePicture) }
            ivShare.setOnClickListener {
                intentHelper.run {
                    this@DetailFragment.intentShareImageAndText(lifecycleScope, data.placeName, data.placeDetail, data.placePicture)
                }
            }
            ivDownload.setOnClickListener {
                imageHelper.run {
                    this@DetailFragment.saveImage(lifecycleScope, coordinatorParent, data.placePicture)
                }
            }
        }
    }
}