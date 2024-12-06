package com.workspace.core.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.workspace.core.data.dto.PokemonResult
import com.workspace.core.domain.model.ServiceResult

class PokemonPagingSource(
    private val pokemonDataSource: PokemonDataSource
) : PagingSource<Int, PokemonResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: 0
        return when (val result = pokemonDataSource.fetchPokemonList(offset, params.loadSize)) {
            is ServiceResult.Success -> {
                LoadResult.Page(
                    data = result.data.results,
                    prevKey = if (offset == 0) null else offset - params.loadSize,
                    nextKey = if (result.data.next == null) null else offset + params.loadSize
                )
            }
            is ServiceResult.Error -> {
                Log.d("PokemonPagingSource", "에러이유: ${result.error.message}")
                LoadResult.Error(Throwable(result.error.message))
            }
            else -> LoadResult.Error(Throwable("Unknown error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(state.config.pageSize)
        }
    }
}