package com.workspace.core.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.workspace.core.data.dto.PokemonResult

class PokemonPagingSource(
    private val pokemonDataSource: PokemonDataSource
) : PagingSource<Int, PokemonResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: 0
        return try {
            val response = pokemonDataSource.fetchPokemonList(offset, params.loadSize)
            LoadResult.Page(
                data = response.results,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (response.next == null) null else offset + params.loadSize
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(state.config.pageSize)
        }
    }
}