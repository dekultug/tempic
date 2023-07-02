package com.example.devfeandroid.presentation.store.serice

import android.graphics.drawable.Drawable
import android.os.Parcelable
import com.example.devfeandroid.R
import com.example.devfeandroid.extensions.getAppDrawable
import com.example.devfeandroid.extensions.getAppString
import kotlinx.android.parcel.Parcelize

enum class SERVICE_TYPE {
    SKIN_CARE,
    SUN_CARE,
    CLEAN_SING,
    MEN,
    BASE,
    EYE,
    LIP_CHECK,
    MAKE_UP_TOOL,
    SET_PALETTE,
    PERFUME,
    CANDLE_DIFFUSION,
    BODY_CARE,
    HAIR_CARE
}

@Parcelize
class ServiceDisplay(var type: SERVICE_TYPE) : Parcelable {
    fun getIcon(): Drawable? {
        return when (type) {
            SERVICE_TYPE.SKIN_CARE -> getAppDrawable(R.drawable.ic_skin_care)
            SERVICE_TYPE.SUN_CARE -> getAppDrawable(R.drawable.ic_sun_care)
            SERVICE_TYPE.CLEAN_SING -> getAppDrawable(R.drawable.ic_cleansing)
            SERVICE_TYPE.MEN -> getAppDrawable(R.drawable.ic_men)
            SERVICE_TYPE.BASE -> getAppDrawable(R.drawable.ic_base)
            SERVICE_TYPE.EYE -> getAppDrawable(R.drawable.ic_eye)
            SERVICE_TYPE.LIP_CHECK -> getAppDrawable(R.drawable.ic_lip_cheek)
            SERVICE_TYPE.MAKE_UP_TOOL -> getAppDrawable(R.drawable.ic_make_up_tool)
            SERVICE_TYPE.SET_PALETTE -> getAppDrawable(R.drawable.ic_set_palette)
            SERVICE_TYPE.PERFUME -> getAppDrawable(R.drawable.ic_perfume)
            SERVICE_TYPE.CANDLE_DIFFUSION -> getAppDrawable(R.drawable.ic_candle_diffusion)
            SERVICE_TYPE.BODY_CARE -> getAppDrawable(R.drawable.ic_body_care)
            SERVICE_TYPE.HAIR_CARE -> getAppDrawable(R.drawable.ic_hair_care)
        }
    }

    fun getTitle(): String {
        return when (type) {
            SERVICE_TYPE.SKIN_CARE -> getAppString(R.string.skin_care)
            SERVICE_TYPE.SUN_CARE -> getAppString(R.string.sun_care)
            SERVICE_TYPE.CLEAN_SING -> getAppString(R.string.clean_sing)
            SERVICE_TYPE.MEN -> getAppString(R.string.men)
            SERVICE_TYPE.BASE -> getAppString(R.string.base)
            SERVICE_TYPE.EYE -> getAppString(R.string.eye)
            SERVICE_TYPE.LIP_CHECK -> getAppString(R.string.lif_cheek)
            SERVICE_TYPE.MAKE_UP_TOOL -> getAppString(R.string.make_up_tool)
            SERVICE_TYPE.SET_PALETTE -> getAppString(R.string.set_palette)
            SERVICE_TYPE.PERFUME -> getAppString(R.string.perfume)
            SERVICE_TYPE.CANDLE_DIFFUSION -> getAppString(R.string.candle_diffusion)
            SERVICE_TYPE.BODY_CARE -> getAppString(R.string.body_care)
            SERVICE_TYPE.HAIR_CARE -> getAppString(R.string.hair_care)
        }
    }
}