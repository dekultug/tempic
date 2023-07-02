package com.example.devfeandroid.presentation.store

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.product.Product
import com.example.devfeandroid.databinding.DescriptionItemBinding
import com.example.devfeandroid.databinding.ProductItemBinding
import com.example.devfeandroid.databinding.ServiceItemBinding
import com.example.devfeandroid.databinding.SliderBannerStoreItemBinding
import com.example.devfeandroid.databinding.SliderServiceStoreItemBinding
import com.example.devfeandroid.databinding.TitleReviewItemBinding
import com.example.devfeandroid.extensions.getAppString
import com.example.devfeandroid.presentation.store.banner.SliderBannerAdapter
import com.example.devfeandroid.presentation.store.serice.PageServiceAdapter
import com.example.devfeandroid.presentation.store.serice.ServiceDisplay

class StoreAdapter constructor(
    private val productAdapter: ProductAdapter,

    private val bannerAdapter: SliderBannerAdapter,

    private var serviceAdapter: PageServiceAdapter

) : ListAdapter<Any, RecyclerView.ViewHolder>(StoreDiffCallBack()) {

    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_SERVICE = 1
        private const val TYPE_DESCRIPTION = 2
        private const val TYPE_PRODUCT = 3
        private const val TYPE_CATEGORY_CARE = 4
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_BANNER
            1 -> TYPE_SERVICE
            2 -> TYPE_DESCRIPTION
            3 -> TYPE_PRODUCT
            4 -> TYPE_CATEGORY_CARE
            else -> TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class BannerVH(private val binding: SliderBannerStoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.vpSliderBannerStore.adapter = bannerAdapter
        }

        fun onBind(data: List<String>) {
            bannerAdapter.submitList(data)
            binding.vpSliderBannerStore.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvSliderStoreNumberPage.text =
                        getAppString(R.string.page_slider, position)
                }
            })
        }
    }

    inner class ServiceVH(private val binding: SliderServiceStoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.vpSliderServiceStore.adapter = serviceAdapter
        }

        fun onBind(data: List<ServiceDisplay>) {
        }
    }

    inner class DescriptionProduct(private val binding: DescriptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    inner class ProductVH(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rvProduct.adapter = productAdapter
        }

        fun onBind(data: List<Product>) {
            productAdapter.submitList(data)
        }
    }
}