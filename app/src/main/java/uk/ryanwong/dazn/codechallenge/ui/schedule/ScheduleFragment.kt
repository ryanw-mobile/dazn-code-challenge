package uk.ryanwong.dazn.codechallenge.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import uk.ryanwong.dazn.codechallenge.DaznApp
import uk.ryanwong.dazn.codechallenge.base.BaseFragment
import uk.ryanwong.dazn.codechallenge.data.model.Schedule
import uk.ryanwong.dazn.codechallenge.databinding.FragmentScheduleBinding
import uk.ryanwong.dazn.codechallenge.util.setupRefreshLayout

class ScheduleFragment : BaseFragment() {

    override val viewModel by viewModels<ScheduleViewModel> {
        ScheduleViewModelFactory(
            (requireContext().applicationContext as DaznApp).apiRepository
        )
    }
    private lateinit var binding: FragmentScheduleBinding
    private val scheduleAdapter = ScheduleAdapter().apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        // This eliminates flickering
        setHasStableIds(true)

        // This overrides the adapter's intention to scroll back to the top
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                viewModel.listState?.let {
                    // layoutManager.scrollToPositionWithOffset(position, 0)
                    binding.recyclerview.layoutManager?.onRestoreInstanceState(it)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The purpose of LifecycleObserver is to eliminate writing the boilerplate code
        // to load and cleanup resources in onCreate() and onDestroy()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerview.apply {
            adapter = scheduleAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        this.setupRefreshLayout(binding.refreshlayout) { viewModel.refreshList() }
    }

    override fun onStart() {
        super.onStart()

        viewModel.showLoading.observe(viewLifecycleOwner, { isRefreshing ->
            binding.refreshlayout.isRefreshing = isRefreshing
        })

        viewModel.showNoData.observe(viewLifecycleOwner, { isShowNoData ->
            binding.textviewNodata.visibility = when (isShowNoData) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        })

        viewModel.listContents.observe(viewLifecycleOwner, {
            viewModel.saveListState(binding.recyclerview.layoutManager?.onSaveInstanceState())
            scheduleAdapter.submitList(it as List<Schedule>)
        })
    }
}