package com.example.sportapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase

class TrainingList : Fragment() {
    private lateinit var trainingListRecyclerView: RecyclerView
    private lateinit var dataSet: MutableList<ListItem>
    private lateinit var db: SQLiteDatabase

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    private fun fillList(): MutableList<ListItem> {
        var items = mutableListOf<ListItem>()
        var item: ListItem
        for(i in 0 until 2) {
            item = ListItem("kurva", "matb")
            items.add(item)
        }
        return items
    }

    private fun fillDataSet(db: SQLiteDatabase) {
        db.rawQuery("SELECT * FROM trainings", null)
        
    }
    private fun dbExist() {

    }
    private fun fillDB() {
        db = openOrCreateDatabase("mainDB.db", null)
        db.execSQL("CREATE TABLE IF NOT EXISTS trainings (name TEXT)");

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_training_list, container, false)
        val newWindow = root.findViewById<ConstraintLayout>(R.id.newWindow)
        newWindow.visibility = View.VISIBLE
        newWindow.scaleX = 0f
        newWindow.scaleY = 0f
        newWindow.alpha = 0f
        var trainingName = root.findViewById<EditText>(R.id.trainingName)

        // Открытие базы данных
        db = openOrCreateDatabase("mainDB.db", null)
        db.execSQL("CREATE TABLE IF NOT EXISTS trainings (name TEXT)");
        db.execSQL("INSERT INTO trainings (name) VALUES ()")


        // Список говна
        dataSet = fillList()
        trainingListRecyclerView = root.findViewById<RecyclerView>(R.id.trainingListRecyclerView)
        trainingListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        trainingListRecyclerView.adapter = TrainingListAdapter(dataSet, this.context)
        // Список говна закончился

        // Обработка нажатий
        root.findViewById<FloatingActionButton>(R.id.button2).setOnClickListener {
            if(isAnim) return@setOnClickListener
            var str: String = ""
            trainingName.text = str.toEditable()
            showOverlay(newWindow)
        }
        newWindow.setOnClickListener {hideKeyboard()}
        root.findViewById<ConstraintLayout>(R.id.mainScreen).setOnClickListener {
            if(isAnim) return@setOnClickListener
            fadeOverlay(newWindow)
            hideKeyboard()
        }
        root.findViewById<Button>(R.id.createBttn).setOnClickListener {
            Toast.makeText(this.context, "Hello ${trainingName.text}", Toast.LENGTH_LONG).show()
            dataSet.add(ListItem(trainingName.text.toString(), "ebaatb"))
            (trainingListRecyclerView.adapter)!!.notifyItemChanged(dataSet.size - 1)
            fadeOverlay(newWindow)
            hideKeyboard()
        }
        root.findViewById<Button>(R.id.declineBttn).setOnClickListener {
            var trainingName = root.findViewById<EditText>(R.id.trainingName)
            fadeOverlay(newWindow)
            hideKeyboard()
        }

        return root
    }

    var isAnim = false
    var animDelay = 400f

    private fun showOverlay(overlay: View){
        overlay.visibility = View.VISIBLE
        isAnim = true
        overlay.animate().alpha(1f).scaleX(1f).scaleY(1f)
            .setDuration(animDelay.toLong()).setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    isAnim = false
                }
            })
    }
    private fun fadeOverlay(overlay: View){
        isAnim = true
        overlay.animate().alpha(0f).scaleX(0f).scaleY(0f)
            .setDuration(animDelay.toLong()).setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    overlay.visibility = View.GONE
                    isAnim = false
                }
            })
    }
    private fun hideKeyboard() {
        var view: View? = this.view
        if(view != null) {
            var imm: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}