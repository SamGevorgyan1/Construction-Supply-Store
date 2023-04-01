package com.constructionsupplystore


import com.common.BaseCommonApplication
import com.constructionsupplystore.di.appModule
import com.product.di.productModule
import org.koin.core.module.Module

class AppApplication: BaseCommonApplication() {
    override fun getKoinModules(): List<Module> = listOf(productModule(), appModule)
}