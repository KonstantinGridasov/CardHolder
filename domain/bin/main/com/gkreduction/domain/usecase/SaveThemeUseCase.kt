package com.gkreduction.domain.usecase

import com.gkreduction.domain.repository.SharedService
import io.reactivex.Observable

class SaveThemeUseCase(private val service: SharedService) : UseCase<Boolean, Int>() {
    override fun buildObservable(params: Int?): Observable<Boolean> {
        return service.saveTheme(params)
    }

}