package com.di

import com.repository.ProductRepository
import com.repository.ProductRepositoryImpl
import org.koin.dsl.module

fun productModule()= module {

    single<ProductRepository> { ProductRepositoryImpl() }

}