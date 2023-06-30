package com.gkreduction.domain.repository

import io.reactivex.Observable

interface SharedService {
    fun saveTheme(theme: Int?): Observable<Boolean>
    fun getTheme(): Observable<Int>
}