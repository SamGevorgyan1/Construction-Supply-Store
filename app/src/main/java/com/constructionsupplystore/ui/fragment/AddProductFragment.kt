package com.constructionsupplystore.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.common.BaseCommonFragment
import com.product.data.ProductData
import com.constructionsupplystore.databinding.FragmentAddProductBinding
import com.constructionsupplystore.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddProductFragment : BaseCommonFragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel by viewModel<ProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()

    }

    private fun setupListener() {
        binding.btnAddProduct.setOnClickListener {
            with(binding) {

                val editTexts = arrayOf(
                    edtProductName,
                    edtProductFirma,
                    edtProductCode,
                    edtProductCount,
                    edtProductInitialPrice,
                    edtProductPrice
                )

                val productData = ProductData(
                    edtProductName.text.toString(), edtProductFirma.text.toString(),
                    edtProductCode.text.toString(), edtProductCount.text.toString(),
                    edtProductInitialPrice.text.toString(), edtProductPrice.text.toString()
                )
                if (checkEdtText(editTexts)) {
                    viewModel.addProduct(productData)
                    editTexts.forEach { it.text?.clear() }
                    Toast.makeText(requireContext(), "Product saved", Toast.LENGTH_SHORT).show()
                } else {
                    editTexts.forEach {
                        if (it.text.isNullOrEmpty()) {
                            it.hint = "has no value"

                        }
                    }
                }
            }
        }
    }
}