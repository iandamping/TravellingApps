package com.junemon.composertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.lifecycle.Observer
import androidx.ui.core.*
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.style.TextOverflow
import com.ian.app.helper.data.ResultToConsume
import com.junemon.gamesapi.model.GamePresentation
import com.junemon.gamesapi.model.mapToPresentation
import com.junemon.gamesapi.ui.GameDataViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {
    private val gameVm: GameDataViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameVm.get().observe(this, Observer { result ->
            when (result.status) {
                ResultToConsume.Status.LOADING -> {
                }
                ResultToConsume.Status.ERROR -> {
                }
                ResultToConsume.Status.SUCCESS -> {
                }
            }
            setContent {
                gameRecyclerview(result.data?.mapToPresentation())
            }
        })
    }
}

fun gameRecyclerview(data: List<GamePresentation>?) {
    VerticalScroller {
        Column {
            data?.forEach {
                gameItem(it)
            }
        }
    }
}


@Composable
fun gameItem(data: GamePresentation) {
    val typography: MaterialTypography = MaterialTypography()
    MaterialTheme() {
        Row {
            MaterialTheme() {
                Column(modifier = Spacing(8.dp)) {
                    HeightSpacer(height = 16.dp)
                    Text(text = data.name, style = typography.h5)
                    Text(
                        text = data.slug, style = typography.subtitle1, maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Padding(padding = 8.dp) {
                        Divider(color = Color.Black)
                    }
                }
            }
        }
    }

}



