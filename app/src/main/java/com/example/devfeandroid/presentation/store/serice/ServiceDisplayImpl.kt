package com.example.devfeandroid.presentation.store.serice

import com.example.devfeandroid.presentation.store.serice.IServiceDisplay
import com.example.devfeandroid.presentation.store.serice.SERVICE_TYPE
import com.example.devfeandroid.presentation.store.serice.ServiceDisplay

class ServiceDisplayImpl: IServiceDisplay {
    override fun getListService(): List<ServiceDisplay> {
        val list: MutableList<ServiceDisplay> = arrayListOf()
        list.add(ServiceDisplay(SERVICE_TYPE.SKIN_CARE))
        list.add(ServiceDisplay(SERVICE_TYPE.SUN_CARE))
        list.add(ServiceDisplay(SERVICE_TYPE.CLEAN_SING))
        list.add(ServiceDisplay(SERVICE_TYPE.MEN))
        list.add(ServiceDisplay(SERVICE_TYPE.BASE))
        list.add(ServiceDisplay(SERVICE_TYPE.EYE))
        list.add(ServiceDisplay(SERVICE_TYPE.LIP_CHECK))
        list.add(ServiceDisplay(SERVICE_TYPE.MAKE_UP_TOOL))
        list.add(ServiceDisplay(SERVICE_TYPE.SET_PALETTE))
        list.add(ServiceDisplay(SERVICE_TYPE.PERFUME))
        list.add(ServiceDisplay(SERVICE_TYPE.CANDLE_DIFFUSION))
        list.add(ServiceDisplay(SERVICE_TYPE.BODY_CARE))
        list.add(ServiceDisplay(SERVICE_TYPE.HAIR_CARE))
        return list
    }
}