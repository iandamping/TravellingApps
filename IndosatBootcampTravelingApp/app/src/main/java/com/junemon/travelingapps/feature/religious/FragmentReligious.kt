package com.junemon.travelingapps.feature.religious

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.core.presentation.util.interfaces.LoadImageHelper
import com.junemon.core.presentation.util.interfaces.ViewHelper
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.databinding.FragmentReligiousBinding
import com.junemon.travelingapps.feature.onboard.FragmentOnBoardDirections
import com.junemon.travelingapps.utils.staggeredVerticalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview_religius_place.*
import org.koin.android.ext.android.inject
import androidx.lifecycle.lifecycleScope as androidxLifecycleScope
import org.koin.androidx.scope.lifecycleScope as koinLifecycleScope

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentReligious : BaseFragment(), HomeReligiousAdapter.HomeReligiousAdapterListener {
    private var _binding: FragmentReligiousBinding? = null
    private val binding get() = _binding!!
    private val gson: Gson by inject()
    private val placeVm: PlaceViewModel by koinLifecycleScope.inject()

    private lateinit var religiousAdapter: HomeReligiousAdapter

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReligiousBinding.inflate(inflater, container, false)
        religiousAdapter = HomeReligiousAdapter(this)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.initView()
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        initData()
    }

    override fun onReligiousClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            FragmentOnBoardDirections.actionFragmentOnBoardToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemReligiusContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    private fun FragmentReligiousBinding.initView() {
        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewGroupCompat.setTransitionGroup(rvReligious, true)
            }
            Build.VERSION.SDK_INT > 24 -> {
                rvReligious.isTransitionGroup = true
            }
        }

        rvReligious.apply {
            staggeredVerticalRecyclerviewInitializer()
            adapter = religiousAdapter
        }
    }

    private fun initData() {
        placeVm.getRemote().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Results.Success -> {
                    initRecyclerView(result.data.mapCacheToPresentation())
                }
                is Results.Error -> {
                }
                is Results.Loading -> {
                    if (!result.cache.isNullOrEmpty()) {
                        initRecyclerView(result.cache?.mapCacheToPresentation())
                    }
                }
            }
        })
    }

    private fun initRecyclerView(result: List<PlaceCachePresentation>?) {
        universalCatching {
            checkNotNull(result)
            check(result.isNotEmpty())

            val religiData = result.filter { it.placeType == "Wisata Religi" }


            religiousAdapter.run {
                submitList(religiData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }

        }
    }
}