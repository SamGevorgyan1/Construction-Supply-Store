package com.product.di

import com.product.repository.ProductRepository
import com.product.repository.ProductRepositoryImpl
import org.koin.dsl.module

fun productModule()= module {

   // single<ProductRepository> { ProductRepositoryImpl() }
    factory<ProductRepository> { ProductRepositoryImpl() }
}