package com.rst.nfc

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rst.nfc.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var binding: ActivityMainBinding
    private var currentNfcMode = NfcMode.REMOVE_PASSWORD

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNfc()
        setupViews()

        updateView()
    }

    private fun setupViews() = with(binding) {
        nfcModeButton.setOnClickListener {
            MaterialAlertDialogBuilder(this@MainActivity)
                .setSingleChoiceItems(
                    NfcModeHelper.nfcModesString.values.toTypedArray(),
                    NfcModeHelper.nfcModes.indexOf(currentNfcMode)
                ) { dialog, which ->
                    currentNfcMode = NfcModeHelper.nfcModes[which]
                    updateView()
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun updateView() {
        with(binding) {
            binding.nfcModeTextView.text = NfcModeHelper.nfcModesString[currentNfcMode]
            when (currentNfcMode) {
                NfcMode.READ_NDEF, NfcMode.READ_FULL_RAW -> {
                    passwordTextInputLayout.isVisible = false
                    textTextInputLayout.isVisible = false
                }
                NfcMode.WRITE_WITHOUT_PASSWORD -> {
                    passwordTextInputLayout.isVisible = false
                    textTextInputLayout.isVisible = true
                }
                NfcMode.WRITE_WITH_PASSWORD -> {
                    passwordTextInputLayout.isVisible = true
                    textTextInputLayout.isVisible = true
                }
                NfcMode.SET_PASSWORD -> {
                    passwordTextInputLayout.isVisible = true
                    textTextInputLayout.isVisible = false
                }
                NfcMode.REMOVE_PASSWORD -> {
                    passwordTextInputLayout.isVisible = true
                    textTextInputLayout.isVisible = false
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logIntent(intent)

        val tag: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        Timber.d("techList: ${tag?.techList?.toList()}")

        if (tag == null) {
            binding.resTextView.text = "Тег не найден"
            return
        }

        val password = binding.passwordTextInputEditText.text?.toString() ?: ""
        val text = binding.textTextInputEditText.text?.toString() ?: ""

        lifecycleScope.launch(Dispatchers.IO) {
            doNfcWork(tag, intent, password, text)
        }
    }

    private suspend fun doNfcWork(
        tag: Tag,
        intent: Intent,
        password: String,
        text: String
    ) {
        val result = try {
            when (currentNfcMode) {
                NfcMode.READ_NDEF -> intent.dataString ?: NfcHelper(tag).readAsNdef()
                NfcMode.READ_FULL_RAW -> NfcHelper(tag).readRawAllBytes()
                NfcMode.WRITE_WITHOUT_PASSWORD -> NfcHelper(tag).writeWithoutPassword(text)
                NfcMode.WRITE_WITH_PASSWORD ->
                    NfcHelper(tag).writeWithPassword(text, password)
                NfcMode.SET_PASSWORD -> NfcHelper(tag).setPassword(password)
                NfcMode.REMOVE_PASSWORD -> NfcHelper(tag).removePassword(password)
            }
        } catch (e: Exception) {
            Timber.e(e)
            "Произошла ошибка"
        }

        withContext(Dispatchers.Main) {
            binding.resTextView.text = result
        }
    }

    private fun logIntent(intent: Intent?) {
        Timber.d("intent: $intent")
        intent ?: return

        Timber.d("intent.action: ${intent.action}")
        Timber.d("intent.extras: ${intent.extras}")
        Timber.d("intent.data: ${intent.data}")
        Timber.d("intent.dataString: ${intent.dataString}")
    }

    private fun setupNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)


        val intent = Intent(this, this::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT.let {
                if (Build.VERSION.SDK_INT >= 31) {
                    it or PendingIntent.FLAG_MUTABLE
                } else it
            }
        )

        setNfcLifecycleObservers()
    }

    private fun setNfcLifecycleObservers() {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        nfcAdapter?.enableForegroundDispatch(
                            this@MainActivity,
                            pendingIntent,
                            null,
                            null
                        )
                        Timber.d("nfcAdapter ENABLE")
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        nfcAdapter?.disableForegroundDispatch(this@MainActivity)
                        Timber.d("nfcAdapter DISABLE")
                    }
                    else -> {}
                }
            }
        })
    }
}