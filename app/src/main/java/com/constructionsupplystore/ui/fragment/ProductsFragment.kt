package com.constructionsupplystore.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.common.BaseCommonFragment
import com.constructionsupplystore.listener.OnItemListener
import com.constructionsupplystore.R
import com.product.data.ProductData
import com.constructionsupplystore.databinding.FragmentProductsBinding
import com.constructionsupplystore.ui.adapter.ProductAdapter
import com.constructionsupplystore.utils.ConstantsKey.KEY_OBJECT_PRODUCT_DATA
import com.constructionsupplystore.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : BaseCommonFragment(), OnItemListener {

    private lateinit var binding: FragmentProductsBinding
    private val productAdapter = ProductAdapter(this)
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

    override fun onChangeItemClick(position: Int, productData: ProductData) {
        val fragment = ChangeProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_OBJECT_PRODUCT_DATA, productData)
            }
        }
        replaceFragment(R.id.flFragment, fragment)
    }

    override fun onDeleteItemClick(position: Int, productData: ProductData) {
        viewModel.deleteData(productData)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProduct()
    }
}





