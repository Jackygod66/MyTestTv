package com.example.mytesttv.BaseMvp

interface ResultCallback<T> {
    fun onSuccess(result : T)

    fun onFail(message : String)
}