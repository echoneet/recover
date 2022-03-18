package dev.echoneet.recover.presentation.issuelist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.echoneet.recover.R
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.databinding.ActivityIssueListBinding

@AndroidEntryPoint
class IssueListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIssueListBinding
    private val viewModel: IssueListViewModel by viewModels()
    private lateinit var issueAdaptor: IssueAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssueListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        subscribeUi()
    }

    private fun init() {
        title = "issue list"
        val layoutManager = LinearLayoutManager(this)

        binding.issueRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.issueRecyclerView.context,
            layoutManager.orientation
        )

        binding.issueRecyclerView.addItemDecoration(dividerItemDecoration)

        issueAdaptor = IssueAdaptor(this, ArrayList())
        binding.issueRecyclerView.adapter = issueAdaptor

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNewData()
        }
    }

    private fun subscribeUi() {
        viewModel.issueList.observe(this, Observer {
            if (it.status == ResultWithStatus.Status.ERROR) {
                Snackbar.make(
                    binding.rootView,
                    it.message ?: "unknown error",
                    Snackbar.LENGTH_INDEFINITE
                ).setBackgroundTint(Color.RED)
                    .show()
            }

            issueAdaptor.updateData(it.data ?: listOf())

            binding.loading.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false;

        })
    }
}