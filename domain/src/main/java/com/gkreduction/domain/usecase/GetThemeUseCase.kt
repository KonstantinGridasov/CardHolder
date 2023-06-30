package com.gkreduction.domain.usecase

import com.gkreduction.domain.repository.SharedService
import io.reactivex.Observable

class GetThemeUseCase(private val service: SharedService) : UseCase<Int, Void>() {
    override fun buildObservable(params: Void?): Observable<Int> {
        return service.getTheme()
    }

}