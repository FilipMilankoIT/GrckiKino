package com.mozzartbet.core

import com.mozzartbet.core.model.Draw
import com.mozzartbet.core.model.RepositoryResult

interface Repository {

    suspend fun getUpcomingKinoDraws(itemNum: Int): RepositoryResult<List<Draw>>

    suspend fun getKinoDraw(drawId: Int): RepositoryResult<Draw>

    suspend fun getKinoDrawResults(fromDate: String, toDate: String): RepositoryResult<List<Draw>>
}