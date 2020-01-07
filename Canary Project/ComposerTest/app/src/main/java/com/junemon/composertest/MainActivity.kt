package com.junemon.composertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.MaterialTypography
import androidx.ui.material.surface.Surface
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainView()
        }
    }
}


@Composable
fun mainView() {
    val listOfNames = listOf<String>("Jun","Ian","Kentus")
    val listOfJobs = listOf<String>("Technician","Chef","Cookie")
    MaterialTheme() {
        Column(modifier = Spacing(8.dp)) {
            setImageView()
            //give padding top
            HeightSpacer(height = 16.dp)
            listOfNames.forEach {names ->
                setTextViewName(names)

            }
            Padding(padding = 10.dp) {
                Divider(color = Color.Black)
            }
            listOfJobs.forEach { jobs ->
                setTextViewDetail(jobs)
            }
            //typography h6 is to set textsize, the lower it get the bigger the textsize
        }
    }
}

@Composable
fun setTextViewName(name:String) {
    val typography: MaterialTypography = MaterialTypography()
    Column(modifier = Spacing(8.dp)) {
        Surface(color = Color.White) {
            Text(text = "Hello $name", modifier = Spacing(8.dp))
        }
    }

}

@Composable
fun setTextViewDetail(job:String) {
    val typography: MaterialTypography = MaterialTypography()
    Column(modifier = Spacing(8.dp)) {
        Text(
            text = "Im an ${job} in here and there with this and stuff also blalbalbalfpldpalpwdlapwlvpapvlpawlpbalbplwpalbp ",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = typography.h6
        )
    }

}

@Composable
fun setImageView() {
    //Context to use as context.resource
    val context = +ambient(ContextAmbient)
    //change drawable into image type
    val image = imageFromResource(context.resources, R.drawable.gereja)

    //container to hold imageview
    Container(height = 180.dp, expanded = true) {
        //giving round corner in image
        Clip(shape = RoundedCornerShape(9.dp)) {
            //imageview
            DrawImage(image)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    myApp {
        mainView()
    }
}

@Composable
private fun myApp(children: @Composable() () -> Unit) {
    MaterialTheme {
        children()
    }
}
