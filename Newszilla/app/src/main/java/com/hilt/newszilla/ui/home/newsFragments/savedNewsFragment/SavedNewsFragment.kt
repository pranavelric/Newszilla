package com.hilt.newszilla.ui.home.newsFragments.savedNewsFragment

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hilt.newszilla.R
import com.hilt.newszilla.adapters.SavedNewsAdapter
import com.hilt.newszilla.ui.base.BaseHomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.saved_news_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment : BaseHomeFragment(R.layout.saved_news_fragment) {


    @Inject
    lateinit var newsAdapter: SavedNewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        newsAdapter.setViewModel(homeViewModel)
        setAdapter()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleNewsFragment,
                bundle
            )

        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT

        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                homeViewModel.deleteArticle(article)

                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        homeViewModel.saveArticle(article)
                    }
                }.show()


            }

            private val deleteIcon =
                ContextCompat.getDrawable(activity!!, R.drawable.ic_baseline_delete_24)

            private val intrinsicWidth = deleteIcon?.intrinsicWidth
            private val intrinsicHeight = deleteIcon?.intrinsicHeight
            private val background = ColorDrawable()
            private val backgroundColor = Color.parseColor("#f44336")
            private val clearPaint =
                Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top
                val isCanceled = dX == 0f && !isCurrentlyActive

                if (isCanceled) {
                    clearCanvas(
                        c,
                        itemView.right + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    return
                }

                // Draw the red delete background
                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                // Calculate position of delete icon
                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeight!!) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth!!
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon?.setBounds(
                    deleteIconLeft,
                    deleteIconTop,
                    deleteIconRight,
                    deleteIconBottom
                )
                deleteIcon?.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            private fun clearCanvas(
                c: Canvas?,
                left: Float,
                top: Float,
                right: Float,
                bottom: Float
            ) {
                c?.drawRect(left, top, right, bottom, clearPaint)
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(saved_news_recycler_view)
        }





        homeViewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })



refresh_layout.setOnRefreshListener {
    setDiffList()
        }


    }

    private fun setDiffList() {

        refresh_layout.isRefreshing = false
        saved_news_recycler_view.scheduleLayoutAnimation()
        val list = newsAdapter.differ.currentList
        val temp = list.toMutableList()
        newsAdapter.differ.submitList(temp)
        temp.clear()
        for (i in list) {
            temp.add(i)
            newsAdapter.differ.submitList(temp)
        }


    }

    private fun setAdapter() {


        saved_news_recycler_view.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }


}