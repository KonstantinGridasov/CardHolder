package com.gkreduction.domain.usecase

import io.reactivex.Observable

abstract class UseCase<Result, Params> {

    abstract fun buildObservable(params: Params?): Observable<Result>

    fun execute(params: Params? = null): Observable<Result> {
        return buildObservable(params)
    }

}
