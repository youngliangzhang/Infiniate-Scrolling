package com.dmsoft.infinite.activities

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.dmsoft.infinite.App
import com.dmsoft.infinite.R
import com.dmsoft.infinite.adapter.ImageAdapter
import com.dmsoft.infinite.adapter.SpaceItemDecoration
import com.dmsoft.infinite.utils.PageRecyclerViewScrollListener
import com.dmsoft.infinite.utils.SimplePreferences
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        private const val IMAGE_BASE_URL = "https://dummyimage.com/300&text=%d"
    }

    @Inject
    lateinit var simplePreferences: SimplePreferences

    private var adapter: ImageAdapter? = null
    private var aryImageUrls = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.instance.appComponent.inject(this)

        setContentView(R.layout.activity_main)

        initWidget()
    }

    override fun getRootView(): ViewGroup? {
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_main, it)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.menu_all_count) {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.txt_all_count))
                    setMessage("${getString(R.string.txt_download_count)} ${simplePreferences.imageLoadCount}")
                }.show()
            }
        }
        return true
    }

    private fun initWidget() {
        getImageUrlAry(true)
        with(rv_images){
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addItemDecoration(SpaceItemDecoration(5))
            addOnScrollListener(object : PageRecyclerViewScrollListener() {
                override fun loadMoreItems() {
                    getImageUrlAry(false)
                }
            })
            this@MainActivity.adapter = ImageAdapter(this@MainActivity, aryImageUrls)
            adapter = this@MainActivity.adapter
        }
    }

    private fun getImageUrlAry(isFirstLoading: Boolean) {
        val startValue = aryImageUrls.size + 1
        for (index in startValue until (startValue + 50)) {
            aryImageUrls.add(String.format(Locale.getDefault(), IMAGE_BASE_URL, index))
        }
        if (!isFirstLoading)
            rv_images.post({ adapter?.notifyItemRangeInserted(startValue - 1, 50) })
    }
}
