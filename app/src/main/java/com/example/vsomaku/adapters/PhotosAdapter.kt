package com.example.vsomaku.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.vsomaku.R
import com.example.vsomaku.data.Photo
import com.example.vsomaku.modules.GlideApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PhotosAdapter(private val context : Context, private val photos : List<Photo>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.rcv_item_photo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {
        holder.textView?.text = photos[position].title
        val url = GlideUrl(
            photos[position].thumbnailUrl+".png", LazyHeaders.Builder()
                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(context))
                .build()
        )
        GlideApp
            .with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .override(150,150)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.imageView!!)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var textView: TextView? = null
        var imageView: ImageView? = null
        init {
            textView = itemView.findViewById(R.id.photo_tv)
            imageView = itemView.findViewById(R.id.photo_iv)
            itemView.setOnClickListener(View.OnClickListener {
                val uri: Uri? = getLocalBitmapUri(imageView!!)
                if (uri != null) {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri).putExtra(Intent.EXTRA_TEXT, "That is my image!")
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                        .type = "image/*"
                    startActivity(context, Intent.createChooser(shareIntent, "Share Image" ), null)
                }
            })
        }

        private fun getLocalBitmapUri(imageView: ImageView): Uri? {
            val drawable = imageView.drawable
            var bmp: Bitmap? = null
            bmp = if (drawable is BitmapDrawable) {
                (imageView.drawable as BitmapDrawable).bitmap
            } else {
                return null
            }
            var bmpUri: Uri? = null
            try {
                val file = File(
                    context.getExternalFilesDir(
                        Environment.DIRECTORY_DOWNLOADS
                    ), "share_image_" + System.currentTimeMillis() + ".png"
                )
                file.parentFile?.mkdirs()
                val out = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.close()
                bmpUri = FileProvider.getUriForFile(context, "com.example.vsomaku.provider", file)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bmpUri
        }
    }
}