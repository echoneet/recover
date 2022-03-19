package dev.echoneet.recover.presentation.issuelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.repository.IssueRepository
import dev.echoneet.recover.presentation.model.ViewState
import dev.echoneet.recover.presentation.model.ViewStatus
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IssueListViewModel @Inject constructor(private val issueRepository: IssueRepository) :
    ViewModel() {
    private val _issueList = MutableLiveData<List<Issue>>()
    val issueList = _issueList
    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState


    init {
        listAllIssue()
    }

    private fun listAllIssue() {
        viewModelScope.launch {
            val result = issueRepository.getIssueList()
            if (result.status == ResultWithStatus.Status.ERROR) {
                val newViewState = ViewState(
                    ViewStatus.ERROR,
                    result.message ?: "unknown error"
                )
                _viewState.value = newViewState
            }

            _issueList.value = result.data?.reversed() ?: ArrayList()
        }
    }

    public fun getNewData() {
        listAllIssue();
    }

    public fun createNewIssue(title: String) {
        viewModelScope.launch {
            val result = issueRepository.createNewIssue(
                Issue(
                    id = 0,
                    code = "",
                    title = title,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                    status = ""
                )
            )


            if (result.status == ResultWithStatus.Status.ERROR || result.data == null) {
                val newViewState = ViewState(
                    ViewStatus.ERROR,
                    result.message ?: "unknown error"
                )
                _viewState.value = newViewState

            } else {
                val currentIssueList = _issueList.value?.toMutableList() ?: mutableListOf()
                currentIssueList.add(
                    0,
                    result.data
                )
                _issueList.value = currentIssueList
            }
        }
    }
}