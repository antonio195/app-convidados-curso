package com.antoniocostadossantos.convidados.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.antoniocostadossantos.convidados.model.GuestModel
import com.antoniocostadossantos.convidados.repository.GuestRepository


class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private val _saveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = _saveGuest

    fun save(guestModel: GuestModel){
        if (guestModel.id == 0){
            _saveGuest.value = repository.insert(guestModel)
        }else{
            _saveGuest.value = repository.update(guestModel)
        }
    }

    fun get(id: Int){
        guestModel.value = repository.get(id)
    }

}