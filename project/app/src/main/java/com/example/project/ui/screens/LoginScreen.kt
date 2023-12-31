package com.example.project.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.ui.screens.common.CustomDialog
import com.example.project.ui.screens.common.LoginField
import com.example.project.ui.screens.common.PasswordField
import com.example.project.ui.screens.models.LoginViewModel
import com.example.project.ui.states.LoginAttemptStatus
import com.example.project.ui.theme.ProjectTheme

@Composable
fun LoginScreen(
    onSignUpTextClicked: (Int) -> Unit,
    proceedToPersonalScreen: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val loginUiState by loginViewModel.uiState.collectAsState()

    if(loginUiState.loginAttemptStatus != LoginAttemptStatus.SUCCESS) {
        LoginAttemptStatusDialog(
            loginAttemptStatus = loginUiState.loginAttemptStatus,
            onAcknowledge = {
                    loginViewModel.resetView()
            }
        )
    }
    else {
        proceedToPersonalScreen()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_design_app_icon),
            contentDescription = null,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(20.dp))

        val loginInteractionsource = remember {
            MutableInteractionSource()
        }
        val passwordInteractionsource = remember {
            MutableInteractionSource()
        }

        GuideText(
            loginInteractionsource = loginInteractionsource,
            passwordInteractionsource = passwordInteractionsource,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LoginField(
            userLogin = loginViewModel.userTypes.login,
            interactionSource = loginInteractionsource,
            onUpdateUserLogin = { loginViewModel.userTypes.updateLogin(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PasswordField(
            label = stringResource(id = R.string.password),
            userPassword = loginViewModel.userTypes.password,
            interactionSource = passwordInteractionsource,
            onUpdateUserPassword ={ loginViewModel.userTypes.updatePassword(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { loginViewModel.checkLoginAttempt() },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
        ) {
            Text(stringResource(id = R.string.login))
        }

        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.sign_up_invitation)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onClick = { onSignUpTextClicked(it) }
        )
    }
}

@Composable
fun GuideText(
    loginInteractionsource: MutableInteractionSource,
    passwordInteractionsource: MutableInteractionSource,
) {
    var guideText = remember {
        mutableStateOf("")
    }
    if(loginInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.login_requirements)
    } else if (passwordInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.password_requirements)
    } else {
        guideText.value = ""
    }

    val textAlpha: Float by animateFloatAsState(
        targetValue = if (loginInteractionsource.collectIsFocusedAsState().value or
                    passwordInteractionsource.collectIsFocusedAsState().value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 350,
            easing = LinearEasing,
        )
    )

    Text(
        text = guideText.value,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .alpha(textAlpha)
    )
}

@Composable
fun LoginAttemptStatusDialog(
    loginAttemptStatus: LoginAttemptStatus,
    onAcknowledge: () -> Unit,
    modifier: Modifier = Modifier
){
    if (loginAttemptStatus != LoginAttemptStatus.UNKNOWN) {
        var dialogTitle = ""
        var dialogDescription = ""

        when(loginAttemptStatus) {
            LoginAttemptStatus.BAD_CREDENTIALS -> {
                dialogTitle = stringResource(id = R.string.bad_credentials)
                dialogDescription = stringResource(id = R.string.bad_credentials_desc)
            }

            LoginAttemptStatus.USER_DOES_NOT_EXIST -> {
                dialogTitle = stringResource(id = R.string.user_does_not_exist)
                dialogDescription = stringResource(id = R.string.user_does_not_exist_desc)
            }

            LoginAttemptStatus.WRONG_PASSWORD -> {
                dialogTitle = stringResource(id = R.string.wrong_password)
                dialogDescription = stringResource(id = R.string.wrong_password_desc)
            }

            LoginAttemptStatus.SUCCESS -> {
                dialogTitle = stringResource(id = R.string.success)
                dialogDescription = stringResource(id = R.string.login_success_desc)
            }

            else -> {}
        }

        CustomDialog(
            title = dialogTitle,
            description = dialogDescription,
            onAcknowledge = onAcknowledge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ProjectTheme {
        LoginScreen({}, {})
    }
}