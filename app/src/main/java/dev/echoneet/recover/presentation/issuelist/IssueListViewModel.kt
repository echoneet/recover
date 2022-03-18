package dev.echoneet.recover.presentation.issuelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.repository.IssueRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IssueListViewModel @Inject constructor(private val issueRepository: IssueRepository) :
    ViewModel() {
    private val _issueList = MutableLiveData<ResultWithStatus<List<Issue>>>()
    val issueList: LiveData<ResultWithStatus<List<Issue>>> = _issueList


    init {
        listAllIssue()
    }

    private fun listAllIssue() {
        viewModelScope.launch {
            _issueList.value = issueRepository.getIssueList();

        }
    }

    public fun getNewData() {
        listAllIssue();
    }
}