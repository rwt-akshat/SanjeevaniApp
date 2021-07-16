package com.rawat.sanjeevaniapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rawat.sanjeevaniapp.R
import com.rawat.sanjeevaniapp.databinding.ActivitySlotsBinding
import com.rawat.sanjeevaniapp.databinding.SampleBinding
import com.rawat.sanjeevaniapp.model.ModelDiffCallback
import com.rawat.sanjeevaniapp.model.Session


class RecyclerAdapter(private val context: Context, private var list: List<Session>,private val dose:Int) :
   RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    lateinit var binding:SampleBinding

    class MyViewHolder(binding: SampleBinding) : RecyclerView.ViewHolder(binding.root) {
        val name:TextView = binding.locationName
        val address:TextView = binding.locationAddress
        val blockName:TextView =binding.locationBlock
        val vaccine:TextView = binding.vaccine

        val slots: TextView = binding.slots
        val expandableLayout: LinearLayout = binding.expandableLayout
        val vaccineFees:TextView = binding.vaccineFees
        val coWinBtn:CardView = binding.coWinBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = SampleBinding.inflate(layoutInflater, parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val m = list[position]
        binding.executePendingBindings()


        holder.address.text = m.address
        holder.name.text = m.name
        holder.blockName.text = "${m.block_name}, ${m.pincode}"

        if(m.fee_type == "Paid"){
            holder.vaccineFees.visibility = View.VISIBLE
            holder.vaccineFees.text = "₹ ${m.fee}"
        }

        holder.vaccine.text = "${m.vaccine} (${m.fee_type})"

        if(dose == 1){
            holder.slots.text = m.available_capacity_dose1.toString()+" Slots"
        }else{
            holder.slots.text = m.available_capacity_dose2.toString()+" Slots"
        }


        holder.expandableLayout.visibility = if (m.expanded) View.VISIBLE else View.GONE


        holder.itemView.setOnClickListener {
            holder.expandableLayout.visibility = if (m.expanded) View.GONE else View.VISIBLE
            m.expanded = !m.expanded
            updateList(list)
        }

        holder.coWinBtn.setOnClickListener{
            val uri: Uri =
                Uri.parse("https://selfregistration.cowin.gov.in/") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
    private fun updateList(newList:List<Session>){
        val diffResults = DiffUtil.calculateDiff(ModelDiffCallback(list,newList),true)
        list = newList
        diffResults.dispatchUpdatesTo(this)
    }

}
