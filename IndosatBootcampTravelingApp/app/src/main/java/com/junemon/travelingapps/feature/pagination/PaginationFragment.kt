package com.junemon.travelingapps.feature.pagination

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.junemon.core.di.factory.viewModelProvider
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.base.BaseFragmentViewBinding
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.di.injector.appComponent
import com.junemon.travelingapps.util.verticalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.android.synthetic.main.item_pagination_recyclerview.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationFragment : BaseFragmentViewBinding<FragmentPaginationBinding>(),
    PaginationAdapter.PaginationAdapterrListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var placeVm: PlaceViewModel

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var paginationAdapter: PaginationAdapter

    private val args: PaginationFragmentArgs by navArgs()

    private val paginationType by lazy {
        args.paginationType
    }

    override fun activityCreated() {
    }

    private fun FragmentPaginationBinding.initView(type: String) {
        placeVm.getSelectedTypeCache(type).observe(viewLifecycleOwner, { result ->
            paginationAdapter.submitList(result.mapCacheToPresentation())
        })
        rvPagination.apply {
            verticalRecyclerviewInitializer()
            adapter = paginationAdapter
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPaginationBinding
        get() = FragmentPaginationBinding::inflate

    override fun viewCreated() {
        postponeEnterTransition()
        placeVm = viewModelProvider(viewModelFactory)
        with(binding) {
            initView(paginationType)
            when {
                Build.VERSION.SDK_INT < 24 -> {
                    ViewGroupCompat.setTransitionGroup(rvPagination, true)
                }
                Build.VERSION.SDK_INT > 24 -> {
                    rvPagination.isTransitionGroup = true
                }
            }
            root.doOnPreDraw { startPostponedEnterTransition() }
        }

    }

    override fun onClicked(data: PlaceCachePresentation) {
        setupExitEnterTransition()
        val toDetailFragment =
            PaginationFragmentDirections.actionPaginationFragmentToDetailFragment(
                gson.toJson(data)
            )
        val extras = FragmentNavigatorExtras(cvItemContainer to data.placePicture!!)
        navigate(toDetailFragment, extras)
    }

    override fun injectDagger() {
        appComponent().getPaginationComponent().provideListener(this).inject(this)
    }
}