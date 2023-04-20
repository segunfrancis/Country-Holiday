package com.segunfrancis.shared.image_utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.ImageLoader
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import javax.inject.Inject

class ImageLoadingUtils @Inject constructor(private val imageLoader: ImageLoader) {

    fun loadImage(imageView: ImageView, imageUrl: String) {
        imageView.load(
            data = imageUrl.replace("\\", ""),
            imageLoader = imageLoader,
            builder = {
                transformations(CircleCropTransformation()).crossfade(true).scale(Scale.FILL)
                    .build()
            })
    }

    fun loadImage(imageView: ImageView, @DrawableRes imageRes: Int) {
        imageView.load(imageRes, imageLoader = imageLoader)
    }
}
