package com.jyotirmay.smartshopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jyotirmay.smartshopping.R
import com.jyotirmay.smartshopping.data.local.ProductEntity
import com.jyotirmay.smartshopping.databinding.CartItemBinding

class ProductAdaptor(private val productList: List<ProductEntity>, private val onRemoveClicked: (Int) -> Unit) :
    RecyclerView.Adapter<ProductAdaptor.ProductViewHolder>() {

    inner class ProductViewHolder(item: CartItemBinding) : RecyclerView.ViewHolder(item.root) {
        val productImage: ImageView = item.imageProduct
        val productName: TextView = item.textProductName
        val productPrice: TextView = item.textProductPrice
        val removeProduct: ImageView = item.imageDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        Glide
            .with(holder.itemView.context)
            .load(product.image)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.productImage)

        holder.apply {
            productName.text = product.productName
            productPrice.text = product.productPrice.toString()
            removeProduct.setOnClickListener {
                onRemoveClicked(position)
            }
        }
    }
}