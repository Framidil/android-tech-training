package com.rst.example_image_picker

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rst.example_image_picker.databinding.ActivityMainBinding
import com.rst.example_image_picker.databinding.PhotoViewHolderBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val width: Int = Resources.getSystem().displayMetrics.widthPixels
        val height: Int = Resources.getSystem().displayMetrics.heightPixels
        Log.d("d", "width: ${width}")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = PhotoAdapter(
            width / 3,
            PhotoManager(this)
        )
    }

    class PhotoAdapter(
        private val imageHeight: Int,
        private val photoManager: PhotoManager,
    ) : RecyclerView.Adapter<ViewHolder>() {
        private val scope = MainScope()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = PhotoViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(
                binding = binding,
                scope = scope,
                imageHeight = imageHeight,
                photoManager = photoManager,
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount() = 33
    }
}

class ViewHolder(
    private val binding: PhotoViewHolderBinding,
    private val scope: CoroutineScope,
    private val imageHeight: Int,
    private val photoManager: PhotoManager,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.imageView.updateLayoutParams<ViewGroup.LayoutParams> {
            height = imageHeight
        }
    }

    fun bind(index: Int) {
        scope.launch {
            binding.imageView.setImageResource(R.drawable.load)
            val drawable = photoManager.get(index) ?: return@launch
            withContext(Dispatchers.Main) {

                binding.imageView.setImageDrawable(drawable)
            }
        }
    }
}
