package com.example.sportapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class MyObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun myFun(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("LIFECYCLE", event.toString());
    }
}