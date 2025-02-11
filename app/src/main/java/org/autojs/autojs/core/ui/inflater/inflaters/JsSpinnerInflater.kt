package org.autojs.autojs.core.ui.inflater.inflaters

import android.widget.Spinner
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.inflater.ViewCreator
import org.autojs.autojs.core.ui.inflater.util.ValueMapper
import org.autojs.autojs.core.ui.widget.JsSpinner

/**
 * Created by Stardust on 2017/11/29.
 * Transformed by SuperMonster003 on Apr 12, 2023.
 */
class JsSpinnerInflater(resourceParser: ResourceParser) : AppCompatSpinnerInflater<JsSpinner>(resourceParser) {

    override fun getCreator(): ViewCreator<JsSpinner> = ViewCreator { context, attrs ->
        attrs.remove("android:spinnerMode")?.let { JsSpinner(context, SPINNER_MODES[it]) } ?: JsSpinner(context)
    }

    companion object {

        val SPINNER_MODES: ValueMapper<Int> = ValueMapper<Int>("spinnerMode")
            .map("dialog", Spinner.MODE_DIALOG)
            .map("dropdown", Spinner.MODE_DROPDOWN)

    }

}