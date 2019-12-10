package com.vannhat.recyclerslidetabdemo.slide

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vannhat.recyclerslidetabdemo.MainActivity
import com.vannhat.recyclerslidetabdemo.R
import com.vannhat.recyclerslidetabdemo.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_group, container, false)
    }

    private val data = (1..20).toList().map { it.toString() } as ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        // Setting the padding such that the items will appear in the middle of the screen
        val padding: Int =
            ScreenUtils.getScreenWidth(this.requireContext()) / 2 - ScreenUtils.dpToPx(
                this.requireContext(),
                40
            )
        rv_list.setPadding(padding, 0, padding, 0)

        // Setting layout manager
        rv_list.layoutManager = SliderLayoutManager(this.requireContext()).apply {
            callback = object : SliderLayoutManager.ItemSelectedListener {
                override fun onItemSelected(position: Int) {
                    tv_selected_item.text = data[position]
                }
            }
        }

        rv_list.adapter = SlideAdapter().apply {
            setData(data)
            callback = object : SlideAdapter.Callback {
                override fun onItemClicked(position: Int) {
                    rv_list.smoothScrollToPosition(position)
                }

            }
        }
        rv_list.setOnTouchListener { v, event ->
            Log.d("ccccc", event.action.toString())
            if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_DOWN) {
                (requireActivity() as? MainActivity)?.setSwipeableViewPager(false)
            } else {
                (requireActivity() as? MainActivity)?.setSwipeableViewPager(true)
            }
            false
        }

    }

    companion object {
        const val TAG = "GROUP"
        fun newInstance() =
            GroupFragment()
    }
}
