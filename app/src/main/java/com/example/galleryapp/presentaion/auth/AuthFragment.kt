package com.example.galleryapp.presentaion.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding:FragmentAuthBinding
    private val viewModel by viewModels<AuthViewModel>()

    var login = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLoginMask()
        observeLoginError()
        observePasswordError()


        binding.button.setOnClickListener {

            viewModel.isFieldValid(login, binding.passwordEdt.text.toString())

            //findNavController().navigate(R.id.action_authFragment_to_mainFragment)
//            binding.loginTil.error = "Тест"
//            binding.button.isLoading = true

//            Snackbar.make(binding.root,
//                resources.getString(R.string.auth_error_text),
//                Snackbar.LENGTH_LONG)
//                .setAnchorView(binding.button)
//                .show()
        }
    }

    private fun initLoginMask(){
       installOn(
           binding.loginEdt,
           PHONE_MASK,
           object : MaskedTextChangedListener.ValueListener{
               override fun onTextChanged(
                   maskFilled: Boolean,
                   extractedValue: String,
                   formattedValue: String,
                   tailPlaceholder: String
               ) {
                   login = extractedValue
                   //viewModel.setLogin(extractedValue)
               }
           }
       )
    }

  private fun observeLoginError(){
      viewModel.mutableLoginError.observe(viewLifecycleOwner){loginError ->
          when(loginError){
              LoginError.EMPTY -> {
                  binding.loginTil.error = getString(R.string.login_error_empty)
              }
              LoginError.NOT_VALID -> {
                  binding.loginTil.error = getString(R.string.login_error_not_valid)
              }
              LoginError.VALID -> {
                  binding.loginTil.error = null
              }
              else -> {
                  //do nothing
              }
          }
      }
  }


    private fun observePasswordError(){
        viewModel.mutablePasswordError.observe(viewLifecycleOwner){passwordError ->
            when(passwordError){
                PasswordError.EMPTY -> {
                    binding.passwordTil.error = getString(R.string.password_error_empty)
                }
                PasswordError.NOT_VALID -> {
                    binding.passwordTil.error = getString(R.string.password_error_not_valid)
                }
                PasswordError.VALID -> {
                    binding.loginTil.error = null
                }
                else -> {
                    //do nothing
                }
            }
        }
    }






    companion object{
        const val PHONE_MASK = "+ 7 ([000]) [000] [00] [00]"
    }




}