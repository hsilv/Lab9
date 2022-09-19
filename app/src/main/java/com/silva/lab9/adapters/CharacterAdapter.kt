package com.silva.lab9.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.silva.lab9.R
import com.silva.lab9.db.CharacterRM
import de.hdodenhof.circleimageview.CircleImageView

class CharacterAdapter(
    private val dataSet: MutableList<CharacterRM>,
    private val listener: RecyclerViewCharacterClickHandler
    ) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {


    class ViewHolder(
        view: View,
        private val listener: RecyclerViewCharacterClickHandler) : RecyclerView.ViewHolder(view) {
        private val CharacterImage: CircleImageView = view.findViewById(R.id.character_item_circleImageView)
        private val CharacterStats: TextView = view.findViewById(R.id.character_item_stats)
        private val CharacterName: TextView = view.findViewById(R.id.character_item_name)
        private val layoutCharacterRM: ConstraintLayout = view.findViewById(R.id.character_item_layout)
        fun setData(character: CharacterRM){
            CharacterStats.text = character.species + " - " + character.status
            CharacterName.text = character.name
            CharacterImage.load("https://rickandmortyapi.com/api/character/avatar/10.jpeg"){
                memoryCachePolicy(CachePolicy.DISABLED)
                diskCachePolicy(CachePolicy.DISABLED)
                crossfade(enable = true)
                crossfade(450)
                placeholder(R.drawable.downloading_icon)
                error(R.drawable.error_icon)
            }
            layoutCharacterRM.setOnClickListener{
                listener.onCharacterClicked(character)
            }
        }
    }

    interface RecyclerViewCharacterClickHandler{
        fun onCharacterClicked(character: CharacterRM)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item_layout, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}