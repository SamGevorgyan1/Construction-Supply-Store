package com.constructionsupplystore.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.constructionsupplystore.listener.OnItemListener
import com.product.data.ProductData
import com.constructionsupplystore.databinding.AdapterProductsBinding
import java.util.*

class ProductAdapter(val onItemListener: OnItemListener) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(), Filterable {

    private var items: MutableList<ProductData> = mutableListOf()
    private lateinit var context: Context
    private var filteredItems: MutableList<ProductData> = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(filteredItems[position])


    override fun getItemCount(): Int = filteredItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: List<ProductData>) {
        this.items.clear()
        items.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(private val binding: AdapterProductsBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(items: ProductData) {
            with(binding) {
                productCode.text = items.code.toString()
                productNameTitle.text = items.name
                productCount.text = items.count.toString()
                productPrice.text = items.price.toString()
                productInitialPrice.text = items.initialPrice.toString()
                firmaName.text = items.firma
            }
        }

        init {
            binding.codeTitle.setOnClickListener(this)
            binding.productNameTitle.setOnClickListener(this)
            binding.productCountTitle.setOnClickListener(this)
            binding.initialPriceTitle.setOnClickListener(this)
            binding.btnDelete.setOnClickListener(this)
            binding.btnChange.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val product = filteredItems.getOrNull(position)
            when (v) {
                binding.btnDelete -> if (position != RecyclerView.NO_POSITION) {
                    if (product != null) {
                        onItemListener.onDeleteItemClick(position, product)
                    }
                    removeItem(position)
                }
                binding.btnChange -> if (position != RecyclerView.NO_POSITION) {
                    if (product != null) {
                        onItemListener.onChangeItemClick(position, product)
                    }
                }
                else -> Toast.makeText(context, "click not work", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("notifyDataSetChanged")
    override fun getFilter(): Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val query = constraint.toString().toLowerCase(Locale.getDefault())
            val filteredList = if (query.isBlank()) {
                items
            } else {
                items.filter {
                    it.name?.toLowerCase(Locale.getDefault())!!.contains(query)
                }
            }
            return FilterResults().apply {
                values = filteredList
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredItems = (results?.values as MutableList<ProductData>)
            notifyDataSetChanged()
        }
    }


}
