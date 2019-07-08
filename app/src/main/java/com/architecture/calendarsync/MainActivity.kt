package com.architecture.calendarsync

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    /**
     * The Calendar Provider is a repository for a user's calendar events. The Calendar Provider API allows you to perform query,
     * insert, update, and delete operations on calendars, events, attendees, reminders, and so on.
     */


    /**
     * Initalizing local variables
     */

    private var from_date_boolean: Boolean = false
    private var to_date_boolean: Boolean = false

    private var from_year: Int = 0
    private var from_month: Int = 0
    private var from_day: Int = 0
    private var from_hour: Int = 0
    private var from_min: Int = 0


    private var to_year: Int = 0
    private var to_month: Int = 0
    private var to_day: Int = 0
    private var to_hour: Int = 0
    private var to_min: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * implementing onclick listner
         */

        from_date.setOnClickListener {
            from_date_boolean = true
            to_date_boolean = false

            openDatePicker()

        }

        to_date.setOnClickListener {
            from_date_boolean = false
            to_date_boolean = true

            openDatePicker()
        }

        add_event.setOnClickListener {

            if (from_year != 0 && from_month != 0 && from_day != 0 && from_hour != 0 && from_min != 0 && to_year != 0 && to_month != 0 &&
                to_day != 0 && to_hour != 0 && to_min != 0 && !TextUtils.isEmpty(event_name.toString()) && !TextUtils.isEmpty(
                    event_desc.toString()
                )
            ) {

                /**
                 * Adding event to the calender.
                 */

                val from_millis = CalenderUtils.getMillis(from_year, from_month, from_day, from_hour, from_min)
                val to_millis = CalenderUtils.getMillis(to_year, to_month, to_day, to_hour, to_min)

                val id = CalenderUtils.insertEvent(
                    this,
                    from_millis,
                    to_millis,
                    1,
                    event_name.text.toString(),
                    event_desc.text.toString(),
                    25
                )


                Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show()


            }
        }


    }


    fun openDatePicker() {
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // SimpleDateFormat("dd.MM.yyyy").format(cal.time)

            if (from_date_boolean) {

                from_year = year
                from_month = monthOfYear
                from_day = dayOfMonth

            } else {

                to_year = year
                to_month = monthOfYear
                to_day = dayOfMonth

            }

            openTimePicker()
        }

        DatePickerDialog(
            this, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()

    }


    @SuppressLint("SetTextI18n")
    fun openTimePicker() {

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, hour, min ->

            if (from_date_boolean) {

                from_hour = hour
                from_min = min

                from.text = "$from_day/$from_month/$from_year $from_hour:$from_min"
            } else {

                to_hour = hour
                to_min = min

                to.text = "$to_day/$to_month/$to_year $to_hour:$to_min"
            }

        }), hour, minute, true)

        tpd.show()

    }

}
