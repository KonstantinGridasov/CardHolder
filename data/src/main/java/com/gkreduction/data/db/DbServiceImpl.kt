package com.gkreduction.data.db

import com.gkreduction.data.db.dao.CardDao
import com.gkreduction.data.mapper.DbMapper
import com.gkreduction.domain.repository.DbService

class DbServiceImpl(
    private val cardDao: CardDao,
    private val dbMapper: DbMapper
) : DbService
