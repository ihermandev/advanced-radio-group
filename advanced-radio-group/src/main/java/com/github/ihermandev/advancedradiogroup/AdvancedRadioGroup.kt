package com.github.ihermandev.advancedradiogroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IdRes
import com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup.OnCheckedChangeListener

/**
 *  The AdvancedRadioGroup is a custom version of the Android [android.widget.RadioGroup], which
 *  allows to detect [android.widget.RadioButton] inside a custom container.
 *  Unlike the standard RadioGroup, the AdvancedRadioGroup allows greater customization by allowing
 *  the placement of the RadioButtons in containers, such LinearLayout, RelativeLayout,
 *  ConstraintLayout etc.
 *
 *  Example:
 *  <com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup
 *  ...
 *      <androidx.constraintlayout.widget.ConstraintLayout...
 *          <RadioButton...
 *          <TextView...
 *       </androidx.constraintlayout.widget.ConstraintLayout>
 *
 *        <androidx.constraintlayout.widget.ConstraintLayout...
 *          <TextView...
 *          <TextView...
 *          <RadioButton...
 *       </androidx.constraintlayout.widget.ConstraintLayout>
 *
 *       <LinearLayout...
 *          <ImageView...
 *          <TextView...
 *          <TextView...
 *          <RadioButton...
 *       </LinearLayout>
 *
 * </com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup>
 */
class AdvancedRadioGroup
@JvmOverloads
constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attr, defStyle) {

    /**
     * The id of checked RadioButton view. By default, the id values is -1 which means that there
     * is no checked view
     */
    @IdRes
    var checkedRadioId = -1
        private set

    /**
     * Monitoring child radio button checked state
     */
    private val childOnCheckedChangeListener: CompoundButton.OnCheckedChangeListener by lazy {
        CheckedChangeMonitor()
    }

    private val processHierarchyChangeListener: ProcessHierarchyChangeListener by lazy {
        ProcessHierarchyChangeListener()
    }

    /**
     * Listener to be invoked when the checked RadioButton changed in the container.
     */
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    /**
     * Flag for preventing loops
     */
    private var protectFromCheckedChange = false


    init {
        super.setOnHierarchyChangeListener(processHierarchyChangeListener)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener?) {
        processHierarchyChangeListener.onHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (checkedRadioId != -1) {
            protectFromCheckedChange = true
            setViewCheckedState(checkedRadioId, true)
            protectFromCheckedChange = false
            setCheckedId(checkedRadioId)
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is RadioButton && child.isChecked) {
            protectFromCheckedChange = true
            if (checkedRadioId != -1) {
                setViewCheckedState(checkedRadioId, false)
            }
            protectFromCheckedChange = false
            setCheckedId(child.id)

        }

        super.addView(child, index, params)
    }

    /**
     * Checking of view id and call of [setCheckedId] method if checking is pass. Logic is similar to
     * original [android.widget.RadioGroup.check]
     *
     * @param id the unique id of the RadioButton
     *
     */
    fun checkId(@IdRes id: Int) {
        if (id != -1 && id == checkedRadioId) {
            return
        }
        if (checkedRadioId != -1) {
            setViewCheckedState(checkedRadioId, false)
        }
        if (id != -1) {
            setViewCheckedState(id, true)
        }
        setCheckedId(id)
    }

    private fun setCheckedId(@IdRes id: Int) {
        checkedRadioId = id
        onCheckedChangeListener?.onCheckedChanged(this, checkedRadioId)
    }

    private fun setViewCheckedState(viewId: Int, checked: Boolean) {
        val view: View? = findViewById(viewId)
        if (view != null && view is RadioButton) {
            view.isChecked = checked
        }
    }

    /**
     * Clear RadioButton check, checkedRadioId is -1
     */
    fun clearCheck() {
        checkId(-1)
    }

    /**
     * Setter method for OnCheckedChangeListener
     *
     * @see android.widget.RadioGroup.setOnCheckedChangeListener
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }

    fun setOnCheckedChangeListener(listener: ((group: AdvancedRadioGroup, checkedId: Int) -> Unit)) {
        onCheckedChangeListener =
            OnCheckedChangeListener { group, checkedId -> listener.invoke(group, checkedId) }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(layoutParams: ViewGroup.LayoutParams?): Boolean {
        return layoutParams is RadioGroup.LayoutParams
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun getAccessibilityClassName(): String = AdvancedRadioGroup::class.java.name

    /**
     * Listener interface to be invoked when the checked RadioButton changed in the container.
     *
     * Check [android.widget.RadioGroup.OnCheckedChangeListener] for the reference
     */
    fun interface OnCheckedChangeListener {

        /**
         * Called when RadioButton has changed. The checkedId value is -1 when selection is cleared.
         */
        fun onCheckedChanged(group: AdvancedRadioGroup, @IdRes checkedId: Int)
    }

    /**
     * Monitoring child radio button checked state.
     *
     * Check [android.widget.RadioGroup.CheckedStateTracker] for the reference
     */
    private inner class CheckedChangeMonitor : CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (protectFromCheckedChange) {
                return
            }

            protectFromCheckedChange = true

            if (checkedRadioId != -1) {
                setViewCheckedState(checkedRadioId, false)
            }

            protectFromCheckedChange = false

            setCheckedId(buttonView.id)
        }
    }

    /**
     * Extend functionality of OnHierarchyChangeListener in order to process events and dispatch them
     * to internal listeners
     *
     * Check [android.widget.RadioGroup.PassThroughHierarchyChangeListener] for the reference
     */
    private inner class ProcessHierarchyChangeListener : OnHierarchyChangeListener {

        var onHierarchyChangeListener: OnHierarchyChangeListener? = null

        fun passThroughViewHierarchy(view: View) {
            if (view is RadioButton) {
                if (view.getId() == -1) {
                    view.setId(View.generateViewId())
                }
                view.setOnCheckedChangeListener(
                    childOnCheckedChangeListener
                )
            }

            if (view is ViewGroup && view.childCount != 0) {
                for (i in 0 until view.childCount) {
                    passThroughViewHierarchy(view.getChildAt(i))
                }
            } else return
        }

        override fun onChildViewAdded(parent: View, child: View) {

            passThroughViewHierarchy(child)

            if (parent === this@AdvancedRadioGroup && child is RadioButton) {
                if (child.getId() == -1) {
                    child.setId(View.generateViewId())
                }
                child.setOnCheckedChangeListener(
                    childOnCheckedChangeListener
                )
            }
            onHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View?) {
            if (parent === this@AdvancedRadioGroup && child is RadioButton) {
                child.setOnCheckedChangeListener(null)
            }
            onHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}