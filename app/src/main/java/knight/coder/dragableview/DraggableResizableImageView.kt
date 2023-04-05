package knight.coder.dragableview


import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginStart

class DraggableResizableImageView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private var lastX = 0
    private var lastY = 0
    private var xPos = 0
    private var yPos = 0
    private var isDragging = false
    private var isResizing = false
    private var isResizingFromLeft = false
    private var isResizingFromRight = false
    private var isResizingFromTop = false
    private var isResizingFromBottom = false
    private var minWidth = 200
    private var minHeight = 200

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val left = x < width / 3
                val top = y < height / 3
                val right = x > 2 * width / 3
                val bottom = y > 2 * height / 3
                if (left && top) {
                    isResizing = true
                    isResizingFromLeft = true
                    isResizingFromTop = true
                } else if (right && top) {
                    isResizing = true
                    isResizingFromRight = true
                    isResizingFromTop = true
                } else if (left && bottom) {
                    isResizing = true
                    isResizingFromLeft = true
                    isResizingFromBottom = true
                } else if (right && bottom) {
                    isResizing = true
                    isResizingFromRight = true
                    isResizingFromBottom = true
                } else {
                    isDragging = true
                }
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = x - lastX
                val dy = y - lastY
                if (isDragging) {
                    xPos += dx
                    yPos += dy
                    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
                        marginStart = xPos
                        topMargin = yPos
                    }
                } else if (isResizing) {
                    if (isResizingFromLeft) {
                        val newWidth = width - dx
                        if (newWidth > minWidth) {
                            val lp = layoutParams as ViewGroup.MarginLayoutParams
                            lp.marginStart = xPos
                            lp.width = newWidth
                            layoutParams = lp
                            lastX = x
                        }
                    } else if (isResizingFromRight) {
                        val newWidth = width + dx
                        if (newWidth > minWidth) {
                            val lp = layoutParams as ViewGroup.MarginLayoutParams
                            lp.width = newWidth
                            layoutParams = lp
                            lastX = x
                        }
                    }
                    if (isResizingFromTop) {
                        val newHeight = height - dy
                        if (newHeight > minHeight) {
                            val lp = layoutParams as ViewGroup.MarginLayoutParams
                            lp.topMargin = yPos
                            lp.height = newHeight
                            layoutParams = lp
                            lastY = y
                        }
                    } else if (isResizingFromBottom) {
                        val newHeight = height + dy
                        if (newHeight > minHeight) {
                            val lp = layoutParams as ViewGroup.MarginLayoutParams
                            lp.height = newHeight
                            layoutParams = lp
                            lastY = y
                        }
                    }

                }
            }

            MotionEvent.ACTION_UP -> {
                isDragging = false
                isResizing = false
                isResizingFromLeft = false
                isResizingFromRight = false
                isResizingFromTop = false
                isResizingFromBottom = false
            }
        }
        return true
    }
}
