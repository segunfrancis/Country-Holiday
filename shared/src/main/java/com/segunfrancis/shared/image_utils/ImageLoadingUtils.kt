package com.segunfrancis.shared.image_utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.ImageLoader
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.util.DebugLogger
import com.segunfrancis.shared.image_utils.ImageLoaderImpl.provideImageLoader

fun ImageView.loadImage(imageUrl: String) {
    load(
        data = imageUrl.replace("\\", ""),
        imageLoader = provideImageLoader(context),
        builder = {
            transformations(CircleCropTransformation()).crossfade(true).scale(Scale.FILL).build()
        })
}

fun ImageView.loadImage(@DrawableRes imageRes: Int) {
    load(imageRes, imageLoader = provideImageLoader(context))
}

object ImageLoaderImpl {
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .logger(DebugLogger())
            .build()
    }
}
