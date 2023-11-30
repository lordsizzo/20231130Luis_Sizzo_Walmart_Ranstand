package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.CheckConnection
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.click
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.settingsGrid
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.settingsLinearVertical
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.snackCustom
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.UIStateFlow
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.remote.Coutries
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.view.adapters.CountriesAdapter
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.databinding.ActivityMainBinding
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.view_model.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private var howShowIt = true
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOberserver()
        initViews()
    }

    private fun initViews() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getCountries()
        }

        binding.listView.click {
            howShowIt = false
            viewModel.getStateView(false)
        }
        binding.tableView.click {
            howShowIt = true
            viewModel.getStateView(true)
        }
    }

    private fun initOberserver() {
        val networkConnection = CheckConnection(applicationContext)
        viewModel.stateView.observe(this) {
            howShowIt = it
            if (it) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.listView.visibility = View.VISIBLE
                binding.tableView.visibility = View.GONE

            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.listView.visibility = View.GONE
                binding.tableView.visibility = View.VISIBLE
            }
        }
        viewModel.getStateView(howShowIt)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.getCoutriesResponse.observe(this) {
                    it.let { result ->
                        try {
                            when (result) {
                                is UIStateFlow.LOADING -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "Loading Details...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is UIStateFlow.SUCCESS<*> -> {

                                    val country = result.response as? List<Coutries>
                                    country?.let {
                                        CountriesAdapter(it).apply {
                                            if (howShowIt)
                                                binding.recyclerView.settingsLinearVertical(this)
                                            else
                                                binding.recyclerView.settingsGrid(this)
                                            binding.swipeRefresh.isRefreshing = false
                                        }
                                    } ?: showError("Error at casting")
                                }
                                is UIStateFlow.ERROR -> {
                                    result.error.localizedMessage?.let { error -> showError(error) }
                                }
                            }
                        } catch (e: Exception) {
                            showError(e.toString())
                        }
                    }
                }
            } else {
                binding.swipeRefresh.isRefreshing = false
                binding.root.snackCustom("No Internet Connected")
            }
        }
        viewModel.getCountries()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
                binding.swipeRefresh.isRefreshing = false
            }
            .create()
            .show()
    }
}