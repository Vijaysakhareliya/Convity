package com.convity.di

import com.convity.presentation.editprofile.viewmodel.EditProfileViewModel
import com.convity.presentation.home.viewmodel.*
import com.convity.presentation.login.viewmodel.LoginViewModel
import com.convity.presentation.newpost.createpost.viewmodel.CreatePostViewModel
import com.convity.presentation.newpost.filterpost.viewmodel.FilterPostViewModel
import com.convity.presentation.newpost.sendpost.viewmodel.SendPostViewModel
import com.convity.presentation.register.viewmodel.RegisterViewModel
import com.convity.presentation.splash.viewmodel.SplashViewModel
import com.convity.presentation.verification.viewmodel.VerificationSuccessViewModel
import com.convity.presentation.verification.viewmodel.VerificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { VerificationViewModel(get()) }
    viewModel { VerificationSuccessViewModel() }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HomeFragmentViewModel(get()) }
    viewModel { HomeSearchFragmentViewModel(get()) }
    viewModel { SearchFragmentViewModel(get()) }
    viewModel { NotificationFragmentViewModel(get()) }
    viewModel { ProfileFragmentViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { MyPostFragmentViewModel(get()) }
    viewModel { SavedPostFragmentViewModel(get()) }
    viewModel { BusinessDetailFragmentViewModel(get()) }
    viewModel { CreatePostViewModel(get()) }
    viewModel { FilterPostViewModel(get()) }
    viewModel { SendPostViewModel(get()) }
}