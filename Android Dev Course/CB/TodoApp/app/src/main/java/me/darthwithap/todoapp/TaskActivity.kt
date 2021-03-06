package me.darthwithap.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val DB_NAME = "todoApp.db"
private const val TAG = "TaskActivity"

class TaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mCalender: Calendar
    lateinit var progressBar: ProgressDialog
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mTimeSetListener: TimePickerDialog.OnTimeSetListener
    lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    var finalDate = 0L
    var finalTime = 0L

    companion object {
        var categories =
            arrayListOf("Personal", "Business", "Shopping", "College", "Work", "Finance", "Others")
    }

    private val db by lazy {
        TodoDatabase.getDatabase(this)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            btnSave.isEnabled = checkValidation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)

        saveCategories()
        loadCategories()

        etTime.addTextChangedListener(textWatcher)
        etDate.addTextChangedListener(textWatcher)
        etTask.addTextChangedListener(textWatcher)
        etTitle.addTextChangedListener(textWatcher)

        btnSave.isEnabled = checkValidation()

        etDate.setOnClickListener(this)
        etTime.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        llBtnSave.setOnClickListener(this)
        setupSpinner()

        progressBar = ProgressDialog(this)
    }

    private fun saveCategories() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(categories)
        Log.d(TAG, "saveCategories: $json")

        editor.putString("categories", json)
        editor.apply()
    }

    private fun loadCategories() {
        val json = sharedPreferences.getString("categories", null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        Log.d(TAG, "loadCategories: $json")

        categories = gson.fromJson(json, type)
    }

    private fun checkValidation(): Boolean {
        return !(etDate.text.toString().isEmpty() ||
                etTime.text.toString().isEmpty() ||
                etTask.text.toString().isEmpty() ||
                etTitle.text.toString().isEmpty())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.etDate -> setDateListener()
            R.id.etTime -> setTimeListener(v)
            R.id.btnSave -> saveTodo()
            R.id.llBtnSave -> checkIfValidated(v)
        }
    }

    private fun checkIfValidated(v: View) {
        if (!checkValidation()) {
            val snackbar =
                Snackbar.make(v, "Fields cannot be empty!", Snackbar.LENGTH_LONG)
            snackbar.apply {
                setBackgroundTint(resources.getColor(R.color.yosemite))
                setTextColor(resources.getColor(R.color.hot_pink))
                show()
            }
        }
    }

    private fun saveTodo() {
        progressBar.show()
        val category = spinnerCategory.selectedItem.toString()
        val title = etTitle.text.toString()
        val description = etTask.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            delay(250)
            withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTodo(
                    TodoModel(
                        title,
                        description, category,
                        finalDate,
                        finalTime
                    )
                )
            }
            progressBar.cancel()
            finish()
        }
    }

    private fun setupSpinner() {
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categories.sort()
        spinnerCategory.adapter = spinnerAdapter
    }

    private fun setTimeListener(v: View) {
        mCalender = Calendar.getInstance()
        val sdf = SimpleDateFormat(resources.getString(R.string.date_format))
        mTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val currTime = Calendar.getInstance()
            mCalender.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            if (sdf.format(Date(System.currentTimeMillis() - 1000)) == sdf.format(Date(finalDate))) {
                if (mCalender.timeInMillis > currTime.timeInMillis + 1000) updateTime()
                else dateTimeErrorSnackbar(v)
            } else updateTime()
        }
        val mTimePickerDialog = TimePickerDialog(
            this, mTimeSetListener,
            mCalender.get(Calendar.HOUR_OF_DAY), mCalender.get(Calendar.MINUTE + 1), false
        )
        mTimePickerDialog.show()
    }

    private fun dateTimeErrorSnackbar(v: View) {
        val snackbar =
            Snackbar.make(v, "Can't set a date in the past!", Snackbar.LENGTH_LONG)
        snackbar.apply {
            setBackgroundTint(resources.getColor(R.color.yosemite))
            setTextColor(resources.getColor(R.color.hot_pink))
            show()
        }
    }

    private fun updateTime() {
        val simpleDateFormat = SimpleDateFormat(getString(R.string.time_format))
        finalTime = mCalender.time.time
        etTime.setText(simpleDateFormat.format(mCalender.time))
    }

    private fun setDateListener() {
        mCalender = Calendar.getInstance()
        mDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            mCalender.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONDAY, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            updateDate()
        }

        val mDatePickerDialog = DatePickerDialog(
            this, mDateSetListener, mCalender.get(Calendar.YEAR),
            mCalender.get(Calendar.MONDAY), mCalender.get(Calendar.DAY_OF_MONTH)
        )
        val mDatePicker = mDatePickerDialog.datePicker
        mCalender.add(Calendar.MONTH, +1)
        if (finalTime > 0 && finalTime > System.currentTimeMillis()) mDatePicker.minDate =
            System.currentTimeMillis() - 1000
        else mDatePicker.minDate = System.currentTimeMillis() - 1000 + 24 * 60 * 60 * 1000
        mDatePicker.maxDate = mCalender.timeInMillis
        mDatePickerDialog.show()
    }

    private fun updateDate() {
        val simpleDateFormat = SimpleDateFormat(getString(R.string.date_format))
        finalDate = mCalender.time.time
        etDate.setText(simpleDateFormat.format(mCalender.time))
        ilTime.visibility = View.VISIBLE
    }
}