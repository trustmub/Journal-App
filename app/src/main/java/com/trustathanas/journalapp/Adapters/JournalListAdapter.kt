package com.trustathanas.journalapp.Adapters

import android.content.Context
import android.view.View

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Rooms.JournalEntity

class JournalListAdapter(private val context: Context, private val journalList: List<JournalEntity>, private val itemClick: (JournalEntity) -> Unit) : RecyclerView.Adapter<JournalListAdapter.JournalVewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalVewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.journal_list_item_layout, parent, false)
        return JournalVewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return journalList.count()
    }

    override fun onBindViewHolder(holder: JournalVewHolder, position: Int) {
        holder.bindJournal(journalList[position], context)
    }

    inner class JournalVewHolder(itemView: View?, itemClick: (JournalEntity) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val journalTitle = itemView?.findViewById<TextView>(R.id.tv_title)
        private val journalContent = itemView?.findViewById<TextView>(R.id.tv_content)
        private val journalDate = itemView?.findViewById<TextView>(R.id.tv_list_posting_date)
        private val titleTag = itemView?.findViewById<TextView>(R.id.tv_tag)

        private fun getFirstLetter(title: String): String {
            return title[0].toString().toUpperCase()
        }

        private fun formatedDate(date: String): String {
            val dateLength = date.length
            val year = date.subSequence(dateLength - 4, dateLength)
            return "${date.subSequence(0, 16)} $year"
        }

        fun bindJournal(journal: JournalEntity, context: Context) {

            journalTitle?.text = journal.title.toUpperCase()
            journalContent?.text = journal.contents
            journalDate?.text = formatedDate(journal.date.toString())


            titleTag?.text = getFirstLetter(journal.title)
            /** set on click listenr on the item */
            itemView.setOnClickListener { itemClick(journal) }

        }

    }
}