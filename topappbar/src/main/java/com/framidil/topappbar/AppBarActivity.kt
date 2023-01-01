package com.framidil.topappbar

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.framidil.topappbar.databinding.ActivityAppBarBinding

class AppBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBarBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_app_bar_menu, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as LinearLayout
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_search -> {
            showToast("Поиск")
            true
        }
        R.id.menu_settings -> {
            showToast("Настройки")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        showToast("Поиск. context: ${this as Context}")

        applicationContext.run {
            showToast("Поиск. context")
        }

        onBackPressed();
        return true
    }

    private fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
