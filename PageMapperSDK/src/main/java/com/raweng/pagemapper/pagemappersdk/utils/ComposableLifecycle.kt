package com.raweng.pagemapper.pagemappersdk.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun ComposableLifecycle(
    lifeCycleOwner: LifecycleOwner,
    onEvent: ((LifecycleOwner, Lifecycle.Event) -> Unit)? = null,
    onDisposeCalled: (() -> Unit?)? = null) {
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent?.invoke(source, event)
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            onDisposeCalled?.invoke()
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}