package com.eric.startupmatching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.eric.startupmatching.databinding.ActivityLoginBinding
import com.eric.startupmatching.login.UserManager

private const val RC_SIGN_IN = 20
class LoginActivity : AppCompatActivity() {
    private val TAG = this.javaClass.name
//    private lateinit var spark: Spark
    private lateinit var binding: ActivityLoginBinding
    // FirebaseAuth
    private lateinit var auth: FirebaseAuth
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
//        spark = Spark(binding.root, Spark.ANIM_YELLOW_BLUE, 4000)
//        spark.startAnimation()
        // Initialize Firebase Auth
        auth = Firebase.auth
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("145352377830-dl5tt65kcv3vgcam6004feumvvj84cgu.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signInButton.setOnClickListener {
            signIn(mGoogleSignInClient)
        }
        viewModel.getUserData.observe(this, Observer {
            if(it == null){
                viewModel.createUser()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
        viewModel.status.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
        })

    }



    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("L", account.email.toString())
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d("Google sign in failed" , e.message.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, intent to main activity with the signed-in user's information
                val user = auth.currentUser
                if (user != null) {
                    UserManager.userToken = user.uid
                    viewModel.firebaseUser.value = user
                    viewModel.getUser(user.uid)
                }
//                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG,"signInWithCredential:failure", task.exception)
            }
        }
    }
    private fun paddingPicture(tv: TextView, pic: Int) {
        val drawable = resources.getDrawable(pic)
        drawable.setBounds(0, 0, 50, 50)
        tv.setCompoundDrawables(drawable, null, null, null)
    }
    // 8d:d0:4d:64:ee:51:e7:fb:ef:e7:6b:41:27:94:d4:04:a3:1a:53:f1



}