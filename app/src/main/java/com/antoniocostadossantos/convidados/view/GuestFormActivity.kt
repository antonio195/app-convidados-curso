package com.antoniocostadossantos.convidados.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antoniocostadossantos.convidados.contants.DataBaseConstants
import com.antoniocostadossantos.convidados.databinding.ActivityGuestFormBinding
import com.antoniocostadossantos.convidados.model.GuestModel
import com.antoniocostadossantos.convidados.viewModel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        // Botão Presente vir marcado como padrão
        binding.radioPresent.isChecked = true

        binding.buttonSave.setOnClickListener { saveContact() }

        loadData()

        observe()


    }

    private fun observe(){
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer{
            if (it){
                if (guestId == 0) {
                    Toast.makeText(applicationContext, "Contato salvo", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "Contato atualizado", Toast.LENGTH_SHORT).show()
                }
                finish()
            }else{
                Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }

    private fun saveContact() {
        var name = binding.editName.text.toString()
        val presence = binding.radioPresent.isChecked

        val guestModel = GuestModel(guestId, name, presence)

        viewModel.save(guestModel)

        Toast.makeText(this, "Contato salvo", Toast.LENGTH_LONG).show()

//        binding.editName.text.clear()
    }
}