package com.dmsoft.infinite.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmsoft.infinite.App
import com.dmsoft.infinite.R
import com.dmsoft.infinite.utils.ImageCallable
import com.dmsoft.infinite.utils.ImageDownloadThreadPoolManager
import com.dmsoft.infinite.utils.OnImageDownloadListener
import com.dmsoft.infinite.utils.SimplePreferences
import kotlinx.android.synthetic.main.li_image_holder.view.*
import javax.inject.Inject

class ImageAdapter(private val context: Context, private var images: ArrayList<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    @Inject
    lateinit var imageDownloadManager: ImageDownloadThreadPoolManager

    @Inject
    lateinit var simplePreferences: SimplePreferences

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.li_image_holder, parent, false), imageDownloadManager, simplePreferences)

    override fun getItemCount(): Int = images.size

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.view.iv_image.setImageDrawable(null)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        imageDownloadManager.cancelTask(holder.layoutPosition)
    }

    class ViewHolder(val view: View,
                     private val imageDownloadThreadPoolManager: ImageDownloadThreadPoolManager,
                     private val simplePreferences: SimplePreferences) : RecyclerView.ViewHolder(view), OnImageDownloadListener {
        fun bind(url: String) {
            view.iv_image.setImageDrawable(null)
            imageDownloadThreadPoolManager.addCallable(layoutPosition, ImageCallable(layoutPosition, url, this))
        }

        override fun onComplete(position: Int, bitmap: Bitmap?) {
            bitmap?.let {
                Handler(Looper.getMainLooper()).post({view.iv_image.setImageBitmap(it)})
                imageDownloadThreadPoolManager.cancelTask(position)
                simplePreferences.increaseLoadingCount()
            }
        }
    }
}