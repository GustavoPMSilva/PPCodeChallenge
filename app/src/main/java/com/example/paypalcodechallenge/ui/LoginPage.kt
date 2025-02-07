package com.example.paypalcodechallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.paypalcodechallenge.ui.components.LabelCheckbox
import com.example.paypalcodechallenge.ui.components.PasswordField
import com.example.paypalcodechallenge.ui.components.UsernameField
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: LoginViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.loginResponseState.observe(lifecycleOwner) {
            it?.let { response ->
                val message = if (response.success) {
                    "Login successful"
                } else {
                    response.errorMessage!!
                }

                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UsernameField(
                value = viewModel.loginDataState.value.username,
                onValueChanged = viewModel::onUsernameChanged,
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = viewModel.loginDataState.value.password,
                onValueChanged = viewModel::onPasswordChanged,
                onDone = viewModel::onLoginButtonPressed,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            LabelCheckbox(
                label = "Remember Me",
                onCheckedChange = viewModel::onRememberMeChanged,
                isChecked = viewModel.loginDataState.value.rememberMe
            )
            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.onLoginButtonPressed()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login"
                )
            }
        }

        if (viewModel.loadingState.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White.copy(alpha = 0.7f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}
