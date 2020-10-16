package com.junemon.travelingapps.feature.culture

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.gson.Gson
import com.junemon.core.presentation.base.fragment.BaseFragment
import com.junemon.model.domain.Results
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.databinding.FragmentCultureBinding
import com.junemon.travelingapps.feature.onboard.FragmentOnBoardDirections
import com.junemon.travelingapps.utils.staggeredVerticalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_recyclerview_culture_place.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope as koinLifecycleScope

/**
 * Created by Ian Damping on 03,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentCulture : BaseFragment(), HomeCultureAdapter.HomeCultureAdapterListener {
    private var _binding: FragmentCultureBinding? = null
    private val binding get() = _binding!!
   private val gson: Gson by inject()
    private val placeVm: PlaceViewModel by koinLifecycleScope.inject()

    private lateinit var cultureAdapter: HomeCultureAdapter

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCultureBinding.inflate(inflater, container, false)
        cultureAdapter = HomeCultureAdapter(this)
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

    override fun onCultureClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()

        val toDetailFragment =
            FragmentOnBoardDirections.actionFragmentOnBoardToDetailFragment(
                gson.toJson(
                    data
                )
            )

        val extras =
            FragmentNavigatorExtras(cvItemCultureContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    private fun FragmentCultureBinding.initView() {
        when {
            Build.VERSION.SDK_INT < 24 -> {
                ViewGroupCompat.setTransitionGroup(rvCulture, true)
            }
            Build.VERSION.SDK_INT > 24 -> {
                rvCulture.isTransitionGroup = true
            }
        }
        rvCulture.apply {
            staggeredVerticalRecyclerviewInitializer()
            adapter = cultureAdapter
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

            val cultureData = result.filter { it.placeType == "Wisata Budaya" }

            cultureAdapter.run {
                submitList(cultureData)
                // Force a redraw in case the time zone has changed
                this.notifyDataSetChanged()
            }

        }
    }
}