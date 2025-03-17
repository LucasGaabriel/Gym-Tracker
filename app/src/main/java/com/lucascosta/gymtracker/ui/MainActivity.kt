package com.lucascosta.gymtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.databinding.ActivityMainBinding

/**
 * A [MainActivity] gerencia a tela principal do aplicativo, configurando a navegação entre fragmentos
 * utilizando a [BottomNavigationView] e uma [Toolbar].
 * Essa Activity é responsável por configurar a navegação de forma fluída entre os fragmentos com o uso do NavController.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbarAndBottomNav()
    }

    /**
     * Método para configurar a navegação e a Toolbar.
     * Estabelece o relacionamento entre o [NavController], [BottomNavigationView] e [Toolbar],
     * permitindo a navegação fluida entre os fragmentos.
     */
    private fun setToolbarAndBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFrag.navController
        navView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}