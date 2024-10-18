package lat.pam.hellotoast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private var mCount = 0
    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switchpage)
        val buttonBrowser = findViewById<Button>(R.id.button_browser)



        buttonCountUp.setOnClickListener(View.OnClickListener {
            mCount = mCount + 1
            if (mShowCount != null)
            //mShowCount.text = mCount.toString()
                model.currentName.setValue(mCount)
        })


        buttonToast.setOnClickListener {
            val tulisan = mShowCount.text.toString()
            val toast = Toast.makeText(this, "Angka yang dimunculkan $tulisan", Toast.LENGTH_LONG)
            toast.show()
        }

        buttonSwitchPage.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        buttonBrowser.setOnClickListener(View.OnClickListener {
            val intentbrowse = Intent(Intent.ACTION_VIEW)
            intentbrowse.setData(Uri.parse("https://www.google.com/"))
            startActivity(intentbrowse)
        })

        // Create the observer which updates the UI.
        val nameObserver = Observer<Int> { newName ->
            // Update the UI, in this case, a TextView.
            mShowCount.text = newName.toString()
        }


// Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentName.observe(this, nameObserver)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count_value", mCount)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mCount = savedInstanceState.getInt("count_value", 0)
    }

}
