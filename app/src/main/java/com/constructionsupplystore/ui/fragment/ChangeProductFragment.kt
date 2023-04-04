package com.constructionsupplystore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.common.BaseCommonFragment
import com.constructionsupplystore.R
import com.product.data.ProductData
import com.constructionsupplystore.databinding.FragmentChangeProductBinding
import com.constructionsupplystore.utils.ConstantsKey.KEY_OBJECT_PRODUCT_DATA
import com.constructionsupplystore.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProductFragment : BaseCommonFragment() {

    private lateinit var binding: FragmentChangeProductBinding
    private val viewModel by viewModel<ProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<ProductData>(KEY_OBJECT_PRODUCT_DATA)

        with(binding) {
            edtProductName.setText(product?.name)
            edtProductFirma.setText(product?.firma)
            edtProductCode.setText(product?.code)
            edtProductCount.setText(product?.count)
            edtProductInitialPrice.setText(product?.initialPrice)
            edtProductPrice.setText(product?.price)
        }

        binding.btnChangeProduct.setOnClickListener {
            viewModel.changeData(
                ProductData(
                    binding.edtProductName.text.toString(),
                    binding.edtProductFirma.text.toString(),
                    binding.edtProductCode.text.toString(),
                    binding.edtProductCount.text.toString(),
                    binding.edtProductInitialPrice.text.toString(),
                    binding.edtProductPrice.text.toString()
                ), product?.name ?: ""
            )
            replaceFragment(R.id.flFragment, ProductsFragment())
        }
    }
}
