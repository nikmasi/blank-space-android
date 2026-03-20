package com.example.blankspace.viewModels

import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.AdminRepository
import com.example.blankspace.data.retrofit.models.PesmePoIzvodjacimaResponse
import com.example.blankspace.data.retrofit.models.StatistikaResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AdminStatistikaViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AdminRepository = mockk()
    private lateinit var viewModel: AdminStatistikaViewModel

    @Test
    fun `fetchAdminStatistika success updates uiState`() = runTest {
        val fakeResponse = StatistikaResponse(
            20,32,10,12,"gica",
            4,"gojko",5,40
        )

        coEvery { repository.getStatistika() } returns fakeResponse

        viewModel = AdminStatistikaViewModel(repository)

        viewModel.fetchAdminStatistika()

        val state = viewModel.uiState.value

        assertThat(state.informacije).isEqualTo(fakeResponse)
        assertThat(state.isRefreshing).isFalse()
        assertThat(state.error).isNull()
    }

    @Test
    fun `fetchAdminStatistika error updates uiState with error`() = runTest {
        coEvery { repository.getStatistika() } throws Exception("Network error")

        viewModel = AdminStatistikaViewModel(repository)

        viewModel.fetchAdminStatistika()

        val state = viewModel.uiState.value

        assertThat(state.informacije).isNull()
        assertThat(state.isRefreshing).isFalse()
        assertThat(state.error).isEqualTo("Network error")
    }

    @Test
    fun `fetchPesmePoIzvodjacima success updates state`() = runTest {
        val fakeList = listOf(
            PesmePoIzvodjacimaResponse("za kraj","dzej")
        )

        coEvery { repository.getPesmePoIzvodjacima() } returns fakeList

        viewModel = AdminStatistikaViewModel(repository)

        viewModel.fetchPesmePoIzvodjacima()

        val state = viewModel.uiStatePesmePoIzvodjacima.value

        assertThat(state.pesmePoIzvodjacima).isEqualTo(fakeList)
        assertThat(state.isRefreshing).isFalse()
        assertThat(state.error).isNull()
    }

    @Test
    fun `fetchStihoviPoPesmama error updates state`() = runTest {
        coEvery { repository.getStihoviPoPesmama() } throws Exception("Server error")

        viewModel = AdminStatistikaViewModel(repository)

        viewModel.fetchStihoviPoPesmama()

        val state = viewModel.uiStateStihoviPoPesmama.value

        assertThat(state.stihoviPoPesmama).isNull()
        assertThat(state.isRefreshing).isFalse()
        assertThat(state.error).isEqualTo("Server error")
    }
}