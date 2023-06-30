package com.gkreduction.data.shared

import com.gkreduction.data.shared.datasource.SharedDataSource
import com.gkreduction.domain.repository.SharedService
import io.reactivex.Observable

class SharedServiceImpl(val source: SharedDataSource) : SharedService {
    override fun saveTheme(theme: Int?): Observable<Boolean> {
        return source.saveTheme(theme)
    }

    override fun getTheme(): Observable<Int> {
        return source.getTheme()
    }
}