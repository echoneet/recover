package dev.echoneet.recover.presentation.issuelist

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.echoneet.recover.R
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.databinding.ActivityIssueListBinding
import dev.echoneet.recover.presentation.model.ViewStatus


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

        val callback = IssueItemTouchHelperCallback(issueAdaptor)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.issueRecyclerView)

        issueAdaptor.setOnItemDismissListener {
            binding.loading.visibility = View.VISIBLE
            viewModel.cancelIssue(it)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNewData()
        }


    }

    private fun subscribeUi() {
        viewModel.issueList.observe(this) {
            issueAdaptor.updateData(it ?: listOf())

            binding.loading.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false;

            binding.createFAB.setOnClickListener {
                popupNewCreateIssueDialog()
            }

        }

        viewModel.viewState.observe(this) {
            if (it.status == ViewStatus.ERROR) {
                Snackbar.make(
                    binding.rootView,
                    it.errorMessage,
                    Snackbar.LENGTH_INDEFINITE
                ).setBackgroundTint(Color.RED)
                    .show()
            }
        }
    }

    private fun popupNewCreateIssueDialog() {
        val input = EditText(
            this
        )
        input.hint = "Issue title"

        AlertDialog.Builder(this)
            .setTitle("Create new issue")
            .setView(input)
            .setPositiveButton(
                "Submit"
            ) { _, _ ->
                val title = input.text.toString()
                viewModel.createNewIssue(title)
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}