package knight.coder.dragableview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import knight.coder.dragableview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var dX: Float = 0f
    private var dY: Float = 0f
    private var initialWidth: Float = 0f
    private var initialHeight: Float = 0f
    private var initialTouchX: Float = 0f
    private var initialTouchY: Float = 0f

    var xDelta = 0f
    var yDelta = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rectangle.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    initialWidth = view.width.toFloat()
                    initialHeight = view.height.toFloat()
                    // Calculate the distance between the touch point and the top-left corner of the view
                    xDelta = view.x - event.rawX
                    yDelta = view.y - event.rawY


                    val touchX = event.x
                    val touchY = event.y

                    val viewLeft = view.left
                    val viewTop = view.top
                    val viewRight = view.right
                    val viewBottom = view.bottom

                    val cornerSize = 16

                    binding.textX.text = "touchX $touchX\n viewLeft $viewLeft\n viewTop $viewTop\n viewRight $viewRight\n viewBottom $viewBottom"
                    binding.textY.text = touchY.toString()
                    if (touchX <= viewLeft + cornerSize && touchY <= viewTop + cornerSize) {
                        // Top-left corner was touched
                        Toast.makeText(this, "Top-left corner was touched", Toast.LENGTH_SHORT).show()
                    } else if (touchX >= viewRight - cornerSize && touchY <= viewTop + cornerSize) {
                        // Top-right corner was touched
                        Toast.makeText(this, "Top-right corner was touched", Toast.LENGTH_SHORT).show()
                    } else if (touchX <= viewLeft + cornerSize && touchY >= viewBottom - cornerSize) {
                        // Bottom-left corner was touched
                        Toast.makeText(this, "Bottom-left corner was touched", Toast.LENGTH_SHORT).show()
                    } else if (touchX >= viewRight - cornerSize && touchY >= viewBottom - cornerSize) {
                        // Bottom-right corner was touched
                        Toast.makeText(this, "Bottom-right corner was touched", Toast.LENGTH_SHORT).show()
                    } else {
                        // Center of view was touched
                        Toast.makeText(this, "Center of view was touched", Toast.LENGTH_SHORT).show()
                    }

                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY
                    val newWidth = initialWidth + dx
                    val newHeight = initialHeight + dy
                    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.width = newWidth.toInt()
                    layoutParams.height = newHeight.toInt()
                    view.layoutParams = layoutParams
                    // Update the position of the view based on the distance between the touch point and the top-left corner of the view
                    view.x = event.rawX + xDelta
                    view.y = event.rawY + yDelta
                    true
                }
                MotionEvent.ACTION_UP -> {

                    true
                }
                else -> false
            }
        }

//        binding.rectangle.setOnTouchListener(object : View.OnTouchListener {
//            private var lastX: Float = 0F
//            private var lastY: Float = 0F
//            private var initialX: Float = 0F
//            private var initialY: Float = 0F
//            private var isDragging = false
//
//            override fun onTouch(view: View, event: MotionEvent): Boolean {
//                when (event.actionMasked) {
//                    MotionEvent.ACTION_DOWN -> {
//                        initialX = view.x
//                        initialY = view.y
//
//                        // Get touch coordinates
//                        lastX = event.rawX
//                        lastY = event.rawY
//
//                        isDragging = true
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        if (isDragging) {
//                            // Calculate the amount of movement
//                            val dx = event.rawX - lastX
//                            val dy = event.rawY - lastY
//
//                            // Update the position of the view
//                            view.x += dx
//                            view.y += dy
//
//                            // Remember the last touch position
//                            lastX = event.rawX
//                            lastY = event.rawY
//                        }
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        isDragging = false
//                    }
//                    else -> return false
//                }
//                return true
//            }
//        })
    }
}