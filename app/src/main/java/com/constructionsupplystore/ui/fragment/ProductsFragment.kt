package com.constructionsupplystore.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.common.basecommon.BaseCommonFragment
import com.constructionsupplystore.R
import com.data.ProductData
import com.constructionsupplystore.databinding.FragmentProductsBinding
import com.constructionsupplystore.ui.adapter.ProductAdapter
import com.constructionsupplystore.utils.ConstantsKey.KEY_OBJECT_PRODUCT_DATA
import com.constructionsupplystore.ui.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : BaseCommonFragment() {

    private lateinit var binding: FragmentProductsBinding
    private val productAdapter = ProductAdapter { data, boolean ->
        listener(data, boolean)
    }
    private val viewModel by viewModel<ProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter.setContext(requireContext())
        viewModel.resultsLiveData.observe(viewLifecycleOwner) {
            it?.let { productAdapter.updateData(it) }
        }
        setupView()
        setSearchView()
    }

    private fun setupView() {
        binding.recyclerView.adapter = productAdapter
    }

    private fun setSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun listener(productData: ProductData, boolean: Boolean) {
        if (boolean) {
            val fragment = ChangeProductFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_OBJECT_PRODUCT_DATA, productData)
                }
            }
            replaceFragment(R.id.flFragment, fragment)

        } else viewModel.deleteData(productData)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProduct()
    }


}