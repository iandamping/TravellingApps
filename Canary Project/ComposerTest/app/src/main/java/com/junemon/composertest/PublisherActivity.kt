package com.junemon.composertest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.MaterialTypography
import androidx.ui.text.style.TextOverflow
import com.ian.app.helper.data.ResultToConsume
import com.ian.app.helper.util.*
import com.junemon.gamesapi.data.datasource.model.sharedGson
import com.junemon.gamesapi.model.GamePresentation
import com.junemon.gamesapi.model.PublisherPresentation
import com.junemon.gamesapi.ui.PublisherDataViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PublisherActivity: AppCompatActivity() {
    private val publisherVm:PublisherDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        publisherVm.get()
        publisherVm.publisherData.observe(this@PublisherActivity, Observer { result ->
            when (result.status) {
                ResultToConsume.Status.LOADING -> {
                    logD(result.status.name + " ini statusnya")
                }
                ResultToConsume.Status.ERROR -> {
                    logD(result.status.name + " ini statusnya")
                }
                ResultToConsume.Status.SUCCESS -> {
                    logD(result.status.name + " ini statusnya")
                }
            }
            setContent {
                publisherRecyclerview(result.data)
            }
        })
    }
}

fun publisherRecyclerview(data: List<PublisherPresentation>?) {
    VerticalScroller {
        Column {
            data?.forEach {
                publisherItem(it)
            }
        }
    }
}


@Composable
fun publisherItem(data: PublisherPresentation) {
    val typography: MaterialTypography = MaterialTypography()
    MaterialTheme() {
        Row {
            MaterialTheme() {
                Column(modifier = Spacing(8.dp)) {
                    HeightSpacer(height = 16.dp)
                    Text(text = data.name, style = typography.h5)
                    Padding(padding = 8.dp) {
                        Divider(color = Color.Black)
                    }
                }
            }
        }
    }

}
