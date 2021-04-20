package com.junemon.travelingapps.feature.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.junemon.model.presentation.PlaceCachePresentation
import com.junemon.model.presentation.dto.mapCacheToPresentation
import com.junemon.travelingapps.base.BaseFragmentViewBinding
import com.junemon.travelingapps.databinding.FragmentPaginationBinding
import com.junemon.travelingapps.util.interfaces.ImageHelper
import com.junemon.travelingapps.util.interfaces.IntentHelper
import com.junemon.travelingapps.util.interfaces.LoadImageHelper
import com.junemon.travelingapps.util.interfaces.PermissionHelper
import com.junemon.travelingapps.util.observeEvent
import com.junemon.travelingapps.util.verticalRecyclerviewInitializer
import com.junemon.travelingapps.vm.PlaceViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PaginationFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val gson: Gson,
    permissionHelper: PermissionHelper,
    loadImageHelper: LoadImageHelper,
    imageHelper: ImageHelper,
    scope: CoroutineScope,
    intentHelper: IntentHelper,
) : BaseFragmentViewBinding<FragmentPaginationBinding>(),
    PaginationAdapter.PaginationAdapterrListener {

    private val paginationAdapter: PaginationAdapter =
        PaginationAdapter(this, permissionHelper, loadImageHelper, imageHelper, scope, intentHelper)

    private val placeVm: PlaceViewModel by viewModels { viewModelFactory }

    private val args: PaginationFragmentArgs by navArgs()

    private val paginationType by lazy {
        args.paginationType
    }

    override fun activityCreated() {
        obvserveNavigation()
    }

    private fun obvserveNavigation() {
        observeEvent(placeVm.navigateEvent) {
            navigate(it)
        }
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
        with(binding) {
            initView(paginationType)
        }
    }

    override fun onClicked(data: PlaceCachePresentation) {
        val toDetailFragment =
            PaginationFragmentDirections.actionPaginationFragmentToDetailFragment(
                gson.toJson(data)
            )
        placeVm.setNavigate(toDetailFragment)
    }

}