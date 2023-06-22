package com.example.devfeandroid.presentation.home.detailvideo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.devfeandroid.R
import com.example.devfeandroid.data.model.producthome.Products
import com.example.devfeandroid.databinding.DetailVideoFragmentBinding
import com.example.devfeandroid.extensions.*
import com.example.devfeandroid.presentation.BaseFragment
import com.example.devfeandroid.presentation.home.review.video.ReviewVideoFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class DetailVideoFragment : BaseFragment() {

    companion object {
        const val DETAIL_PRODUCT_ITEM = "DETAIL_PRODUCT_ITEM"
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[DetailVideoViewModel::class.java]
    }

    private var player: ExoPlayer? = null

    private var binding: DetailVideoFragmentBinding? = null

    private var isFake = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DetailVideoFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity!!.showBottomNav(false)
        setEventView()
        oberservale()
    }

    override fun onResume() {
        super.onResume()
        player!!.play()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    private fun setEventView() {
        binding!!.ivDetailVideoFull.setOnSafeClick {
            binding!!.clDetailVideoShowAction.gone()
        }

        binding!!.flDetailVideoSub.setOnSafeClick {
            binding!!.clDetailVideoShowAction.show()
        }

        binding!!.ivDetailVideoBack.setOnSafeClick {
            parentFragmentManager.popBackStack()
        }

        binding!!.fvDetailVideo.setFollow { follow ->
            if (follow) {
                mainViewModel.follow(viewModel.userInfo?.getUserId() ?: STRING_DEFAULT)
            } else {
                mainViewModel.unFollow(viewModel.userInfo?.getUserId() ?: STRING_DEFAULT)
            }
        }

        binding!!.ivDetailVideoReplyComment.setOnSafeClick {
            replaceFragmentInFragment(
                containerID = R.id.clDetailVideoRoot,
                fragment = ReviewVideoFragment(),
                keepToBackStack = true,
                bundle = bundleOf(
                    ReviewVideoFragment.REVIEW_PRODUCT_KEY to viewModel.product.value
                )
            )
        }

        binding!!.ivDetailVideoLike.setOnSafeClick {
            isFake = !isFake
            if (isFake){
                binding!!.ivDetailVideoLike.setImageResource(R.drawable.ic_heart_select)
            }else{
                binding!!.ivDetailVideoLike.setImageResource(R.drawable.ic_heart_white)
            }
        }
    }

    private fun oberservale() {
        lifecycleScope.launchWhenCreated {
            viewModel.product.collect {
                viewModel.userInfo = it.userInfo
                mainViewModel.getStatusFollow(it.getUserInfo().getUserId())
                updateUI(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            mainViewModel.followState.collect {
                binding!!.fvDetailVideo.isFollow(it)
            }
        }
    }

    private fun updateUI(data: Products) {
        binding!!.apply {
            ivDetailVideoImageUser.loadImageUrl(data.getAvatarUserInfo())
            tvDetailVideoNameUser.text = data.getUserName()
            tvDetailVideoDescription.text = data.getTitleProductsDisplay()
            tvDetailViewCountLike.text = data.getCountNumberHeart().toString()

            if (player == null) {
                player = ExoPlayer.Builder(requireContext()).build()
            }
            binding!!.pvDetailVideo.player = player;

            val mediaUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
            val mediaItem: MediaItem = MediaItem.fromUri(mediaUri)
            player!!.setMediaItem(mediaItem)
            player!!.prepare()
            player!!.play()
        }
    }
}
