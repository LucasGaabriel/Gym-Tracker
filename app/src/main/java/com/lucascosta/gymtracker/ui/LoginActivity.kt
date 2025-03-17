package com.lucascosta.gymtracker.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.databinding.ActivityLoginBinding

/**
 * Activity responsável pela tela de login com Google utilizando Firebase Authentication.
 * Verifica se o usuário já está autenticado, e caso não, permite que o usuário se autentique via Google.
 * Após a autenticação bem-sucedida, o usuário é redirecionado para a [MainActivity].
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        // Verifica se já tem um usuário logado
        if (auth.currentUser != null) {
            Log.d(
                TAG, String.format(
                    R.string.user_already_authenticated.toString(), auth.currentUser?.email
                )
            )
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        oneTapClient = Identity.getSignInClient(this)

        signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false).build()
        ).build()

        binding.btnLoginGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_login_google) {
            signInWithGoogle()
        }
    }

    /**
     * Inicia o processo de login via Google, pedindo ao usuário para se autenticar.
     *
     * Em caso de sucesso, o processo de autenticação do Firebase é iniciado.
     */
    private fun signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener { result ->
            googleSignInLauncher.launch(
                IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
            )
        }.addOnFailureListener { e ->
            Log.e(TAG, R.string.google_signin_failed.toString(), e)
        }
    }

    /**
     * Lançador para o processo de login via Google.
     * Após o sucesso da autenticação, converte o ID Token do Google para um credencial do Firebase.
     */
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        firebaseAuthWithGoogle(idToken)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, R.string.google_signin_failed.toString(), e)
                }
            }
        }

    /**
     * Converte o ID Token do Google para uma credencial do Firebase e realiza a autenticação.
     *
     * Caso a autenticação seja bem-sucedida, o usuário é redirecionado para a [MainActivity].
     *
     * @param idToken O ID Token recebido da autenticação do Google.
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, R.string.google_signin_with_credential_success.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.w(
                    TAG, R.string.google_signin_with_credential_failure.toString(), task.exception
                )
            }
        }
    }
}
