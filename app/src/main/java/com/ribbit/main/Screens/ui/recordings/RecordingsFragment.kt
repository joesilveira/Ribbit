package com.ribbit.main.Screens.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ribbit.main.R
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes


class RecordingsFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_user_recordings_screen, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(view.context.filesDir)
        createTable()
    }

    fun createTable(){

        var tbl = view?.findViewById<TableLayout>(R.id.tableLayout)
        var scroll = view?.findViewById<ScrollView>(R.id.scrollView)

        var directory = view?.context?.filesDir
        var path = "/documents/recordings/"
        var recordingFolder = File(directory, path)

        println(scroll?.layoutParams?.width)


        for(i in recordingFolder.listFiles()){

            var row = TableRow(this.context)
            row.layoutParams = ViewGroup.LayoutParams(scroll!!.layoutParams!!.width,100)

            var txt = TextView(this.context)
            txt.setText(i.name)
            txt.setTextSize(34f)
            txt.width = scroll!!.layoutParams!!.width
            txt.height = 150


            //Handle when user clicks on recording
            txt.setOnClickListener {

                showPopUpWindow(i)

                //Variables to path of recording file
                var pName = txt.text
                var path = directory.toString() + path + pName
                var pFile = File(path)

            }

            //Add items to the view
            row.addView(txt)
            tbl?.addView(row)
        }
    }

    fun showPopUpWindow(file : File){

        //Get layout from xml and variables
        var cLayout = view!!.findViewById<ConstraintLayout>(R.id.cLayout)

        //Instantiate popup.xml
        var layoutinflater : LayoutInflater = LayoutInflater.from(view!!.context)
        var myView : View = layoutinflater.inflate(R.layout.recordings_popup,null)

        //Instantiate popup window
        var popUp = PopupWindow(myView,1000,1000)
        popUp.showAtLocation(cLayout, Gravity.CENTER,0,0)
        //cLayout.setBackgroundColor(resources.getColor(R.color.darkGreen))
        cLayout.alpha = 0.4F
        popUp.isOutsideTouchable = false


        //Set popup window variables
        var title = myView.findViewById<TextView>(R.id.recordingTitle)
        var date = myView.findViewById<TextView>(R.id.dateRecorded)
        var fileSize = myView.findViewById<TextView>(R.id.fileSize)


        var attributes : BasicFileAttributes = Files.readAttributes(file.toPath(),BasicFileAttributes::class.java)

        title.setText(file.name)
        date.setText(attributes.creationTime().toString())
        fileSize.setText(attributes.size().toString())



        var closeBtn = myView.findViewById<Button>(R.id.closeBtn)
        closeBtn.setOnClickListener {
            popUp.dismiss()
            cLayout.alpha = 1.0F
            //cLayout.setBackgroundColor(resources.getColor(R.color.mainGreen))
        }
    }
}