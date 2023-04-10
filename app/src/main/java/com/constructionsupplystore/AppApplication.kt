package com.constructionsupplystore

import com.common.basecommon.BaseCommonApplication
import com.constructionsupplystore.di.appModule
import com.di.productModule
import org.koin.core.module.Module

class AppApplication: BaseCommonApplication() {

    override fun getKoinModules(): List<Module> = listOf(productModule(), appModule)

}