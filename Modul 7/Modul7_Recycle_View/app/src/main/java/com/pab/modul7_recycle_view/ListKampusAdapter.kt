package com.pab.modul7_recycle_view

import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ListKampusAdapter(private val listKampus: ArrayList<Kampus>) : RecyclerView.Adapter<ListKampusAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kampus, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kampus = listKampus[position]
        holder.imgPhoto.setImageResource(kampus.photo)
        holder.tvName.text = kampus.name
        holder.tvLokasi.text = kampus.lokasi
        holder.tvSejarah.text = kampus.sejarah

        holder.itemView.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it.context)
            builder
                .setIcon(kampus.photo)
                .setTitle("Detail Kampus: \n" + kampus.name)
                .setMessage(kampus.lokasi + "\n" + kampus.sejarah)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun getItemCount(): Int = listKampus.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvLokasi: TextView = itemView.findViewById(R.id.tv_item_location)
        val tvSejarah: TextView = itemView.findViewById(R.id.tv_item_description)

        init {
            tvSejarah.movementMethod = ScrollingMovementMethod()
        }
    }
}